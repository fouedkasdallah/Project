package com.example.project.scan


import android.content.Context
import android.media.MediaActionSound
import android.os.Bundle
import android.util.Log
import android.util.Size
import android.view.LayoutInflater
import android.view.SoundEffectConstants
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.project.PermissionsFragment
import com.example.project.assistant.AssistantViewModel
import com.example.project.databinding.ScanFragmentBinding
import com.example.project.model.EquipementId
import com.google.common.util.concurrent.ListenableFuture
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.io.IOException
import java.util.concurrent.Executor

@ExperimentalCoroutinesApi
class ScanFragment : Fragment() {
    private val viewModel:  AssistantViewModel by activityViewModels()
    private lateinit var barcodeScanner: BarcodeScaner
    lateinit var binding: ScanFragmentBinding
    private lateinit var cameraExecutor: Executor
    private val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    private lateinit var cameraProvider: ProcessCameraProvider
    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider> //=
    private lateinit var camera: Camera //=cameraProvider.bindToLifecycle( viewLifecycleOwner, cameraSelector )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ScanFragmentBinding.inflate(inflater, container, false)
        binding.vm = viewModel

        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        cameraExecutor = ContextCompat.getMainExecutor(this.requireContext())
        cameraProviderFuture = ProcessCameraProvider.getInstance(this@ScanFragment.requireContext()) //context.requireContext())
        cameraProvider = cameraProviderFuture.get()
        barcodeScanner = BarcodeScaner()
        camera = cameraProvider.bindToLifecycle(viewLifecycleOwner, cameraSelector)

        binding.switch1.setOnCheckedChangeListener { buttonView, isChecked ->
            camera.cameraControl.enableTorch(isChecked)
            buttonView.playSoundEffect(SoundEffectConstants.NAVIGATION_DOWN)
        }

        val permission = PermissionsFragment.hasPermissions(this.requireContext())
        binding.scanbutton.isEnabled = permission

        binding.buttonInject.setOnClickListener {
            val db = Firebase.firestore

            val jsonList = getJsonDataFromAsset(this.requireContext(), "Base_ABM_ALL.json")
            Log.d("firestore", "$jsonList")
            val gson = Gson()
            val equipementsList = object : TypeToken<List<EquipementId>>() {}.type
            val equipements: List<EquipementId> = gson.fromJson(jsonList, equipementsList)
            val docRef = db.collection("equipements")
            equipements.forEach {
                //Log.d("firestore","${it.inv} => {$it.equipement}")
                //db.collection("cities").document("LA").set(city)
                docRef.document(it.Inventaire!!).set(it.equipement)
                    .addOnSuccessListener {
                        Log.d("firestore", "write success")
                    }
                    .addOnFailureListener { exception ->
                        Log.d("firestore", "write fail ${exception.cause}")
                    }

            }
            /*docRef.get()
                    .addOnSuccessListener { result ->


                        val chaine =gson.toJson(result.documents)
                        val eq=listOf( Equipements("012345", Equipement("PC","STOCK","02755",true)),
                                Equipements("012346", Equipement("PC","STOCK","02755",true))
                        )
                        val chaine1  : String= gson.toJson(eq)
                        Log.d("firestore","${chaine1}")
                        for (document in result) {
                            Log.d("firestore", "${document.id} => ${document.data}")


                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.d("firestore", "Error getting documents: ", exception)
                    }*/
        }
        binding.scanbutton.setOnClickListener {
            if (permission) {
                Log.d("scanbutton", "ok")
                barcodeScanner.cameraStart()
            }
        }
    }

    private fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

    override fun onDestroy() {

                super.onDestroy()
                barcodeScanner.stop = true
            }

    inner class BarcodeScaner {

        var stop = false
        private val preview = Preview.Builder()
            .build()
            .also {
                it.setSurfaceProvider(this@ScanFragment.binding.viewFinder.surfaceProvider)
            }
        private val barcodeAnalyzer = ImageAnalysis.Builder()
            .setTargetResolution(Size(1280, 720))
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()
            .also {
                it.setAnalyzer(cameraExecutor, BarcodeDetector { luma ->
                    MediaActionSound().play(MediaActionSound.START_VIDEO_RECORDING)
                    Log.d(TAG, "Average luminosity: $luma")
                    //TODO implementer regles dans viewmodel
                    if (luma.startsWith("00")) viewModel.detectedCode.value = luma.toLongOrNull().toString()
                    else viewModel.detectedCode.value = luma.dropLast(1).toLongOrNull().toString()

                    Log.d("code", viewModel.detectedCode.value!!)
                    //multiple multiple multiple.....................................
                    cameraProvider.unbindAll()
                    //cameraExecutor.shutdownNow()
                })
            }

        fun cameraStart() {

          cameraProviderFuture.addListener  ({
                try {
                    // Unbind use cases before rebinding
                    cameraProvider.unbindAll()
                    Log.d("bind", "avant bind")
                    // Bind use cases to camera
                    cameraProvider.bindToLifecycle(
                        this@ScanFragment.viewLifecycleOwner,
                        cameraSelector,
                        preview,
                        barcodeAnalyzer
                    )
                    Log.d("bind", "apres bind")

                } catch (exc: Exception) {
                    Log.e(TAG, "Use case binding failed", exc)
                }

            },cameraExecutor) //ContextCompat.getMainExecutor(this@ScanFragment.context))*/

        }

        private val TAG = "CameraXBasic"

    }
}
