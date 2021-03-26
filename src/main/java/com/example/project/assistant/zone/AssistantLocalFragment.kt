package com.example.project.assistant.zone

import android.util.Log
import androidx.navigation.fragment.findNavController
import com.example.project.R
import com.example.project.assistant.model.Local
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class AssistantLocalFragment : AbstractLocalFragment() {

    override fun localToFindInitialisation() {
        super.localToFindInitialisation()
        if (viewModel.localToFind.currentState==0) viewModel.localToFind.goToState(0)
    }

    override fun prepareGoTo(zone: Local) {
        super.prepareGoTo(zone)
        Log.d("click", "${zone.designation} clicked")

        Log.d("LOCAL","avant click ${viewModel.localToFind}")
        viewModel.localToFind.designation=zone.designation
        Log.d("LOCAL","avant click ${viewModel.localToFind}")
        viewModel.localToFind.goToNextState()
        Log.d("LOCAL","apres click ${viewModel.localToFind}")
        if (viewModel.localToFind.currentState <4)
            findNavController().navigate(R.id.action_assistantLocalFragment_self)
        else {
            viewModel.affecterequipement()
            findNavController().navigate(viewModel.source)
        }
        //findNavController().navigate(R.id.action_zoneFragment_to_immeubleFragment)

    }
}