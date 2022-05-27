package com.picpay.desafio.android.presentation.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.picpay.desafio.android.R
import com.picpay.desafio.android.databinding.ActivityMainBinding
import com.picpay.desafio.android.presentation.utils.ViewExtensions.toVisibility
import com.picpay.desafio.android.presentation.view.adapter.UserListAdapter
import com.picpay.desafio.android.presentation.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }
    private val userViewModel: UserViewModel by viewModels()
    private val userAdapter: UserListAdapter by lazy { UserListAdapter() }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAdapter()
        setObservers()
        userViewModel.getUsers()
    }

    private fun setupAdapter() {
        binding.recyclerView.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun setObservers() {
        userViewModel.userResult.observe(this) { userListState ->
            userListState.users?.let { userAdapter.updateUsers(it) }
            binding.progressBar.visibility = userListState.isLoading.toVisibility()
            userListState.error?.let { showErrorToast() }
        }
    }

    private fun showErrorToast() {
        val message = getString(R.string.error)
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
