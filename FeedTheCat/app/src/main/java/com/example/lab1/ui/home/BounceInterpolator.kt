package com.example.lab1.ui.home

import android.view.animation.Interpolator

internal class BounceInterpolator(private val mAmplitude: Double, private val mFrequency: Double) :
    Interpolator {
    override fun getInterpolation(time: Float): Float {
        return (-1 * Math.pow(Math.E, -time / mAmplitude) *
                Math.cos(mFrequency * time) + 1).toFloat()
    }
}