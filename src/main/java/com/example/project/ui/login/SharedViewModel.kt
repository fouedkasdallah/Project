package com.example.project.ui.login

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.project.projectApplication
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SharedViewModel : ViewModel() {


    private var auth: FirebaseAuth = Firebase.auth



    private val _password : MutableLiveData<String?> = MutableLiveData<String?>(null)
    var password = _password

    private val _user : MutableLiveData<String?> = MutableLiveData<String?>(null)
    var user = _user

    private val _displayName : MutableLiveData<String?> = MutableLiveData<String?>(null)
    var displayName = _displayName
        /*get() {
            return _password
        }
        set(value) {
            if (value != _password) _password.value=value.value
        }*/

        fun loguser(puser: String, ppassword: String) {
                val email = "${puser}@biat.com.tn"
                //viewModelScope.launch {
                auth.signInWithEmailAndPassword(email, ppassword)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("Login envoie", "signInWithEmail:success")
                                //val user = auth.currentUser!!.email
                                _user.value=auth.currentUser!!.email
                                _displayName.value= auth.currentUser!!.displayName
                                Toast.makeText(
                                        projectApplication.application, "Authentication success.",
                                        Toast.LENGTH_LONG).show()
                                /*val userProfileChangeRequest = UserProfileChangeRequest.Builder()
                                .setDisplayName(displayName)
                                .build()


                            if (user != null) {
                                user.updateProfile(userProfileChangeRequest)
                                viewModel.email = user.email
                                viewModel.displayName = user.displayName
                            }*/
                                /*findNavController().navigate(R.id.action_loginFragment_to_mainFragment)*/
                            } else {
                                // If sign in fails, display a message to the user.
                                    Toast.makeText(
                                        projectApplication.application, "Authentication failed: ${task.exception?.cause?.message} ",
                                        Toast.LENGTH_LONG).show()
                            }
                        }

            }


}