package com.example.githubusers.ui.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubusers.databinding.ActivityUserDetailBinding

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.progressBar.visibility = View.VISIBLE

        val username = intent.getStringExtra("username") ?: ""
        val avatarUrl = intent.getStringExtra("avatar_url") ?: ""

        binding.tvDetailUsername.text = username

        Glide.with(this)
            .load(avatarUrl)
            .apply(RequestOptions.circleCropTransform())
            .into(binding.imgDetailItemAvatar)
    }
}

