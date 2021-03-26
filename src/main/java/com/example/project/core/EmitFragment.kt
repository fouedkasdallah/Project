package com.example.project.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.project.R
import com.example.project.assistant.AssistantViewModel
import com.example.project.databinding.EmitfragmentBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi

//import kotlinx.android.synthetic.main.abmfragment.*
//import kotlinx.android.synthetic.main.emitfragment.*

class EmitFragment : Fragment() {
    //private val viewModel: AssistantViewModel by activityViewModels()
    @ExperimentalCoroutinesApi
    private val assistantViewModel : AssistantViewModel by activityViewModels()
   /* companion object {
        fun newInstance() = EmitFragment()
    }*/
    lateinit var binding : EmitfragmentBinding
    //private lateinit var viewModel: EmitViewModel

    @ExperimentalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EmitfragmentBinding.inflate(inflater,container,false)
        //binding.vm=viewModel
        assistantViewModel.source=R.id.emitFragment
        binding.vm=assistantViewModel
        binding.lifecycleOwner=this

        return binding.root
    }

    /*override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var b="1/2/3/4"
        var ab=b.split("/")
        var x= ab[1].toInt().toString()

        *//*binding.buttonVersAssistantLieu.setOnClickListener {
            findNavController().navigate(R.id.assistantLocalFragment)
        }*//*
       *//* val adapterZone : ArrayAdapter<String> = ArrayAdapter<String>(this.requireContext(),
                android.R.layout.simple_list_item_1,ZONE).also {
            binding.autoCompleteTextViewZone.setAdapter(it)
        }
        val adapterImmeuble : ArrayAdapter<String> = ArrayAdapter<String>(this.requireContext(),
                android.R.layout.simple_list_item_1,IMMEUBLE).also {
            binding.autoCompleteTextViewZone.setAdapter(it)
        }*//*
    }*/

}