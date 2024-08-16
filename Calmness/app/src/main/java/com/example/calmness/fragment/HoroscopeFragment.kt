package com.example.calmness.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.calmness.R

class HoroscopeFragment : Fragment() {
    private lateinit var browser: WebView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.horoscope_fragment, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        browser = view.findViewById(R.id.webview)
        browser.webViewClient = WebViewClient();
        browser.loadUrl("https://www.astrostar.ru/horoscopes/main/oven/day.html");
    }
}