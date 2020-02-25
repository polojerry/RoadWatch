package com.polotechnologies.roadwatch.ui.login


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.polotechnologies.roadwatch.R
import com.polotechnologies.roadwatch.databinding.FragmentLoginBinding

/**
 * Login Screen [Fragment]
 */
class LoginFragment : Fragment() {

    private lateinit var mBinding: FragmentLoginBinding
    private lateinit var mAuth: FirebaseAuth

    private var userEmail = ""
    private var userPassword = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        mAuth = FirebaseAuth.getInstance()

        checkLoginStatus()
        initiateClickListeners()
        return mBinding.root
    }

    private fun checkLoginStatus() {
        if(mAuth.currentUser != null){
            findNavController().navigate(R.id.action_loginFragment_to_roadIncidentsFragment)
        }
    }

    private fun initiateClickListeners() {
        mBinding.btnLoginSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        mBinding.btnLogin.setOnClickListener {
            login()
        }
    }

    private fun login() {
        if (validateInputs()) {
            loginUser()
        }
    }

    private fun validateInputs(): Boolean {

        var isValid = false

        userEmail = mBinding.etLoginEmail.text.toString()
        userPassword = mBinding.etLoginPassword.text.toString()

        if (userEmail == "") {
            isValid = false
            mBinding.etLoginEmail.error = "Email Required"
        }

        if (userPassword == "") {
            isValid = false
            mBinding.etLoginPassword.error = "Password Required"
        }


        if (userEmail != "" && userPassword != "") isValid = true

        return isValid


    }

    private fun loginUser() {
        mBinding.progressLogin.visibility = View.VISIBLE

        mAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener {
            mBinding.progressLogin.visibility = View.GONE

            if (it.isSuccessful) {
                Toast.makeText(context, "Login Successful...", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_loginFragment_to_roadIncidentsFragment)
            }
        }.addOnFailureListener {
            Toast.makeText(context, "Failed to Login..." + it.message, Toast.LENGTH_SHORT).show()
        }


    }

}
