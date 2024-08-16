package com.example.store.fragment

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.store.activity.MainActivity
import com.example.store.R
import com.example.store.viewmodel.ViewModel
import java.io.*


class ProfileFragment : Fragment() {

    private lateinit var viewModel: ViewModel
    lateinit var btSaveImage: Button
    lateinit var btSaveName: Button
    lateinit var btSaveMail: Button
    lateinit var avatar: ImageView
    lateinit var edittextSave: EditText
    lateinit var tvName: TextView
    lateinit var tvMail: TextView

    lateinit var streamOut: OutputStream
    lateinit var streamIn: InputStream

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.profile_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        streamOut = context!!.openFileOutput("test.png", MODE_PRIVATE)
        streamIn = context!!.openFileInput("test.png")
        viewModel = ViewModelProvider(this).get(ViewModel::class.java)
        tvName.text = viewModel.getSharedPreference(requireContext(), "Name")
        tvMail.text = viewModel.getSharedPreference(requireContext(), "Mail")

        (activity as MainActivity?)!!.getUri().observe(viewLifecycleOwner, {
            val bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, it)
            // avatar.setImageURI(it)
            viewModel.putSharedPreference(
                requireContext(),
                "Avatar",
                it.toString()
            )
            viewModel.putSharedPreference(requireContext(), "path", it.toString())

            avatar.setImageBitmap(bitmap)
            streamOut.close()
        })

        (activity as MainActivity?)!!.start(1)
        (activity as MainActivity?)!!.getBitmap().observe(viewLifecycleOwner, {
            avatar.setImageBitmap(it)
        })

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btSaveImage = view.findViewById(R.id.btSaveImage)
        btSaveName = view.findViewById(R.id.btSaveName)
        btSaveMail = view.findViewById(R.id.btSaveMail)
        avatar = view.findViewById(R.id.imageView3)
        edittextSave = view.findViewById(R.id.editTextSave)
        tvName = view.findViewById(R.id.textViewName)
        tvMail = view.findViewById(R.id.textViewMail)


        btSaveName.setOnClickListener {
            if (edittextSave.visibility == EditText.INVISIBLE) {
                edittextSave.visibility = EditText.VISIBLE
                edittextSave.text.clear()
                btSaveName.text = "Сохранить"
                btSaveMail.isEnabled = false
            } else {
                edittextSave.visibility = EditText.INVISIBLE
                viewModel.putSharedPreference(
                    requireContext(),
                    "Name",
                    edittextSave.text.toString()
                )
                tvName.text = edittextSave.text.toString()
                btSaveName.text = "Изменить"
                btSaveMail.isEnabled = true
            }
            // btSaveName.text=(activity as MainActivity?)!!.getUri().toString()
        }

        btSaveMail.setOnClickListener {
            if (edittextSave.visibility == EditText.INVISIBLE) {
                edittextSave.visibility = EditText.VISIBLE
                btSaveMail.text = "Сохранить"
                edittextSave.text.clear()
                btSaveName.isEnabled = false
            } else {
                edittextSave.visibility = EditText.INVISIBLE
                viewModel.putSharedPreference(
                    requireContext(),
                    "Mail",
                    edittextSave.text.toString()
                )
                tvMail.text = edittextSave.text.toString()
                btSaveMail.text = "Изменить"
                btSaveName.isEnabled = true
            }
        }

        btSaveImage.setOnClickListener {
            (activity as MainActivity?)!!.start()
        }


    }
}

