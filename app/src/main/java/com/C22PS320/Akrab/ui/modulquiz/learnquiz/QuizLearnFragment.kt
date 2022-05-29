package com.C22PS320.Akrab.ui.modulquiz.learnquiz

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.C22PS320.Akrab.databinding.FragmentQuizLearnBinding
import com.C22PS320.Akrab.network.response.ModuleQuizResponse
import com.C22PS320.Akrab.ui.camera.CameraActivity
import java.io.File

class QuizLearnFragment : Fragment() {

    private var _binding: FragmentQuizLearnBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuizLearnBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data = arguments?.getParcelable<ModuleQuizResponse>(DATAMODULEQUIZ)
        val pvt = arguments?.getInt(PIVOT)
        val mx = arguments?.getInt(MAX)
        val now = data?.data?.quiz?.get(pvt!!)
        binding.textView.text = now?.soal
        binding.goToQuiz.setOnClickListener {
            startCameraX()
        }
    }
    private fun startCameraX() {
        val intent = Intent(view?.context, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = it.data?.getSerializableExtra("picture") as File
            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean
            val result = BitmapFactory.decodeFile(myFile.path)
            binding.imageView2.setImageBitmap(result)
        }
    }

    companion object {
        const val DATAMODULEQUIZ: String = "data"
        const val PIVOT: String = "prev"
        const val MAX: String = "next"

        const val CAMERA_X_RESULT = 200
        @JvmStatic
        fun newInstance() =
            QuizLearnFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}