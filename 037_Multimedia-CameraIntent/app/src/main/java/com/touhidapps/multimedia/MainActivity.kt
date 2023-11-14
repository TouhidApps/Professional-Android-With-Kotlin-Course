package com.touhidapps.multimedia


import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.touhidapps.multimedia.databinding.ActivityMainBinding
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var startForResult: ActivityResultLauncher<Intent>
    private var imageUri: Uri? = null
    private lateinit var imgFile: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        makeImageLocation()
        myPhotoResult()

        binding.btnTakePhoto.setOnClickListener {

            takePhoto()

        }


    } // onCreate

    private fun makeImageLocation() {

        imgFile = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "")

        if (!imgFile.exists()) {
            imgFile.mkdirs()
        }

    } // makeImageLocation

    private fun myPhotoResult() {

        startForResult = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {

                try {

                    val mBitmap: Bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)

                    val rotationMatrix = Matrix()
                    if (mBitmap.width >= mBitmap.height) {
                        rotationMatrix.setRotate(90F)
                    } else {
                        rotationMatrix.setRotate(0F)
                    }
                    val rotateBitmap: Bitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.width,
                        mBitmap.height, rotationMatrix, false)

                    binding.ivMain.setImageBitmap(rotateBitmap)

                    // Save to storage
                    val mTime = System.currentTimeMillis()
                    val outputStream = FileOutputStream("$imgFile/my_image_${mTime}.jpg")
                    rotateBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                    outputStream.flush()
                    outputStream.close()

                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }

    } // myPhotoResult

    private fun takePhoto() {

        val values = ContentValues().apply {
            put(MediaStore.Images.Media.TITLE, "My Profile Photo")
            put(MediaStore.Images.Media.DESCRIPTION, "My Profile Photo")
        }
        imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        val mIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        }
        startForResult.launch(mIntent)

    } // takePhoto




}