package com.example.project.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.project.assistant.AssistantViewModel
import com.example.project.databinding.InjectfragmentBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi

//import kotlinx.android.synthetic.main.injectfragment.*

@ExperimentalCoroutinesApi
class InjectFragment : Fragment() {
    private val viewModel: AssistantViewModel by activityViewModels()
    lateinit var binding : InjectfragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = InjectfragmentBinding.inflate(inflater,container,false)
        binding.vm=viewModel
        binding.lifecycleOwner=this

        return binding.root
    }
}