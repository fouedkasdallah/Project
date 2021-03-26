package com.example.project.core


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.project.R
import com.example.project.assistant.AssistantViewModel
import com.example.project.databinding.AbmfragmentBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class AbmFragment : Fragment() {

    private val assistanViewModel: AssistantViewModel by activityViewModels()
    lateinit var binding: AbmfragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AbmfragmentBinding.inflate(inflater, container, false)
        //binding.vm=assistanViewModel
        binding.assistantvm = assistanViewModel
        binding.lifecycleOwner = this
        assistanViewModel.source = R.id.abmFragment

        return binding.root
    }


}
/* companion object  private val COUNTRIES = arrayOf(
        "Belgium", "France", "Italy", "Germany", "Spain")*/
/*
@ExperimentalCoroutinesApi
override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    */
/*val COUNTRIES = arrayOf("")

    assistanViewModel.allLieuxListe.observe(viewLifecycleOwner){ lieux ->
        ArrayAdapter(
            this.requireContext(),
            android.R.layout.simple_list_item_1, lieux)
            .also {
                binding.autoCompleteTextView.setAdapter(it)
            }

    }*//*

    */
/*binding.buttonAffecter.setOnClickListener {
        //viewModel.equipement.value?.Localisation = binding.autoCompleteTextView.text.toString()
        val docRef = db.collection("equipements").document(assistanViewModel.detectedCode.value!!)
        docRef
            .update("localisation",binding.autoCompleteTextView.text.toString() )
            .addOnSuccessListener { Log.d("firestore", "DocumentSnapshot successfully updated!") }
            .addOnFailureListener { e -> Log.w("firestore", "Error updating document", e) }
    }*//*

    */
/*binding.buttonVersAssistantLieu.setOnClickListener {
        findNavController().navigate(R.id.action_abmFragment_to_assistantLocalFragment)
    }*//*



}*/
