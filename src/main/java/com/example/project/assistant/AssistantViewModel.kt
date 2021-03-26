package com.example.project.assistant

import android.util.Log
import android.view.View
import androidx.lifecycle.*
import androidx.navigation.findNavController
import com.example.project.R
import com.example.project.assistant.model.Local
import com.example.project.model.Equipement
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect

@ExperimentalCoroutinesApi
class AssistantViewModel : ViewModel() {
    var _detectedCode: MutableLiveData<String?> = MutableLiveData<String?>(null)
    var detectedCode = _detectedCode

    var localToFind = Local()
    var source = 0
    private val firestoreRepo = FirestoreRepo()
    //lateinit var destination : NavController
    //old version...................................................
    val equipement = detectedCode.switchMap {
        val localEq = liveData(Dispatchers.IO) {
            ->
            val eq = firestoreRepo.getEquipement(it)
            emit(eq)
            Log.d("currenteq1", "$eq ${localToFind.nom}")
        }
        Log.d("currenteq2", "${localEq.value}")
        localEq
    }
    var equipement1 : LiveData<Equipement> =MutableLiveData(Equipement())
    //update version.....................................................
    fun updateEquipement(barcode : String) {
        detectedCode.value = barcode
        equipement1 = liveData(Dispatchers.IO) {
            firestoreRepo.getEquipementWithFlow(detectedCode.value!!)
                .collect {
                    emit(it)
                }
        }
    }

    //FLOW VERSION..............................................
    /*var equipement = liveData(Dispatchers.IO) {
        firestoreRepo.getEquipementWithFlow(detectedCode.value!!).collect {
            emit(it)
        }
    }*/

/*    var equipement =this.viewModelScope.launch { detectedCode.switchMap {

        firestoreRepo.getEquipementWithFlow(it!!).collect{
            emit(it)
        }

    }*/

    val localListe: LiveData<MutableList<Local>>
        get() = liveData(Dispatchers.IO) {
            try {
                val localliste = firestoreRepo.getLieux(localToFind)
                emit(localliste)
            } catch (e: Exception) {
                Log.d("exception", "test")
            }
        }

    val allLieuxListe
        get() = liveData(Dispatchers.IO) {
            ->
            try {
                Log.d("debug", "1")
                val alllieux = emptyList<String>().toMutableList()
                Log.d("debug", "2")
                firestoreRepo.getAllLieux().forEach {
                    alllieux.add(it.designation!!)
                    Log.d("debug", "lieu : ${it.designation}")
                }
                Log.d("debug", "first in vm ${alllieux.first()}")
                emit(alllieux)
            } catch (e: Exception) {
                Log.d("exception vm", "exception ${e.message}")
            }
        }

    fun valider(view : View) {
        if (detectedCode.value != null) {
            //val test = findNavController()
            //.navigate(R.id.action_scanFragment_to_abmFragment)
            when (equipement.value!!.Localisation) {
                "ABM" ->view.findNavController().navigate(R.id.action_scanFragment_to_abmFragment) //OK
                "" -> view.findNavController().navigate(R.id.action_scanFragment_to_injectFragment)
                null -> view.findNavController().navigate(R.id.action_scanFragment_to_injectFragment)
                //"EQUIPEMENTENROUTE" -> view.findNavController().navigate(R.id.action_scanFragment_to_receiveFragment)
                else ->
                {
                    when(equipement.value!!.Affected){
                        true ->view.findNavController().navigate(R.id.action_scanFragment_to_emitFragment)//TODO
                        false ->view.findNavController().navigate(R.id.action_scanFragment_to_receiveFragment)
                    }
                }
            }
        }
    }

    fun affecterequipement() {
       if (equipement.value != null) {
           equipement.value!!.NestedAffectation=localToFind.nom
           equipement.value!!.Utilisateur=firestoreRepo.user!!.email
           if (equipement.value!!.Affected != null) {
               equipement.value!!.Affected = !equipement.value!!.Affected!!
           }else equipement.value!!.Affected=true
       }
        Log.d("EQUIPEMENTVALUE", "$equipement")
    }
    fun affecter(){

        firestoreRepo.affecter(equipement.value!!)
    }
    fun gotoAssistantLieu(view : View){

        view.findNavController().navigate( R.id.assistantLocalFragment)   //R.id.action_abmFragment_to_assistantLocalFragment)
    }

/*fun getListeZone() = firestoreRepo.findlocalFlow(local = localtofind.value!!)
.asLiveData() //: List<Zone> = ArrayList()*/

}