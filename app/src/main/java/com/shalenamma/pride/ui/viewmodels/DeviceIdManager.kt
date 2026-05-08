package com.shalenamma.pride.ui.viewmodels

import android.content.Context
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeviceIdManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val sharedPreferences = context.getSharedPreferences("device_prefs", Context.MODE_PRIVATE)
    private val DEVICE_ID_KEY = "device_id"

    fun getDeviceId(): String {
        var deviceId = sharedPreferences.getString(DEVICE_ID_KEY, null)

        if (deviceId == null) {
            deviceId = UUID.randomUUID().toString()
            sharedPreferences.edit().putString(DEVICE_ID_KEY, deviceId).apply()
            Log.d("DeviceIdManager", "Generated new device ID: $deviceId")
        } else {
            Log.d("DeviceIdManager", "Using existing device ID: $deviceId")
        }

        return deviceId
    }
}
