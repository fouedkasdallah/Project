package com.example.project.scan

//import com.example.project.projectApplication.Companion.application
//import com.example.project.projectApplication.Companion.application
import android.app.Application
import androidx.lifecycle.AndroidViewModel

//import kotlinx.android.synthetic.main.scan_fragment.*

class ScanViewModel(application1: Application) : AndroidViewModel(application1) {
/*    var _detectedCode : MutableLiveData<String?> = MutableLiveData<String?>(null)
    var detectedCode = _detectedCode
    @ExperimentalCoroutinesApi
    val repo = FirestoreRepo()
    private val _equipement : MutableLiveData<Equipement> = MutableLiveData<Equipement>(Equipement())
    @ExperimentalCoroutinesApi
    var equipement : MutableLiveData<Equipement> = detectedCode.switchMap { code ->
        val current  : MutableLiveData<Equipement> = MutableLiveData<Equipement>().apply {
            value=Equipement()
        }
        if (code==null ) {
            current.value=Equipement()
        }
        else {
            current.postValue(repo.getEquipement(code))
            val db = Firebase.firestore
            var currentEquipement: Equipement = Equipement()
            val docRef = db.collection("equipements").document(code)
                .get()
                .addOnSuccessListener {
                    current.value = it.toObject<Equipement>()!!
                }
        }
            current
           } as MutableLiveData<Equipement>
    *//*@ExperimentalCoroutinesApi
    val localListe
        get() = liveData(Dispatchers.IO) {
            ->
            try {
                val localliste = repo.getLieux(localToFind)
                emit(localliste)
            } catch (e: Exception) {
                Log.d("exception", "test")
            }
        }*//*

    @ExperimentalCoroutinesApi
    var data : LiveData<String?> = equipement.map {

        return@map "NS :"+it.NS + "\n Type :"+it.Type + "\n Modele :"+it.Marque + " " + it.Modele + "\n Lieu :" +it.Localisation //+"\n Utilisateur"+it.Utilisateur

            }

    @ExperimentalCoroutinesApi
    fun injectEquipement(type: String, marque: String, modele: String, localisation: String) {

    val db=Firebase.firestore
        equipement.value= Equipement(type,NS = null,marque,modele,localisation)
        db.collection("equipements").document(detectedCode.value!!).set(equipement.value!!)
    }
    fun getEquipement(inv : String) {
        val db=Firebase.firestore
        val docRef = db.collection("equipement").document(detectedCode.value!!)
            .get()
            .addOnSuccessListener {
                equipement.value =  it.toObject<Equipement>()!!
            }
    }*/

}
