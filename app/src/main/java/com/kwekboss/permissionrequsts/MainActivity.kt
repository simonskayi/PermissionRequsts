package com.kwekboss.permissionrequsts

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    val permissionRequestCode = 99
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

      if(hasCameraPermission()){
       showText()
   }
        else{
      showPermissionRational()
        }

    }
    //Step 4 Activity checks if permission has been granted(will run code immediately after granted)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            permissionRequestCode -> if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
               showText()
            } else{
                showPermissionRational()
            }
        }
    }
/// This is a test function
    fun showText(){
        Toast.makeText(this, "Permission is Working", Toast.LENGTH_LONG).show()
    }

    // Step 1. Check If The Permission Is Already Granted
    fun hasCameraPermission():Boolean{
        return ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)==PackageManager.PERMISSION_GRANTED
    }

    //Step.2 Create the Permission Request
    fun requestPermission(){
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),permissionRequestCode)
    }

    // Step 3A. If the User Denies the Permission (Clarify the Permission Again)
    fun showPermissionRational() = if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CAMERA)){
        permissionRational { requestPermission() }
    }
    else{
        requestPermission()
    }

     // Step 3B. Create a Dialoag to Explain the Need
    fun permissionRational(positiveAction:()-> Unit){
        AlertDialog.Builder(this)
            .setTitle("Camera Permission")
            .setMessage("This App Requires Camera To Work")
            .setPositiveButton("Ok"){
                _,_-> positiveAction()
            }
            .create()
            .show()
    }


}

