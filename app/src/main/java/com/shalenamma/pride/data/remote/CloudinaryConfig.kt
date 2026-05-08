package com.shalenamma.pride.data.remote

import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CloudinaryConfig @Inject constructor(@ApplicationContext context: android.content.Context) {
    // Cloudinary configuration
    companion object {
        const val CLOUD_NAME = "dbdocmhbx"
        const val API_KEY = "1oahYvCasrOE1DxtjSIr9QE1MPk"
        const val UPLOAD_PRESET = "shale_namma"  // Create this in Cloudinary for unsigned uploads
    }
}
