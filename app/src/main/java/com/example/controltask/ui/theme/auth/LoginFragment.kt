package com.example.controltask.ui.theme.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.controltask.R
import com.example.controltask.databinding.FragmentLoginBinding
import com.example.controltask.helper.FirebaseHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initClicks()

        auth = Firebase.auth
    }

    //Class to initialize the view elements
    private fun initClicks() {

        binding.btnLogin.setOnClickListener {
            validateData()
        }

        binding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        binding.btnRecover.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_recoverAccountFragment)
        }
    }

    private fun validateData() {
        var email = binding.edtEmail.text.toString().trim()
        var password = binding.edtPassword.text.toString().trim()

        if (email.isNotEmpty()) {
            if (password.isNotEmpty()) {

                binding.progressBar.isVisible = true

                loginUser(email, password)

            } else {
                Toast.makeText(requireContext(), "Informe sua senha", Toast.LENGTH_SHORT).show()
            }

        } else {
            Toast.makeText(requireContext(), "Informe seu e-mail", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, take the user to the home screen
                    findNavController().navigate(R.id.action_global_homeFragment)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        requireContext(),
                        FirebaseHelper.validError(task.exception?.message ?: ""),
                        Toast.LENGTH_SHORT
                    ).show()
                    // If sign in fails, hide the progress bar."
                    binding.progressBar.isVisible = false
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}