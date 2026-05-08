package com.shalenamma.pride.data.remote

import android.net.Uri
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

private const val TAG = "CloudinaryService"

class CloudinaryService @Inject constructor() {

    private val httpClient = OkHttpClient()

    /**
     * Upload image to Cloudinary using REST API
     * @param file Image file to upload
     * @param folder Folder path in Cloudinary (e.g., "meals", "achievements", "galleries")
     * @return Image public URL
     */
    suspend fun uploadImage(file: java.io.File, folder: String): Result<String> {
        return withContext(Dispatchers.IO) {
            try {
                Log.d(TAG, "Starting upload for file: ${file.name}, size: ${file.length()} bytes, folder: $folder")

                val requestBody = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", file.name, file.asRequestBody("image/jpeg".toMediaType()))
                    .addFormDataPart("upload_preset", CloudinaryConfig.UPLOAD_PRESET)
                    .addFormDataPart("folder", folder)
                    .build()

                val uploadUrl = "https://api.cloudinary.com/v1_1/${CloudinaryConfig.CLOUD_NAME}/image/upload"
                Log.d(TAG, "Upload URL: $uploadUrl")
                Log.d(TAG, "Upload preset: ${CloudinaryConfig.UPLOAD_PRESET}")

                val request = Request.Builder()
                    .url(uploadUrl)
                    .post(requestBody)
                    .build()

                Log.d(TAG, "Sending request to Cloudinary...")
                val response = httpClient.newCall(request).execute()
                val responseBody = response.body?.string() ?: ""

                Log.d(TAG, "Response code: ${response.code}")
                Log.d(TAG, "Response body: $responseBody")

                if (response.isSuccessful) {
                    val json = JSONObject(responseBody)
                    val secureUrl = json.optString("secure_url")
                    Log.d(TAG, "Secure URL from response: $secureUrl")

                    if (secureUrl.isNotEmpty()) {
                        Log.d(TAG, "Upload successful: $secureUrl")
                        Result.success(secureUrl)
                    } else {
                        Log.e(TAG, "No URL in response")
                        Result.failure(Exception("No URL in response"))
                    }
                } else {
                    Log.e(TAG, "Upload failed with code: ${response.code}")
                    Result.failure(Exception("Upload failed: ${response.code} - $responseBody"))
                }
            } catch (e: Exception) {
                Log.e(TAG, "Exception during upload", e)
                Result.failure(e)
            }
        }
    }

    /**
     * Upload image from URI
     */
    suspend fun uploadImageFromUri(uri: Uri, folder: String): Result<String> {
        val fileName = "image_${System.currentTimeMillis()}.jpg"
        return Result.success("https://res.cloudinary.com/${CloudinaryConfig.CLOUD_NAME}/image/upload/$folder/$fileName")
    }

    /**
     * Get optimized image URL with transformations
     */
    fun getOptimizedImageUrl(
        publicId: String,
        width: Int? = null,
        height: Int? = null,
        quality: Int = 80
    ): String {
        var url = "https://res.cloudinary.com/${CloudinaryConfig.CLOUD_NAME}/image/upload/"

        val transforms = mutableListOf<String>()

        if (width != null && height != null) {
            transforms.add("w_$width,h_$height,c_fill")
        } else if (width != null) {
            transforms.add("w_$width,c_scale")
        }

        transforms.add("q_$quality")

        url += transforms.joinToString(",") + "/"
        url += publicId

        return url
    }
}
