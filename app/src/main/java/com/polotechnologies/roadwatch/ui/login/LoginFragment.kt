package com.polotechnologies.roadwatch.ui.login


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.polotechnologies.roadwatch.R
import com.polotechnologies.roadwatch.databinding.FragmentLoginBinding

/**
 * Login Screen [Fragment]
 */
class LoginFragment : Fragment() {

    private lateinit var mBinding : FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        mBinding.btnLoginSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        mBinding.btnLogin.setOnClickListener {
            login()
        }


        return mBinding.root
    }

    private fun login() {
        Toast.makeText(context, "Logging In.....", Toast.LENGTH_SHORT).show()
    }


}
