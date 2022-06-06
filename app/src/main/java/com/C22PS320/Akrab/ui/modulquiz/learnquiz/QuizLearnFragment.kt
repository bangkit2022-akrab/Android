package com.C22PS320.Akrab.ui.modulquiz.learnquiz

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.C22PS320.Akrab.R
import com.C22PS320.Akrab.databinding.FragmentQuizLearnBinding
import com.C22PS320.Akrab.ml.AlphabetModel
import com.C22PS320.Akrab.network.response.ModuleQuizResponse
import com.C22PS320.Akrab.network.response.QuizItem
import com.C22PS320.Akrab.preferences.rotateBitmap
import com.C22PS320.Akrab.ui.camera.CameraActivity
import com.C22PS320.Akrab.ui.modulquiz.learnmodule.ModuleLearnFragment
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.File
import java.nio.ByteBuffer
import java.nio.ByteOrder

class QuizLearnFragment : Fragment() {

    private var _binding: FragmentQuizLearnBinding? = null
    private val binding get() = _binding!!
    private val hurufLableList: Array<String> =  arrayOf("A","B","C","D","E","F","G","H","I","K",
        "L","M","N","O","P","Q","R","S",
        "T","U","V","W","X","Y")

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
        binding.btnNextQuiz.visibility = View.GONE
        binding.btnNextQuiz.setOnClickListener {
            changeQuiz(data,pvt,mx)
        }
        val launcherIntentCameraX = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == CAMERA_X_RESULT) {
                val myFile = it.data?.getSerializableExtra("picture") as File
                val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean
                var result = BitmapFactory.decodeFile(myFile.path)
                result = rotateBitmap(result, isBackCamera)
                val scaledBitmap = Bitmap.createScaledBitmap(result, INPUT_SIZE, INPUT_SIZE, false)
                binding.imageView2.setImageBitmap(result)
                var resized = convertBitmapToByteBuffer(scaledBitmap)
                when (data?.data?.tipe){
                    "huruf"-> {
                        val model = AlphabetModel.newInstance(view?.context ?: requireContext())

                        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 150, 150, 3), DataType.FLOAT32)
                        inputFeature0.loadBuffer(resized)

                        val outputs = model.process(inputFeature0)
                        val outputFeature0 = outputs.outputFeature0AsTensorBuffer

                        model.close()
                        val asu = outputFeature0.floatArray
                        val resultAi = getClassificationResult(asu,hurufLableList)

                        if (resultAi != now?.jawaban){
                            if (pvt!=mx) {
                                startDialog(true)
                                binding.btnNextQuiz.visibility = View.VISIBLE
                            }else{
                                binding.btnNextQuiz.visibility = View.GONE
                                endQuiz()
                            }
                        }else{
                            startDialog(false)
                        }
                    }
                }
            }
        }
        binding.btnPhotoQuiz.setOnClickListener {
            startCameraX(launcherIntentCameraX)
        }

    }
    private fun startCameraX(launcherIntentCameraX: ActivityResultLauncher<Intent>) {
        val intent = Intent(view?.context, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }
    private fun startDialog(stat: Boolean){
        var dialogView: View
        if (stat){
            dialogView = layoutInflater.inflate(R.layout.true_layout_dialog, null)
        }else{
            dialogView = layoutInflater.inflate(R.layout.wrong_layout_dialog, null)
        }
        val customDialog = AlertDialog.Builder(view?.context ?: requireContext())
            .setView(dialogView)
            .show()
        val btDismiss = dialogView.findViewById<Button>(R.id.button)
        btDismiss.setOnClickListener {
            customDialog.cancel()
            customDialog.dismiss()
        }
    }
    private fun endQuiz(){
        var dialogView = layoutInflater.inflate(R.layout.finish_layout_dialog, null)
        val customDialog = AlertDialog.Builder(view?.context ?: requireContext())
            .setView(dialogView)
            .show()
        val btDismiss = dialogView.findViewById<Button>(R.id.button)
        btDismiss.setOnClickListener {
            customDialog.cancel()
            customDialog.dismiss()
            activity?.finish()
        }
    }
    private fun changeQuiz(data: ModuleQuizResponse?, pivot: Int?, max: Int?) {
        val mFragmentManager = parentFragmentManager
        val mHomeFragment = newInstance()
        val mBundle = Bundle()
        mBundle.putParcelable(DATAMODULEQUIZ, data)
        mBundle.putInt(PIVOT, pivot!!+1)
        mBundle.putInt(MAX, max!!)
        mHomeFragment.arguments = mBundle
            mFragmentManager
                .beginTransaction()
                .replace(R.id.frame_container, mHomeFragment, QuizLearnFragment::class.java.simpleName)
                .commit()
    }
    private fun getClassificationResult(arr: FloatArray, data: Array<String>): String {
        var maxPos = 0
        var maxConfidence = 0.0f

        for (i in 0 until arr.size){
            if (arr[i]> maxConfidence){
                maxConfidence = arr[i]
                maxPos = i
            }
        }
        return data[maxPos]
    }
    private fun convertBitmapToByteBuffer(bitmap: Bitmap): ByteBuffer {
        val byteBuffer = ByteBuffer.allocateDirect(4 * INPUT_SIZE * INPUT_SIZE * PIXEL_SIZE)
        byteBuffer.order(ByteOrder.nativeOrder())
        val intValues = IntArray(INPUT_SIZE * INPUT_SIZE)

        bitmap.getPixels(intValues, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)

        var pixel = 0
        for (i in 0 until INPUT_SIZE) {
            for (j in 0 until INPUT_SIZE) {
                val input = intValues[pixel++]

                byteBuffer.putFloat((((input.shr(16)  and 0xFF) - IMAGE_MEAN) / IMAGE_STD))
                byteBuffer.putFloat((((input.shr(8) and 0xFF) - IMAGE_MEAN) / IMAGE_STD))
                byteBuffer.putFloat((((input and 0xFF) - IMAGE_MEAN) / IMAGE_STD))
            }
        }
        return byteBuffer
    }

    companion object {
        const val DATAMODULEQUIZ: String = "data"
        const val PIVOT: String = "prev"
        const val MAX: String = "next"
        private val INPUT_SIZE = 150
        private val PIXEL_SIZE: Int = 3
        private val IMAGE_MEAN = 0
        private val IMAGE_STD = 255.0f


        const val CAMERA_X_RESULT = 200
        @JvmStatic
        fun newInstance() =
            QuizLearnFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}