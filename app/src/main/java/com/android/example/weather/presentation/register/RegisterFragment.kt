

package com.android.example.weather.presentation.register

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
import com.android.example.weather.R
import com.android.example.weather.databinding.RegisterFragmentBinding
import com.android.example.weather.presentation.binding.FragmentDataBindingComponent
import com.android.example.weather.di.Injectable
import com.android.example.weather.util.autoCleared
import com.android.example.weather.vo.RegisterUser
import javax.inject.Inject

class RegisterFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    private var binding by autoCleared<RegisterFragmentBinding>()

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

        registerViewModel.getUser()

        setUI()
    }

    private fun setUI(){
        registerViewModel.registerFormState.observe(viewLifecycleOwner,
            Observer { formState ->
                if (formState == null) {
                    return@Observer
                }
                formState.nameError?.let {
                    binding.username.error = getString(it)
                }
                formState.emailError?.let {
                    binding.email.error = getString(it)
                }
                formState.birthdayError?.let {
                    binding.birthday.error = getString(it)
                }
                if(formState.isDataValid){
                    var registerUser = formState.registerUse
                        ?: RegisterUser(binding.username.text.toString(), binding.email.text.toString(), binding.birthday.text.toString())

                    replaceFragment(registerUser)
                }
            })
    }

    private fun replaceFragment(registerUser: RegisterUser){
        if(registerUser.name.isNotBlank()){
            findNavController().navigate(RegisterFragmentDirections.showWeather(registerUser.name, registerUser.email, registerUser.birthday))
        }

    }
}
