package com.example.githubusers.ui.follow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubusers.databinding.FragmentFollowBinding
import com.example.githubusers.ui.UserAdapter

class FollowFragment : Fragment() {

    private lateinit var binding: FragmentFollowBinding
    private lateinit var adapter: UserAdapter
    private lateinit var username: String
    private var position: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)!!
        }

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvFollowList.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.rvFollowList.addItemDecoration(itemDecoration)

        adapter = UserAdapter(emptyList())
        binding.rvFollowList.adapter = adapter

        if (isAdded && !isDetached) {
            val userFollowViewModel = ViewModelProvider(
                this,
                ViewModelProvider.NewInstanceFactory()
            )[FollowViewModel::class.java]

            userFollowViewModel.isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }

            userFollowViewModel.followList.observe(viewLifecycleOwner) { followList ->
                if (followList != null && followList.isNotEmpty()) {
                    adapter.submitList(followList)
                } else {
                    val message = if (position == 1) {
                        "Tidak memiliki follower"
                    } else {
                        "Tidak memiliki following"
                    }
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                }
            }

            if (position == 1) {
                userFollowViewModel.getFollowList(username, "follower")
            } else {
                userFollowViewModel.getFollowList(username, "following")
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.rvFollowList.visibility = if (isLoading)
            View.GONE
        else View.VISIBLE

        binding.progressBar.visibility = if (isLoading)
            View.VISIBLE
        else View.GONE
    }

    companion object {
        const val ARG_POSITION = "section_number"
        const val ARG_USERNAME: String = ""
    }
}