/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.example.github.ui.search

import android.content.Context
import android.os.Bundle
import android.os.IBinder
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.example.github.AppExecutors
import com.android.example.github.R
import com.android.example.github.binding.FragmentDataBindingComponent
import com.android.example.github.databinding.SearchFragmentBinding
import com.android.example.github.di.Injectable
import com.android.example.github.ui.common.RepoListAdapter
import com.android.example.github.ui.common.RetryCallback
import com.android.example.github.ui.repo.RepoFragmentDirections
import com.android.example.github.ui.user.UserFragment
import com.android.example.github.ui.user.UserFragmentDirections
import com.android.example.github.util.autoCleared
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class SearchFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var appExecutors: AppExecutors

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<SearchFragmentBinding>()

    var adapter by autoCleared<RepoListAdapter>()

    val searchViewModel: SearchViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.search_fragment,
            container,
            false,
            dataBindingComponent
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = searchViewModel

        val usernameEditText = binding.username
        val emailEditText = binding.email
        val birthdayEditText = binding.birthday
        val loginButton = binding.login
        val loadingProgressBar = binding.loading

        searchViewModel.loginFormState.observe(viewLifecycleOwner,
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
                    replaceFragment()
                }
            })
    }

    private fun replaceFragment(){
        findNavController().navigate(UserFragmentDirections.showRepo(binding.username.text.toString(), binding.email.text.toString(), binding.birthday.text.toString()))

    }
}
