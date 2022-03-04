package com.lemondo.spacexmissions.ui.views.details

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.lemondo.spacexmissions.databinding.FragmentLaunchesBinding


class DetailsFragment : Fragment() {

    private val args: DetailsFragmentArgs by navArgs()

    private lateinit var binding: FragmentLaunchesBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLaunchesBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val articleUrl = args.articleUrl
        val videoLink = args.videoLink
        val wikipedia = args.wikipedia



        binding.webView.apply {
            when {
                !articleUrl.isNullOrEmpty() -> {
                    webViewClient = WebViewClient()
                    loadUrl(articleUrl)
                    settings.javaScriptEnabled = true
                    loadUrl(articleUrl)

                }
                !videoLink.isNullOrEmpty() -> {
                    webViewClient = WebViewClient()
                    loadUrl(videoLink)
                    settings.javaScriptEnabled = true
                    args.videoLink?.let { loadUrl(it) }
                }

                !wikipedia.isNullOrEmpty() -> {
                    webViewClient = WebViewClient()
                    loadUrl(wikipedia)
                    settings.javaScriptEnabled = true
                    args.wikipedia?.let { loadUrl(it) }
                }
            }

        }
    }

}