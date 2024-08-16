package com.example.lab1.ui.statistic

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.lab1.databinding.FragmentStatisticBinding
import com.example.lab1.model.LogStatistic
import com.example.lab1.service.DatabaseManager
import com.example.lab1.ui.home.HomeFragment

class StatisticFragment : Fragment() {

    private var _binding: FragmentStatisticBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val slideshowViewModel =
            ViewModelProvider(this)
                .get(StatisticViewModel::class.java)

        _binding = FragmentStatisticBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textStatistic
        val db = DatabaseManager(requireContext().applicationContext)
        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat("M/d/y H:m:ss")
        val formattedDate = formatter.format(date)
        val logStatistic = LogStatistic(HomeFragment.countCat, formattedDate)
        db.openDb()
        if (HomeFragment.countCat != 0) {
            db.insert(logStatistic)
            HomeFragment.countCat = 0
        }
        val listStat = db.read().reversed();
        if (listStat.isNotEmpty()) {
            textView.text = listStat.joinToString("\n")
        } else {
            textView.text = "Statistics is empty, feed the cat)"
        }
        db.closeDb()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}