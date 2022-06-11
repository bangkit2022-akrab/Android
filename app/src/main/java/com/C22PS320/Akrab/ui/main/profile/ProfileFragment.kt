package com.C22PS320.Akrab.ui.main.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.C22PS320.Akrab.R
import com.C22PS320.Akrab.databinding.FragmentHomeBinding
import com.C22PS320.Akrab.databinding.FragmentProfileBinding
import com.C22PS320.Akrab.preferences.SettingPreferences
import com.C22PS320.Akrab.preferences.ViewModelFactory
import com.C22PS320.Akrab.ui.kelas.ClassActivity
import com.C22PS320.Akrab.ui.login.LoginActivity
import com.C22PS320.Akrab.ui.main.MainActivity
import com.C22PS320.Akrab.ui.main.MainActivity.Companion.dataStore
import com.C22PS320.Akrab.ui.main.MainViewModel
import com.C22PS320.Akrab.ui.main.home.HomeFragment

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {
        val EXTRA_NAME: String = "NAME"
        val EXTRA_EMAIL: String = "MAIL"
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
            binding.TvEmail.text = arguments?.getString(EXTRA_EMAIL)
        }
        val mainViewModel = ViewModelProvider(this, ViewModelFactory(SettingPreferences.getInstance(
            (activity as AppCompatActivity).dataStore),requireActivity())).get(
            MainViewModel::class.java
        )
        binding.btnLogout.setOnClickListener {
            mainViewModel.deleteAllDatas()
            val i = Intent(requireContext(), LoginActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
        }

    }
}