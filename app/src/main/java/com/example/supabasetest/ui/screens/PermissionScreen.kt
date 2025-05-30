package com.example.supabasetest.ui.screens

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.supabasetest.utils.PermissionStatus
import com.example.supabasetest.viewmodel.PermissionViewModel

@Composable
fun PermissionScreen(navigateToList: () -> Unit) {
    val activity = LocalActivity.current
    val myViewModel = viewModel<PermissionViewModel>()
    val permissionStatus = myViewModel.permissionStatus.value
    var alreadyRequested by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        val result = when {
            granted -> PermissionStatus.Granted
            ActivityCompat.shouldShowRequestPermissionRationale(
                activity!!,
                Manifest.permission.CAMERA
            ) -> PermissionStatus.Denied

            else -> PermissionStatus.PermanentlyDenied
        }
        myViewModel.updatePermissionStatus(result)
    }

    LaunchedEffect(Unit) {
        if (!alreadyRequested) {
            alreadyRequested = true
            launcher.launch(Manifest.permission.CAMERA)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (permissionStatus) {
            null -> {
                CircularProgressIndicator()
                Text("Requesting permission...")
            }
            PermissionStatus.Granted -> navigateToList()
            PermissionStatus.Denied -> {
                Text("Permission denied")
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = {
                    launcher.launch(Manifest.permission.CAMERA)
                }) {
                    Text("Apply again")
                }
            }

            PermissionStatus.PermanentlyDenied -> {
                Text("Permission permanently denied")
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", activity!!.packageName, null)
                    }
                    activity!!.startActivity(intent)
                }) {
                    Text("Go to settings")
                }
            }
        }
    }
}