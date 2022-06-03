package com.C22PS320.Akrab.ui.main.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.C22PS320.Akrab.databinding.FragmentNewsBinding

class NewsFragment : Fragment() {
    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    companion object {

        @JvmStatic
        fun newInstance() = NewsFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.hide()
//        (activity as AppCompatActivity).supportActionBar?.show()
//        (activity as AppCompatActivity).supportActionBar?.title = "News"

    }
}