package com.C22PS320.Akrab.ui.main.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.C22PS320.Akrab.R
import com.C22PS320.Akrab.databinding.FragmentHomeBinding
import com.C22PS320.Akrab.databinding.FragmentProfileBinding
import com.C22PS320.Akrab.ui.kelas.ClassActivity
import com.C22PS320.Akrab.ui.main.home.HomeFragment

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
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
            ProfileFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.hide()
        if(arguments!= null) {
            binding.tvUsername.text = arguments?.getString(EXTRA_NAME)
        }
        binding.btnLogout.setOnClickListener {
            val i = Intent(view?.context, ClassActivity::class.java)
            i.putExtra("class","huruf")
            startActivity(i)
        }
//        (activity as AppCompatActivity).supportActionBar?.show()
//        (activity as AppCompatActivity).supportActionBar?.title = "Profile"

    }
}