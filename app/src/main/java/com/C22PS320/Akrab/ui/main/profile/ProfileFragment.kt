package com.C22PS320.Akrab.ui.main.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.C22PS320.Akrab.R
import com.C22PS320.Akrab.databinding.FragmentProfileBinding
import com.C22PS320.Akrab.preferences.SettingPreferences
import com.C22PS320.Akrab.preferences.ViewModelFactory
import com.C22PS320.Akrab.ui.login.LoginActivity
import com.C22PS320.Akrab.ui.main.MainActivity.Companion.dataStore
import com.C22PS320.Akrab.ui.main.MainViewModel
import com.bumptech.glide.Glide

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
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
            binding.tvEmail.text = arguments?.getString(EXTRA_EMAIL)
        }
        val mainViewModel = ViewModelProvider(this, ViewModelFactory(SettingPreferences.getInstance(
            (activity as AppCompatActivity).dataStore))).get(
            MainViewModel::class.java
        )
        binding.btnTerms?.setOnClickListener {
            val dialogView: View = layoutInflater.inflate(R.layout.module_layout_dialog, null)
            val customDialog = AlertDialog.Builder(view?.context ?: requireContext())
                .setView(dialogView)
                .show()
            val btDismiss = dialogView.findViewById<ImageButton>(R.id.imageButton)
            val txtExplain = dialogView.findViewById<TextView>(R.id.textView2)
            val img = dialogView.findViewById<ImageView>(R.id.img_Module)
            Glide.with(requireContext())
                .load(R.drawable.akrab)
                .into(img)
            txtExplain.text = getText(R.string.term2)
            btDismiss.setOnClickListener {
                customDialog.cancel()
                customDialog.dismiss()
            }
        }
        binding.btnAbout?.setOnClickListener {
            val dialogView: View = layoutInflater.inflate(R.layout.module_layout_dialog, null)
            val customDialog = AlertDialog.Builder(view?.context ?: requireContext())
                .setView(dialogView)
                .show()
            val btDismiss = dialogView.findViewById<ImageButton>(R.id.imageButton)
            val txtExplain = dialogView.findViewById<TextView>(R.id.textView2)
            val img = dialogView.findViewById<ImageView>(R.id.img_Module)
            Glide.with(requireContext())
                .load(R.drawable.akrabxbangkit)
                .into(img)
            txtExplain.text = getText(R.string.about)
            btDismiss.setOnClickListener {
                customDialog.cancel()
                customDialog.dismiss()
            }
        }
        binding.btnLogout.setOnClickListener {
            mainViewModel.deleteAllDatas()
            val i = Intent(requireContext(), LoginActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
        }

    }
}