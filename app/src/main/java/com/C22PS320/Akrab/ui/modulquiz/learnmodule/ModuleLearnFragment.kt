package com.C22PS320.Akrab.ui.modulquiz.learnmodule

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.C22PS320.Akrab.R
import com.C22PS320.Akrab.databinding.FragmentModuleLearnBinding
import com.C22PS320.Akrab.network.response.ModuleQuizResponse
import com.C22PS320.Akrab.ui.modulquiz.learnquiz.QuizLearnFragment
import com.bumptech.glide.Glide

class ModuleLearnFragment : Fragment() {

    private var _binding: FragmentModuleLearnBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentModuleLearnBinding.inflate(inflater, container, false)
        return binding.root
    }
    companion object {
        const val DATAMODULEQUIZ: String = "data"
        const val PIVOT: String = "prev"
        const val MAX: String = "next"

        @JvmStatic
        fun newInstance() =
            ModuleLearnFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnGoToQuiz.visibility = View.GONE
        val data = arguments?.getParcelable<ModuleQuizResponse>(DATAMODULEQUIZ)
        val pvt = arguments?.getInt(PIVOT)
        val mx = arguments?.getInt(MAX)
        val now = data?.data?.modul?.get(pvt!!)
        Glide.with(view.context)
            .load("${now?.image}")
            .into(binding.imgHandModule)
        binding.textView.text = now?.materi
        binding.textView2.text = now?.description
        if (pvt==0){
            binding.btnBeforeModule.visibility = View.GONE
        }else if (pvt==mx!!-1){
            binding.btnNextModule.visibility = View.GONE
            binding.btnGoToQuiz.visibility = View.VISIBLE
        }
        binding.btnNextModule.setOnClickListener {
            changeModule(data,pvt,mx,true)
        }
        binding.btnBeforeModule.setOnClickListener {
            changeModule(data,pvt,mx,false)
        }
        binding.btnGoToQuiz.setOnClickListener {
            val mFragmentManager = parentFragmentManager
            val mHomeFragment = QuizLearnFragment.newInstance()
            val mBundle = Bundle()
            mBundle.putParcelable(DATAMODULEQUIZ, data)
            mBundle.putInt(PIVOT, 0)
            mBundle.putInt(MAX, data?.data?.quiz?.size!!-1)
            mHomeFragment.arguments = mBundle
            val fragment = mFragmentManager.findFragmentByTag(ModuleLearnFragment::class.java.simpleName)
            if (fragment is ModuleLearnFragment) {
                mFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_container, mHomeFragment, ModuleLearnFragment::class.java.simpleName)
                    .commit()
            }
        }
    }
    private fun changeModule(data: ModuleQuizResponse?, pivot: Int?, max: Int?, check: Boolean) {
        val mFragmentManager = parentFragmentManager
        val mHomeFragment = newInstance()
        val mBundle = Bundle()
        mBundle.putParcelable(DATAMODULEQUIZ, data)
        if (check){
            mBundle.putInt(PIVOT, pivot!!+1)
        }else {
            mBundle.putInt(PIVOT, pivot!!-1)
        }
        mBundle.putInt(MAX, max!!)
        mHomeFragment.arguments = mBundle
        val fragment = mFragmentManager.findFragmentByTag(ModuleLearnFragment::class.java.simpleName)
        if (fragment is ModuleLearnFragment) {
            mFragmentManager
                .beginTransaction()
                .replace(R.id.frame_container, mHomeFragment, ModuleLearnFragment::class.java.simpleName)
                .commit()
        }
    }

}