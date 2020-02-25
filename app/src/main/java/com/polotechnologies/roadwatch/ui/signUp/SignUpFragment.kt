package com.polotechnologies.roadwatch.ui.signUp


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
import com.polotechnologies.roadwatch.databinding.FragmentSignUpBinding

/**
 * Sign Up Screen [Fragment]
 */
class SignUpFragment : Fragment() {

    private lateinit var mBinding: FragmentSignUpBinding
    private lateinit var mAuth : FirebaseAuth

    private var userEmail  = ""
    private var userPassword = ""
    private var userName = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)
        mAuth = FirebaseAuth.getInstance()

        mBinding.btnSignUp.setOnClickListener {
            signUp()
        }

        mBinding.btnSignUpLogin.setOnClickListener {
            activity!!.onBackPressed()
        }


        return mBinding.root
    }

    private fun signUp() {
        if(validateInputs()){
            signUpUser()
        }
    }

    private fun validateInputs(): Boolean {
        var isValid = false
        userEmail = mBinding.etSignUpEmail.text.toString()
        userPassword = mBinding.etSignUpPassword.text.toString()
        userName = mBinding.etSignUpUserName.text.toString()

        if(userEmail == ""){
            isValid = false
            mBinding.etSignUpEmail.error = "Email Required"
        }

        if(userPassword == ""){
            isValid = false
            mBinding.etSignUpPassword.error = "Email Required"
        }

        if(userName == ""){
            isValid = false
            mBinding.etSignUpUserName.error = "Email Required"
        }

        if(userEmail!="" && userPassword!="" && userName!="") isValid = true

        return isValid

    }

    private fun signUpUser() {
        mAuth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener { task ->
            if(task.isSuccessful){
                findNavController().navigate(R.id.action_signUpFragment_to_roadIncidentsFragment)

            }else{
                Toast.makeText(context, "Failed to Sign In...", Toast.LENGTH_SHORT).show()
            }
        }

    }



}
