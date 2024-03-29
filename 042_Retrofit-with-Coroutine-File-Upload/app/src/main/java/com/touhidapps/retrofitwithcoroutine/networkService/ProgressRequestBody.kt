package com.touhidapps.retrofitwithcoroutine.networkService

import android.os.Handler
import android.os.Looper
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okio.BufferedSink
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.util.concurrent.TimeUnit


class ProgressRequestBody(val mFile: File, val mListener: UploadCallback) : RequestBody() {

    companion object {
        private const val DEFAULT_BUFFER_SIZE = 2024
    }

    public interface UploadCallback {

        fun onProgressUpdate(percentage: Int)
        fun onError()
        fun onFinish()

    }

    override fun contentType(): MediaType? {
//        return MediaType.parse("multipart/mp4")
//        return MediaType.parse("multipart/form-data")
        return "multipart/form-data".toMediaTypeOrNull()
    }

    @Throws(IOException::class)
    override fun contentLength(): Long {
        return mFile.length()
    }

    @Throws(IOException::class)
    override fun writeTo(sink: BufferedSink) {
        val fileLength = mFile.length()
        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
        val input = FileInputStream(mFile)
        var uploaded: Long = 0

        try {

            var read: Int
            val handler = Handler(Looper.getMainLooper())

            while (true) {
                read = input.read(buffer)
                if (read == -1) break

                uploaded += read.toLong()
                sink.write(buffer, 0, read)
                handler.post(ProgressUpdater(uploaded, fileLength))

//                sink.timeout().timeout(3, TimeUnit.MINUTES)

            }


        } catch (e: Exception) {
            mListener.onError()
        } finally {
            input.close()
            mListener.onFinish()
        }


    } // writeTo

    private inner class ProgressUpdater(private val mUploaded: Long, private val mTotal: Long): Runnable {
        override fun run() {
            mListener.onProgressUpdate((100 * mUploaded / mTotal).toInt())
        }
    }

}