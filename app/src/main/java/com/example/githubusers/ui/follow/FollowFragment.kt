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
import com.example.githubusers.R
import com.example.githubusers.databinding.FragmentFollowBinding
import com.example.githubusers.ui.UserAdapter

class FollowFragment : Fragment() {

    private lateinit var binding: FragmentFollowBinding
    private lateinit var adapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val position = arguments?.getInt(ARG_POSITION) ?: 0
        val username = requireArguments().getString(ARG_USERNAME) ?: ""

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvFollowList.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.rvFollowList.addItemDecoration(itemDecoration)

        adapter = UserAdapter(emptyList())
        binding.rvFollowList.adapter = adapter

        val userFollowViewModel = ViewModelProvider(this).get(FollowViewModel::class.java)

        userFollowViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        userFollowViewModel.followList.observe(viewLifecycleOwner) { followList ->
            if (followList != null && followList.isNotEmpty()) {
                adapter.submitList(followList)
            } else {
                val message = getString(if (position == 1) R.string.no_followers else R.string.no_following)
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        }

        if (position == 1) {
            userFollowViewModel.getFollowList(username, "follower")
        } else {
            userFollowViewModel.getFollowList(username, "following")
        }
    }


    private fun showLoading(isLoading: Boolean) {
        binding.rvFollowList.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val ARG_POSITION = "section_number"
        const val ARG_USERNAME: String = ""
    }
}