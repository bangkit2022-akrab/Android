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
    private val randomText: Array<String> = arrayOf("Study is easy and fun right?",
        "Arkana, Ayu, Fian, Iskan, Randy, and Anggra is my Creator",
        "Uhmm.. do you like eating lizard?",
        "I prefer worm, worm is good",
        "How's your day?",
        "Akrab is made with full of love",
        "Sometimes im feel lonely",
        "Do you know how to make sign for Z?",
        "Ah~~ bangkit program aye",
        "Can you stop touching me")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentModulBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        const val TOKEN: String = "TOKEN"
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
            binding.textView5.text = randomText[(randomText.indices).random()]
            val layoutManager = GridLayoutManager(view?.context, 4)
            binding.rvModule.layoutManager = layoutManager
            setModuleData(data.data)
            binding.imageView7.setOnClickListener {
                binding.textView5.text = randomText[(randomText.indices).random()]
            }
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
        val dialogView =  layoutInflater.inflate(R.layout.module_layout_dialog, null)
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