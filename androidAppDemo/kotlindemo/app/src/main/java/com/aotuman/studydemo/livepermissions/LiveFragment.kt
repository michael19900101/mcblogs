package com.aotuman.studydemo.livepermissions

import android.annotation.TargetApi
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData

internal class LiveFragment : Fragment() {

    lateinit var liveData :MutableLiveData<PermissionResult>

    private val PERMISSIONS_REQUEST_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissions(permissions: Array<out String>) {
        liveData = MutableLiveData()
        requestPermissions(permissions, PERMISSIONS_REQUEST_CODE)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            val denyPermission = ArrayList<String>()
            val rationalePermission = ArrayList<String>()
            for ((index, value) in grantResults.withIndex()) {
                if (value == PackageManager.PERMISSION_DENIED) {
                    if (shouldShowRequestPermissionRationale(permissions[index])) {
                        rationalePermission.add(permissions[index])
                    } else {
                        denyPermission.add(permissions[index])
                    }
                }
            }
            if (denyPermission.isEmpty() && rationalePermission.isEmpty()) {
                liveData.value = PermissionResult.Grant
            } else {
                if (rationalePermission.isNotEmpty()) {
                    liveData.value = PermissionResult.Rationale(rationalePermission.toTypedArray())
                } else if (denyPermission.isNotEmpty()) {
                    liveData.value = PermissionResult.Deny(denyPermission.toTypedArray())
                }
            }

        }
    }

}