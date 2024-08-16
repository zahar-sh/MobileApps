package com.example.store.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.store.R
import com.example.store.viewmodel.ViewModel

class WebFragment : Fragment() {

    lateinit var browser: WebView

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.web_fragment, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        browser = view.findViewById(R.id.webview)
        browser.setWebViewClient(WebViewClient())
        browser.loadUrl(arguments?.getString("URL")!!)
    }
}