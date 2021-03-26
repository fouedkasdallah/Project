package com.example.project.ui.main

//import kotlinx.android.synthetic.main.main_fragment.*
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.project.R
import com.example.project.databinding.MainFragmentBinding
import com.example.project.projectApplication.Companion.application
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainFragment : Fragment() {
    private var auth: FirebaseAuth = Firebase.auth
    private lateinit var viewModel: MainViewModel
    lateinit var  binding : MainFragmentBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = MainFragmentBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.vm=viewModel
        binding.lifecycleOwner=this

        return binding.root
        //return inflater.inflate(R.layout.main_fragment, container, false)

    }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       /*val options = navOptions {
           anim {
               enter = R.anim.fragment_fade_enter
               exit = R.anim.fragment_fade_exit
               popEnter = R.anim.nav_default_pop_enter_anim
               popExit = R.anim.nav_default_pop_exit_anim
           }
       }*/
       val currentUser = auth.currentUser
       if ( currentUser != null){ Log.d("login","${currentUser.displayName} is logged in")

           Toast.makeText(application, "${currentUser.displayName} connecté", Toast.LENGTH_LONG).show()
           findNavController().navigate(R.id.action_mainFragment_to_permissionsFragment)
       }
       else {
           Log.d("login","user is not logged in")
           val toast = Toast.makeText(application, "Vous n'ete pas connecté", Toast.LENGTH_LONG)
           toast.show()
           findNavController().navigate(R.id.action_mainFragment_to_loginFragment)

       }


       binding.button.setOnClickListener {
           /*val db = Firebase.firestore
               val docRef=db.collection("equipements").document("012345")
               docRef
                       .update("NS", "abcd12345")
                       .addOnSuccessListener { Log.d("firestore", "DocumentSnapshot successfully updated!") }
                       .addOnFailureListener { e -> Log.w("firestore", "Error updating document", e) }*/
           /*val jsonList=getJsonDataFromAsset(this.requireContext(),"baseabmnpc.json")
               Log.d("firestore","$jsonList")
               val gson = Gson()
               val equipementsList = object : TypeToken<List<Equipements>>() {}.type
               val equipements: List<Equipements> = gson.fromJson(jsonList, equipementsList)
               equipements.forEach {
                   //Log.d("firestore","${it.inv} => {$it.equipement}")
                   //db.collection("cities").document("LA").set(city)
                   docRef.document(it.inv).set(it.equipement)
                           .addOnSuccessListener {
                               Log.d("firestore","write success")
                           }
                           .addOnFailureListener {
                               Log.d("firestore","write fail ${it.cause}")
                           }

               }
               docRef.get()
                       .addOnSuccessListener { result ->


                           *//*val chaine =gson.toJson(result.documents)*//*
                           *//*val eq=listOf( Equipements("012345", Equipement("PC","STOCK","02755",true)),
                                   Equipements("012346", Equipement("PC","STOCK","02755",true))
                           )
                           val chaine1  : String= gson.toJson(eq)
                           Log.d("firestore","${chaine1}")*//*
                           for (document in result) {
                               Log.d("firestore", "${document.id} => ${document.data}")


                           }
                       }
                       .addOnFailureListener { exception ->
                           Log.d("firestore", "Error getting documents: ", exception)
                       }*/
           //findNavController().navigate(R.id.action_mainFragment_to_loginFragment, null,options)
       }
       binding.button2.setOnClickListener {
           findNavController().navigate(R.id.action_mainFragment_to_permissionsFragment, null)
       }
    }


}