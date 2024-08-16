package com.example.calmness.fragment

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.calmness.R
import com.example.calmness.viewmodel.ViewModel
import java.lang.Math.floor
import java.util.*

class MainFragment : Fragment() {

    lateinit var btIKT: ImageButton
    lateinit var editHeightIKT: EditText
    lateinit var editWeightIKT: EditText
    lateinit var resultIKT: TextView
    lateinit var mood: TextView

    lateinit var viewModel: ViewModel

    lateinit var btHappy: ImageButton
    lateinit var btHormal: ImageButton
    lateinit var btBad: ImageButton
    lateinit var btIrritable: ImageButton
    var mMediaPlayer: MediaPlayer? = null


    lateinit var btPlay: ImageButton
    lateinit var btPause: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ViewModel::class.java)
        viewModel.getMood().observe(viewLifecycleOwner, {
            mood.text = it
        })
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btIKT = view.findViewById(R.id.imageButtonIKT)
        editHeightIKT = view.findViewById(R.id.editTextHeightIKT)
        editWeightIKT = view.findViewById(R.id.editTextWeightIKT)
        resultIKT = view.findViewById(R.id.textViewResultIKT)
        btHappy = view.findViewById(R.id.imageButtonHappy)
        btHormal = view.findViewById(R.id.imageButtonNormal)
        btBad = view.findViewById(R.id.imageButtonBad)
        btIrritable = view.findViewById(R.id.imageButtonIrritable)
        mood = view.findViewById(R.id.textViewHelp)
        btPlay = view.findViewById(R.id.imageButtonPlay)
        btPause = view.findViewById(R.id.imageButtonPause)
        btIKT.setOnClickListener {
            if (editHeightIKT.text != null && editWeightIKT.text != null) {
                val height: Double = editHeightIKT.text.toString().toDouble() / 100
                val weight: Double = editWeightIKT.text.toString().toDouble()
                resultIKT.text = (floor((weight / (height * height)) * 1000.0) / 1000.0).toString()
            }
        }

        btHappy.setOnClickListener {
            viewModel.happyMood()
            setMediaPlayer(R.raw.happyd, R.raw.happyn)
        }
        btHormal.setOnClickListener {
            viewModel.normalMood()
            setMediaPlayer(R.raw.normald, R.raw.normaln)
        }
        btBad.setOnClickListener {
            viewModel.badMood()
            setMediaPlayer(R.raw.badd, R.raw.badn)
        }
        btIrritable.setOnClickListener {
            viewModel.irritableMood()
            setMediaPlayer(R.raw.irrd, R.raw.irrn)
        }
        btPlay.setOnClickListener {
            playSound()
        }
        btPause.setOnClickListener {
            pauseSound()

        }
    }

    private fun setMediaPlayer(idd: Int, idn: Int) {
        if (mMediaPlayer?.isPlaying == true) {
            mMediaPlayer?.stop()
        }
        mMediaPlayer = MediaPlayer.create(
            requireContext(),
            if (Calendar.getInstance().time.hours <= 12) {
                idd
            } else {
                idn
            }
        )
    }

    private fun playSound() {
        if (mMediaPlayer == null) {
            mMediaPlayer!!.isLooping = true
        }
        mMediaPlayer!!.start()
    }

    private fun pauseSound() {
        if (mMediaPlayer?.isPlaying == true) {
            mMediaPlayer?.pause()
        }
    }

}