package com.example.store.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.store.R
import android.text.Editable

import android.text.TextWatcher
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.store.entity.HistoryItem
import com.example.store.entity.Item
import com.example.store.adapter.ItemAdapter
import com.example.store.viewmodel.ViewModel
import java.util.*


class MainFragment : Fragment(), ToMainFragment {

    lateinit var adapter: ItemAdapter
    lateinit var recyclerResult: RecyclerView
    lateinit var search: SearchView
    lateinit var btWomen: Button
    lateinit var btMan: Button
    lateinit var editPrice1: EditText
    lateinit var editPrice2: EditText
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
        return inflater.inflate(R.layout.main_fragment, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ViewModel::class.java)
        viewModel.getdoc(requireContext())
        viewModel.getItem().observe(viewLifecycleOwner, {
            adapter.setData(it)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ItemAdapter(requireContext())
        adapter.setListener(this)

        navigation = Navigation.findNavController(view)
        recyclerResult = view.findViewById(R.id.recyclerView)
        search = view.findViewById(R.id.viewSearch)
        btWomen = view.findViewById(R.id.BtWoman)
        btMan = view.findViewById(R.id.BtMan)
        editPrice1 = view.findViewById(R.id.editTextNumber)
        editPrice2 = view.findViewById(R.id.editTextNumber2)
        recyclerResult.layoutManager = LinearLayoutManager(requireContext())
        recyclerResult.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                RecyclerView.VERTICAL
            )
        )
        recyclerResult.adapter = adapter

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isNotEmpty()) {
                    viewModel.filter(newText)
                } else {
                    viewModel.deleteFilter()
                }
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

        })
        btWomen.setOnClickListener {
            viewModel.filterWoman()
        }

        btMan.setOnClickListener {
            viewModel.filterMan()
        }

        editPrice1.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if ((editPrice1.text.isNotEmpty()) && (editPrice2.text.isNotEmpty())) {
                    viewModel.filterPrice(editPrice1.text.toString(), editPrice2.text.toString())
                } else {
                    viewModel.deleteFilter()
                }
                // yourEditText...
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
        editPrice2.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if ((editPrice1.text.isNotEmpty()) && (editPrice2.text.isNotEmpty())) {
                    viewModel.filterPrice(editPrice1.text.toString(), editPrice2.text.toString())
                } else {
                    viewModel.deleteFilter()
                }
                // yourEditText...
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

    }

    override fun onClick(item: Item) {
        bundle.putInt("id", item.id)
        viewModel.addHistory(
            HistoryItem(
                item.id,
                Calendar.getInstance().time.toString(),
                item.name
            )
        )
        navigation.navigate(R.id.infoFragment, bundle)
    }

}


