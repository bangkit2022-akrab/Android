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
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val EXTRA_NAME: String = "NAME"

        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
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
        binding.textView5.text = randomText[(randomText.indices).random()]
        binding.btnKelashuruf.setOnClickListener {
            val i = Intent(view?.context, ClassActivity::class.java)
            i.putExtra("class","letter")
            startActivity(i)
        }
        binding.imageView7.setOnClickListener {
            binding.textView5.text = randomText[(randomText.indices).random()]
        }
        binding.btnKelasangka.setOnClickListener {
            val i = Intent(view?.context, ClassActivity::class.java)
            i.putExtra("class","number")
            startActivity(i)
        }
    }
}