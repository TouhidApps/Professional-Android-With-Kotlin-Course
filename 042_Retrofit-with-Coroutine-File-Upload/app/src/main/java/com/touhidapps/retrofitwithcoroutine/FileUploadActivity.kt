package com.touhidapps.retrofitwithcoroutine

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.dhaval2404.imagepicker.ImagePicker
import com.touhidapps.retrofitwithcoroutine.databinding.ActivityFileUploadBinding
import com.touhidapps.retrofitwithcoroutine.model.PhotoUploadResponse
import com.touhidapps.retrofitwithcoroutine.model.UserUploadResponse
import com.touhidapps.retrofitwithcoroutine.networkService.ProgressRequestBody
import com.touhidapps.retrofitwithcoroutine.networkService.RetrofitClient
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import java.io.File

class FileUploadActivity : AppCompatActivity() {

    lateinit var binding: ActivityFileUploadBinding
    private val items: ArrayList<UserUploadResponse> = arrayListOf()
    private val mAdapter = UserUploadAdapter(items)
    private var mProfileUri: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFileUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        // init recyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.recyclerView.adapter = mAdapter

        loadPhotoDataList()

        binding.progressBar.visibility = View.GONE
        binding.tvProgress.visibility = View.GONE

        binding.ivBtnAddPhoto.setOnClickListener {

            ImagePicker.with(this)
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(512, 512)	//Final image resolution will be less than 1080 x 1080(Optional)
                .createIntent { intent ->
                    startForProfileImageResult.launch(intent)
                }

        }

        binding.btnUpload.setOnClickListener {

            val name = binding.etName.text.toString()
            val title = binding.etTitle.text.toString()

            if (mProfileUri == null) {
                Toast.makeText(this@FileUploadActivity, "Choose a photo", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            uploadUserPhoto(mProfileUri, name, title) {
                runOnUiThread {
                    Toast.makeText(this@FileUploadActivity, it.result, Toast.LENGTH_SHORT).show()
                    loadPhotoDataList()
                }
            }


        } // btnUpload

    } // onCreate

    private fun loadPhotoDataList() {

        lifecycleScope.launch {

            val resp: List<UserUploadResponse> = RetrofitClient.retrofit.getUploadedPhotoData()
            println("Response: " + resp)
            items.clear()
            items.addAll(resp)
            mAdapter.notifyDataSetChanged()

        }


    } // loadPhotoDataList

    private fun uploadUserPhoto(mFileUrl: String?, mName: String, mPhotoTitle: String, success: (PhotoUploadResponse) -> Unit) {

        binding.progressBar.visibility = View.VISIBLE
        binding.tvProgress.visibility = View.VISIBLE

        val requestBody = MultipartBody.Builder().apply {
            setType(MultipartBody.FORM)

            addFormDataPart("name", mName)
            addFormDataPart("photoTitle", mPhotoTitle)

            val profilePicFile = File(mFileUrl)
            addPart(MultipartBody.Part.createFormData("my_profile_pic",
                profilePicFile.name,
                ProgressRequestBody(
                    profilePicFile,
                    object : ProgressRequestBody.UploadCallback {
                        override fun onProgressUpdate(percentage: Int) {
                            runOnUiThread {
                                binding.progressBar.progress = percentage
                                binding.tvProgress.text = "Progress: ${percentage} %"
                                Log.d("TAG", "onProgressUpdate: ${percentage}")
                            }
                        }

                        override fun onError() {
                            runOnUiThread {

                            }
                        }

                        override fun onFinish() {
                            runOnUiThread {
                                binding.progressBar.visibility = View.GONE
                                binding.tvProgress.visibility = View.GONE

                                binding.etName.text?.clear()
                                binding.etTitle.text?.clear()
                                mProfileUri = null
                                binding.ivUser.setImageResource(R.drawable.ic_action_img)
                                
                            }
                        }
                    }
                )
            ))

        }.build()
















        lifecycleScope.launch {

            val resp: PhotoUploadResponse = RetrofitClient.retrofit.uploadedPhotoData(requestBody.parts)
            println("Response: " + resp)

            success(resp)

        }


    } // loadPhotoDataList




    private val startForProfileImageResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                val fileUri = data?.data!!

                binding.ivUser.setImageURI(fileUri)

                mProfileUri = getRealPathFromURI(fileUri)
//                imgProfile.setImageURI(fileUri)
            } else if (resultCode == ImagePicker.RESULT_ERROR) {
              //  Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            } else {
              //  Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }

    private fun getRealPathFromURI(contentUri: Uri): String? {

        val result: String?

        val cursor = contentResolver.query(contentUri, null, null,null, null)

        if (cursor == null) {
            result = contentUri.path
        } else {
            cursor.moveToFirst()
            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            result = cursor.getString(idx)
            cursor.close()
        }

        return result

    } // getRealPathFromURI

}