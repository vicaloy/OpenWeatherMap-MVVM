

package com.android.example.github.presentation.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.android.example.github.AppExecutors
import com.android.example.github.R
import com.android.example.github.databinding.RegisterFragmentBinding
import com.android.example.github.presentation.binding.FragmentDataBindingComponent
import com.android.example.github.di.Injectable
import com.android.example.github.util.autoCleared
import com.android.example.github.vo.RegisterUser
import javax.inject.Inject

class RegisterFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var appExecutors: AppExecutors

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<RegisterFragmentBinding>()

    private val registerViewModel: RegisterViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.register_fragment,
            container,
            false,
            dataBindingComponent
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = registerViewModel

        val usernameEditText = binding.username
        val emailEditText = binding.email
        val birthdayEditText = binding.birthday


        registerViewModel.getUser()

        registerViewModel.registerFormState.observe(viewLifecycleOwner,
            Observer { loginFormState ->
                if (loginFormState == null) {
                    return@Observer
                }
                loginFormState.nameError?.let {
                    usernameEditText.error = getString(it)
                }
                loginFormState.emailError?.let {
                    emailEditText.error = getString(it)
                }
                loginFormState.birthdayError?.let {
                    birthdayEditText.error = getString(it)
                }
                if(loginFormState.isDataValid){
                    var loggedInUser = loginFormState.registerUse
                        ?: RegisterUser(binding.username.text.toString(), binding.email.text.toString(), binding.birthday.text.toString())
                    replaceFragment(loggedInUser)
                }
            })
    }

    private fun replaceFragment(registerUser: RegisterUser){
        findNavController().navigate(RegisterFragmentDirections.showWeather(registerUser.name, registerUser.email, registerUser.birthday))

    }
}
