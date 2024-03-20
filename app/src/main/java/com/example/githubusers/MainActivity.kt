package com.example.githubusers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubusers.data.response.ItemsItem
import com.example.githubusers.databinding.ActivityMainBinding
import com.example.githubusers.ui.GithubuserAdapter
import com.example.githubusers.ui.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by lazy { ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUsers.addItemDecoration(itemDecoration)

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        mainViewModel.listGithubUser.observe(this) { githubUser ->
            setGithubUserData(githubUser)
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener{ textView, actionId, event ->
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        executeSearch(searchView.text.toString())
                        searchView.hide()
                        return@setOnEditorActionListener true
                    }
                    false
                }
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

    private fun executeSearch(query: String) {
        mainViewModel.searchUsers(query)
    }
}