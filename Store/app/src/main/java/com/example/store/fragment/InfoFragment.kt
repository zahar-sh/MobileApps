package com.example.store.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.store.R
import com.example.store.viewmodel.ViewModel
import com.squareup.picasso.Picasso

class InfoFragment : Fragment() {

    lateinit var img: ImageView
    var kl: Int = 0
    lateinit var textName: TextView
    lateinit var textFirm: TextView
    lateinit var textPrice: TextView
    lateinit var textAbout: TextView
    lateinit var btVatchWeb: Button
    lateinit var navigation: NavController
    val bundle = Bundle()

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.info_fragment, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ViewModel::class.java)
        kl = arguments?.getInt("id")!!
        viewModel.initial(kl)
        viewModel.getOneItem().observe(viewLifecycleOwner, {
            Picasso.get().load(it.img).into(img)
            textName.text = it.name
            textFirm.text = it.producer_name
            textPrice.text = it.price.toString()
            textAbout.text = it.about
            bundle.putString("URL", it.href)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigation = Navigation.findNavController(view)
        img = view.findViewById(R.id.imageView2)
        textName = view.findViewById(R.id.textView3)
        textFirm = view.findViewById(R.id.textView4)
        textPrice = view.findViewById(R.id.textView5)
        textAbout = view.findViewById(R.id.textView6)
        btVatchWeb = view.findViewById(R.id.btWeb)
        //val toast = Toast.makeText(context, kl.toString(), Toast.LENGTH_SHORT)
        //toast.show()

        btVatchWeb.setOnClickListener {
            navigation.navigate(R.id.webFragment, bundle)
        }

    }


}