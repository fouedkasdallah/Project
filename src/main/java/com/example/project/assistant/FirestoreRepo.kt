package com.example.project.assistant

import android.util.Log
import com.example.project.assistant.model.Local
import com.example.project.model.Equipement
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

@ExperimentalCoroutinesApi
class FirestoreRepo {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    val user=firebaseAuth.currentUser //TODO : detecter changement


    suspend fun getLieux(pLocal : Local): MutableList<Local> {
         val localsFromServer =firebaseFirestore.collection(LIEUX)
             .get()
             .await()

         val locals = localsFromServer.toObjects<Local>()
         return when (pLocal.type) {
             ZONE -> locals.filter { loc -> loc.type == pLocal.type }.toMutableList()
             IMMEUBLE -> locals.filter { loc -> loc.type == pLocal.type && loc.Zone == pLocal.Zone }
                 .toMutableList()
             ETAGE -> { locals.filter{ loc -> loc.type == pLocal.type && loc.Zone == pLocal.Zone && loc.immeuble == pLocal.immeuble}
                 .toMutableList()}
             LIEU -> locals.filter { loc -> loc.type == pLocal.type && loc.Zone == pLocal.Zone && loc.etage == pLocal.etage && loc.immeuble == pLocal.immeuble }
                 .toMutableList()
             else -> listOf<Local>().toMutableList()
         }
     }
    suspend fun getAllLieux(): MutableList<Local> {
        val localsFromServer =firebaseFirestore.collection(LIEUX)
            .whereEqualTo(TYPE, LIEU)
            .get()
            .await()
        val lieux = localsFromServer.toObjects(Local::class.java) //??

        Log.d("debug","first lieux ${lieux.last().designation}")
        return lieux

    }
    //FLOW VERSION.......................................................
    suspend fun getEquipementWithFlow(code : String) : Flow<Equipement> = callbackFlow {
        val eventDocument = firebaseFirestore
            .collection(EQUIPEMENTS)
            .document(code)
        val subscription = eventDocument.addSnapshotListener { listener, ex ->

            if (listener!!.exists ()) {
                val eq = listener.toObject<Equipement>()
                offer(eq!!)
            }
        }
        awaitClose { subscription.remove() }
    }

    suspend fun getEquipement ( code : String?) : Equipement{
        var currentEquipement = Equipement()
        if (code !=null) {
            try {
                val equ = firebaseFirestore.collection("equipements").document(code)
                    .get()
                    .await()

                currentEquipement = equ.toObject<Equipement>()!!.apply { Inv=code }
            } catch(e : Exception) {
                Log.d("firebase","${e.message}" )
            }

        }


        return currentEquipement
    }

    fun affecter(equipement: Equipement) : Boolean? {
        var result : Boolean? = null
        val docRef = firebaseFirestore.collection(EQUIPEMENTS).document(equipement.Inv!!)
        docRef.update("localisation",equipement.NestedAffectation!!,USER,equipement.Utilisateur!!)

            .addOnSuccessListener {
                result = true
            Log.d("updateeq","ISSUCCESS : $result" )}
            .addOnFailureListener {
                result = false
                Log.d("updateeq","Fail with message : ${it.message}" )}
            .addOnCompleteListener {
                Log.d("updateeq","Update completed : ${it.isSuccessful}" )}


        return result
}
            //.update("utilisateur",equipement.Utilisateur!!)



    companion object {
        const val LIEU = "lieu"
        const val LIEUX = "lieux "
        const val TYPE = "type"
        const val ZONE = "zone"
        const val IMMEUBLE = "immeuble"
        const val ETAGE = "etage"
        const val USER = "utilisateur"
        const val EQUIPEMENTS = "equipements"

    }
    /*fun getLieuxFlow() = callbackFlow<MutableList<Local>> {
        val test =firebaseFirestore.collection(LIEU)
            .get()
        val subscription=test.addOnCompleteListener {
            if (it.isSuccessful) {
                offer(element = it.result!!.toObjects<Local>().toMutableList()) //.toObjects<Local>()) //(Local::class.java))
            } else {
                Log.d("zonelist", "erreur de recuperation des zones")
            }
        }
        awaitClose { Log.d("close", ".....") }
    }*/
    /*fun updateversion() {
        var update: Boolean = false
        val authentifiedCurrentUser = firebaseAuth.currentUser?.email?.substringBefore("@")
        lateinit var serverUser : User
        lateinit var currentUser : User
        if (authentifiedCurrentUser == null) return
        firebaseFirestore.collection(USER).document("00000")
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) serverUser = it.result!!.toObject(User::class.java)!!
                firebaseFirestore.collection(USER).document(authentifiedCurrentUser)
                    .get()
                    .addOnCompleteListener {
                        if (it.isSuccessful) currentUser = it.result!!.toObject(User::class.java)!!
                        if (currentUser.lieuxversion < serverUser.lieuxversion){
                            update=true

                        }
                    }
            }

    }



    fun findlocalFlow(local : Local) = callbackFlow<MutableList<Local>> {

        val docsRef =firebaseFirestore.collection(LIEU)
                .whereEqualTo(TYPE,local.type)
            //.whereIn("zone",listOf("${local.Zone}",null))
                //.whereEqualTo(ZONE,"*")
                //.whereEqualTo(IMMEUBLE,local.immeuble)
                //.whereEqualTo(ETAGE,local.etage)

                .get()

        val subscription=docsRef.addOnCompleteListener {
            if (it.isSuccessful) {
                offer(element = it.result!!.toObjects(Local::class.java))
            } else {
                Log.d("zonelist", "erreur de recuperation des zones")
            }
        }
        awaitClose { Log.d("close", ".....") }
    }
    fun getzoneFlow() = callbackFlow<MutableList<Local>> {
        val test =firebaseFirestore.collection(LIEU)
                .whereEqualTo("type","zone")
                .get()

        val sudcription=test.addOnCompleteListener {
                    if (it.isSuccessful) {
                        offer(element = it.result!!.toObjects(Local::class.java))
                    } else {
                        Log.d("zonelist", "erreur de recuperation des zones")
                    }
                }
        awaitClose { Log.d("close", ".....") }
    }
    suspend fun getZones(): Flow<QueryDocumentSnapshot> {
        val zones = FirebaseFirestore.getInstance().collection("local").get().await().asFlow()
        return zones
    }

    fun getUser(): FirebaseUser? = firebaseAuth.currentUser

    fun getZonelist(): Task<QuerySnapshot> = firebaseFirestore.collection("lieu")
                .whereEqualTo("type","zone")
                .get()

    fun getImmeublelist(zone: String): Task<QuerySnapshot> {

        return firebaseFirestore.collection("local")
                .document(zone)
                .collection("immeuble")
                .get()

    }

    fun getEtagelist(zone: String, immeuble: String): Task<QuerySnapshot> {

        return firebaseFirestore.collection("local")
                .document(zone)
                .collection("immeuble")
                .document(immeuble)
                .collection("etage")
                .get()

    }

    fun getLocallist(zone: String, immeuble: String,etage: String): Task<QuerySnapshot> {

        return firebaseFirestore.collection("local")
                .document(zone)
                .collection("immeuble")
                .document(immeuble)
                .collection("etage")
                .document("etage")
                .collection("local")
                .get()

    }*/
}
