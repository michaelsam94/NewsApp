package com.example.newsapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentDetailsBinding
import com.example.newsapp.databinding.FragmentNewsBinding
import com.example.newsapp.utils.dateFormat
import com.example.newsapp.utils.dateToTimeFormat
import com.squareup.picasso.Picasso

class DetailsFragment: Fragment(R.layout.fragment_details) {

    private lateinit var binding: FragmentDetailsBinding
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindData()
    }


    private fun bindData() {
        Picasso.get().load(args.article.urlToImage)
            .into(binding.articleImage)

        binding.titleTxt.text = args.article.title
        binding.authorNameTxt.text = args.article.author
        binding.dateTxt.text = dateFormat(args.article.publishedAt)
        binding.articleTimeAgo.text = dateToTimeFormat(args.article.publishedAt)
        binding.descriptionTxt.text = args.article.description
    }
}