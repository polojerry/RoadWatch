package com.polotechnologies.roadwatch.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.polotechnologies.roadwatch.R
import com.polotechnologies.roadwatch.databinding.FragmentSignUpBinding

/**
 * Sign Up Screen [Fragment]
 */
class SignUpFragment : Fragment() {

    private lateinit var mBinding: FragmentSignUpBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)

        mBinding.btnSignUp.setOnClickListener {
            signUpUser()
        }

        mBinding.btnSignUpLogin.setOnClickListener {
            activity!!.onBackPressed()
        }


        return mBinding.root
    }

    private fun signUpUser() {
        Toast.makeText(context, "Signing In.....", Toast.LENGTH_SHORT).show()
    }


}
