package com.example.controltask.ui.theme.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.controltask.databinding.FragmentRecoverAccountBinding
import com.example.controltask.helper.FirebaseHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RecoverAccountFragment : Fragment() {

    private var _binding: FragmentRecoverAccountBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding= FragmentRecoverAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        initClicks()
    }

    private fun initClicks() {

        binding.btnSendEmail.setOnClickListener {
            validateData()
        }
    }

    private fun validateData() {
        var email = binding.edtEmail.text.toString().trim()

        if (email.isNotEmpty()) {
            binding.progressBar.isVisible = true

            recoverAccountUser(email)

        } else {
            Toast.makeText(requireContext(), "Informe seu e-mail", Toast.LENGTH_SHORT).show()
        }
    }

    private fun recoverAccountUser(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Task in success, show a success message to the user.
                    Toast.makeText(requireContext(), "E-mail enviado com sucesso", Toast.LENGTH_SHORT).show()

                } else {
                    // Task fails, show a error message to the user.
                    Toast.makeText(
                        requireContext(),
                        FirebaseHelper.validError(task.exception?.message ?: ""),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                // Task fails, hide the progress bar.
                binding.progressBar.isVisible = false
            }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}