package com.example.controltask.ui.theme.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.controltask.R
import com.example.controltask.databinding.FragmentRecoverAccountBinding
import com.example.controltask.helper.BaseFragment
import com.example.controltask.helper.FirebaseHelper
import com.example.controltask.helper.initToolbar
import com.example.controltask.helper.showBottomSheet
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RecoverAccountFragment : BaseFragment() {

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

        initToolbar(binding.toolbar)

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

            hideKeyboard()
            binding.progressBar.isVisible = true

            recoverAccountUser(email)

        } else {
            showBottomSheet(
                message = R.string.text_email_empty_recover_account_fragment
            )
        }
    }

    private fun recoverAccountUser(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Task in success, show a success message to the user.
                    showBottomSheet(
                        message = R.string.text_email_send_sucess_recover_account_fragment
                    )

                } else {
                    showBottomSheet(
                        message = FirebaseHelper.validError(task.exception?.message ?: "")
                    )
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