package com.C22PS320.Akrab.ui.main.modul

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.C22PS320.Akrab.R
import com.C22PS320.Akrab.adapter.ModuleAdapter
import com.C22PS320.Akrab.databinding.FragmentModulBinding
import com.C22PS320.Akrab.network.response.DataItemModule
import com.C22PS320.Akrab.network.response.ModuleResponse
import com.bumptech.glide.Glide

class ModulFragment : Fragment() {
    private var _binding: FragmentModulBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentModulBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    companion object {
        val TOKEN: String? = "TOKEN"
        @JvmStatic
        fun newInstance() =
            ModulFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.hide()

            val data: ModuleResponse? = arguments?.get(TOKEN) as? ModuleResponse
        if (data == null){
            binding.progressBar.visibility = View.VISIBLE
        }else {
            binding.progressBar.visibility = View.GONE
            val layoutManager = GridLayoutManager(view?.context, 4)
            binding.rvModule.layoutManager = layoutManager
            val itemDecoration = DividerItemDecoration(view?.context, layoutManager.orientation)
            binding.rvModule.addItemDecoration(itemDecoration)
            setModuleData(data?.data)
        }
    }
    private fun setModuleData(UsersData: List<DataItemModule?>?) {
        val adapter = ModuleAdapter(UsersData)
        binding.rvModule.adapter = adapter
        adapter.setOnItemClickCallback(object : ModuleAdapter.OnItemClickCallback {
            override fun onItemClicked(data: DataItemModule) {
                startDialog(data)
            }
        })
    }
    private fun startDialog(data: DataItemModule){
        var dialogView =  layoutInflater.inflate(R.layout.module_layout_dialog, null)
        val customDialog = AlertDialog.Builder(view?.context ?: requireContext())
            .setView(dialogView)
            .show()
        val pic = dialogView.findViewById<ImageView>(R.id.img_Module)
        val title = dialogView.findViewById<TextView>(R.id.textView)
        val desc = dialogView.findViewById<TextView>(R.id.textView2)
        Glide.with(view?.context ?: requireContext())
            .load("${data.image}")
            .into(pic)
        title.text = data.materi
        desc.text = data.description
        val btDismiss = dialogView.findViewById<ImageButton>(R.id.imageButton)
        btDismiss.setOnClickListener {
            customDialog.cancel()
            customDialog.dismiss()
        }
    }
}