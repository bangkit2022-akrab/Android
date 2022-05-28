package com.C22PS320.Akrab.ui.main.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.C22PS320.Akrab.databinding.FragmentHomeBinding
import com.C22PS320.Akrab.ui.kelas.ClassActivity

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        val EXTRA_NAME: String? = "NAME"

        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {
//                    binding.tvUsername.text = this.getString("name")
                }
            }
    }
    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.hide()
        if(arguments!= null) {
            binding.tvUsername.text = arguments?.getString(EXTRA_NAME)
        }
        binding.btnKelashuruf.setOnClickListener {
            val i = Intent(view?.context, ClassActivity::class.java)
            i.putExtra("class","huruf")
            startActivity(i)
        }
        binding.btnKelasangka.setOnClickListener {
            val i = Intent(view?.context, ClassActivity::class.java)
            i.putExtra("class","angka")
            startActivity(i)
        }
    }
}