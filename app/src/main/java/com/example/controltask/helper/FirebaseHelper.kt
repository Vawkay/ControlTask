package com.example.controltask.helper

import com.example.controltask.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class FirebaseHelper {

    companion object {

        fun getDatabase() = FirebaseDatabase.getInstance().reference

        private fun getAuth() = FirebaseAuth.getInstance()

        fun getIdUser() = getAuth().uid

        fun isAutenticated() = getAuth().currentUser != null


        //Function to validate the error returned by Firebase
        fun validError(error: String) : Int {
            return when {
                error.contains("There is no record corresponding to this identifier") -> {
                    R.string.account_not_registered_register_fragment
                }
                error.contains("The email address is badly formatted.") -> {
                    R.string.invalid_email_register_fragment
                }
                error.contains("INVALID_LOGIN_CREDENTIALS") -> {
                    R.string.invalid_password_register_fragment
                }
                error.contains("We have blocked all requests from this device due to unusual activity. Try again later.") -> {
                    R.string.too_many_atempts_register_fragment
                }
                error.contains("The email address is already in use by another account.") -> {
                    R.string.email_in_use_register_fragment
                }
                error.contains("The given password is invalid. [ Password should be at least 6 characters ]") -> {
                    R.string.strong_password_register_fragment
                }
                else -> {
                    R.string.error_generic
                }
            }
        }
    }
}