package com.example.store.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.store.R

class AboutFragment : Fragment() {
    lateinit var btList: Button
    lateinit var btHistory: Button
    lateinit var btProfile: Button
    lateinit var navigation: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.about_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btList = view.findViewById(R.id.btList)
        btHistory = view.findViewById(R.id.btHistory)
        btProfile = view.findViewById(R.id.btProfile)
        navigation = Navigation.findNavController(view)

        btList.setOnClickListener {
            navigation.navigate(R.id.mainFragment)
        }
        btHistory.setOnClickListener {
            navigation.navigate(R.id.historyFragment)
        }
        btProfile.setOnClickListener {
            navigation.navigate(R.id.profileFragment)
        }
    }
}