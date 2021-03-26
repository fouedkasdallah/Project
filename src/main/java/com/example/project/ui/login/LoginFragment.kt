package com.example.project.ui.login

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.project.databinding.LoginFragmentBinding
import com.example.project.projectApplication
//import kotlinx.android.synthetic.main.login_fragment.*


class LoginFragment : Fragment() {
    private lateinit var viewModel: SharedViewModel

    lateinit var binding : LoginFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        binding = LoginFragmentBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(this.requireActivity()).get(SharedViewModel::class.java)
        binding.vm=viewModel
        binding.lifecycleOwner=this

        return binding.root
    }

    @SuppressLint("NewApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonLogin.setOnClickListener {
            if (binding.editTextTextEmailAddress.text.length > 4 && binding.editTextTextPassword.text.isNotBlank())
                    viewModel.loguser(binding.editTextTextEmailAddress.text.toString(), binding.editTextTextPassword.text.toString())
            else
                Toast.makeText(
                    projectApplication.application, "Veuillez saisir Matricule et mot de passe.",
                    Toast.LENGTH_LONG).show()
            /*val matricule = editTextTextEmailAddress.text.toString()
            val email ="$matricule@biat.com.tn"
            val password = editTextTextPassword.text.toString()
            val displayName= editTextTextPersonName.text.toString()
            if (matricule != null && password !=null) {
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this.requireActivity()) { task ->
                            if (task.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("Login envoie", "signInWithEmail:success")
                                val user = auth.currentUser
                                val userProfileChangeRequest = UserProfileChangeRequest.Builder()
                                        .setDisplayName(displayName)
                                        .build()


                                if (user != null) {
                                    user.updateProfile(userProfileChangeRequest)
                                    viewModel.email = user.email
                                    viewModel.displayName = user.displayName
                                }
                                findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("Login envoie", "signInWithEmail:failure", task.exception)
                                Toast.makeText(this.requireActivity().baseContext, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show()

                                // ...
                            }

                            // ...
                        }
            }*/
        }

    }
}