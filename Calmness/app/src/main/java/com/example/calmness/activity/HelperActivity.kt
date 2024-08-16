package com.example.calmness.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.example.calmness.R

class HelperActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_helper)

        val imageList = ArrayList<SlideModel>()
        imageList.add(
            SlideModel(
                R.drawable.slide1,
                getString(R.string.slide1_title)
            )
        )
        imageList.add(
            SlideModel(
                R.drawable.slide2,
                getString(R.string.slide2_title)
            )
        )
        imageList.add(
            SlideModel(
                R.drawable.slide3,
                getString(R.string.slide3_title)
            )
        )
        imageList.add(
            SlideModel(
                R.drawable.slide4,
                getString(R.string.slide4_title)
            )
        )
        imageList.add(
            SlideModel(
                R.drawable.slide5,
                getString(R.string.slide5_title)
            )
        )
        val imageSlider = findViewById<ImageSlider>(R.id.image_slider)
        imageSlider.setImageList(imageList)
    }
}