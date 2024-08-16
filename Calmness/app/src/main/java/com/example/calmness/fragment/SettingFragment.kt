package com.example.calmness.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.calmness.R
import com.example.calmness.activity.AboutActivity
import com.example.calmness.activity.HelperActivity

class SettingFragment : Fragment() {

    lateinit var btAbout: Button
    lateinit var btHelper: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.setting_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btAbout = view.findViewById(R.id.buttonAbout)
        btHelper = view.findViewById(R.id.buttonManual)
        btAbout.setOnClickListener {
            val intent = Intent(activity, AboutActivity::class.java)
            startActivity(intent)
        }
        btHelper.setOnClickListener {
            val intent = Intent(activity, HelperActivity::class.java)
            startActivity(intent)
        }
    }
}