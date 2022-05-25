package com.picpay.desafio.android.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.picpay.desafio.android.R
import com.picpay.desafio.android.databinding.ActivityMainBinding
import com.picpay.desafio.android.view.adapter.UserListAdapter
import com.picpay.desafio.android.viewmodel.UserRequestStatus
import com.picpay.desafio.android.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }
    private val userViewModel: UserViewModel by viewModels()
    @Inject
    lateinit var userAdapter: UserListAdapter

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
        userViewModel.userResult.observe(this) { userList ->
            userAdapter.updateUsers(userList)
        }

        userViewModel.requestStatus.observe(this) { status ->
            when (status) {
                UserRequestStatus.SUCCESS -> binding.progressBar.visibility = View.GONE
                UserRequestStatus.ERROR -> showErrorScreen()
                UserRequestStatus.LOADING -> binding.progressBar.visibility = View.VISIBLE
                else -> {
                    Log.i(TAG, "User request status is null!")
                    showErrorScreen()
                }
            }
        }
    }

    private fun showErrorScreen() {
        val message = getString(R.string.error)
        binding.progressBar.visibility = View.GONE
        binding.recyclerView.visibility = View.GONE
        showToast(message)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT)
            .show()
    }
}
