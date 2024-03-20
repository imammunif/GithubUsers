package com.example.githubusers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubusers.data.response.ItemsItem
import com.example.githubusers.databinding.ActivityMainBinding
import com.example.githubusers.ui.GithubuserAdapter
import com.example.githubusers.ui.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)

        mainViewModel.listGithubUser.observe(this) { githubUser ->
            setGithubUserData(githubUser)
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUsers.addItemDecoration(itemDecoration)

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
        }
    }

    private fun setGithubUserData(githubUser: List<ItemsItem>) {
        val adapter = GithubuserAdapter()
        adapter.submitList(githubUser)
        binding.rvUsers.adapter = adapter
    }


    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}