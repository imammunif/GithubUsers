package com.example.githubusers.ui.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubusers.R
import com.example.githubusers.databinding.ActivityUserDetailBinding
import com.example.githubusers.ui.follow.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailBinding
    private lateinit var username: String
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val avatarUrl = intent.getStringExtra("avatar_url")
        username = intent.getStringExtra("username") ?: ""

        detailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[DetailViewModel::class.java]

        setupUserProfileAndObserve(username, avatarUrl)

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        setViewPager()
    }

    private fun setupUserProfileAndObserve(username: String, avatarUrl: String?) {
        Glide.with(this)
            .load(avatarUrl)
            .apply(RequestOptions.circleCropTransform())
            .into(binding.imgDetailItemAvatar)

        binding.tvDetailUsername.text = username

        detailViewModel.findDetailUser(username)

        detailViewModel.profileUser.observe(this) { userDetail ->
            val followers = userDetail?.followers.toString()
            val following = userDetail?.following.toString()

            val followersText = getString(R.string.followers, followers)
            val followingText = getString(R.string.following, following)

            binding.textViewFollowers.text = followersText
            binding.textViewFollowing.text = followingText

            val fullName = userDetail?.name
            if (fullName != null) {
                binding.tvDetailFullname.text = fullName
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setViewPager() {
        val adapter = SectionsPagerAdapter(this, username)
        binding.viewPager.adapter = adapter

        binding.viewPager.offscreenPageLimit = 2

        TabLayoutMediator(binding.detailTabs, binding.viewPager) { tab, position ->
            val tabText = this.getString(TAB_TITLES[position])
            tab.text = tabText
        }.attach()
    }

    companion object {
        private val TAB_TITLES = arrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}
