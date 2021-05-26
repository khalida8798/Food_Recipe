package com.example.food_recipe.MainDish

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.food_recipe.R
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_add_update_record.*


class AddUpdateRecordActivity : AppCompatActivity() {

    private val CAMERA_REQUEST_CODE = 100;
    private val STORAGE_REQUEST_CODE = 101;
    private val IMAGE_PICK_CAMERA_CODE = 102;
    private val IMAGE_PICK_GALLERY_CODE = 103;
    private lateinit var cameraPermission: Array<String>
    private lateinit var storagePermission: Array<String>
    private var imageUri: Uri? = null
    private var name: String? = ""
    private var id: String? = ""
    private var ingredients: String? = ""
    private var steps: String? = ""
    private var addedTime: String? =""
    private var updatedTime: String?= ""

    private var isEditMode = false


    lateinit var dbHelper: MyDbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_update_record)


        //actionBar!!.title = "Add Recipe"
       // actionBar!!.setDisplayHomeAsUpEnabled(true)
        //actionBar!!.setDisplayShowHomeEnabled(true)


        val intent = intent
        isEditMode = intent.getBooleanExtra("isEditMode",false)
        if (isEditMode){
        /*actionBar!!.title = "UpdateRecord"*/

            id = intent.getStringExtra("ID")
            name = intent.getStringExtra("NAME")
            ingredients = intent.getStringExtra("INGREDIENTS")
            steps = intent.getStringExtra("STEPS")
            imageUri = Uri.parse(intent.getStringExtra("IMAGE"))
            addedTime = intent.getStringExtra("TIMEADDED")
            updatedTime = intent.getStringExtra("UPDATEDTIME")

            if(imageUri.toString() == "null") {
               cimage.setImageResource(R.drawable.image2)
            }
            else{
              cimage.setImageURI(imageUri)
            }
            step.setText(steps)
            ingredient.setText(ingredients)
            recipename.setText(name)
        }
        else{
            /*actionBar!!.title = "Add Record"*/
        }

        dbHelper = MyDbHelper(this)

        cameraPermission = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        storagePermission = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)

        cimage.setOnClickListener {
            imagePickDialog()
        }

        done.setOnClickListener {
            inputData()
        }


    }

    private fun inputData() {
        name = "" + recipename.text.toString().trim()
        ingredients = "" + ingredient.text.toString().trim()
        steps = "" + step.text.toString().trim()

        if(isEditMode){
            val timeStamp = "${System.currentTimeMillis()}"
            dbHelper?. updateRecord(
                "$id",
                "$name",
                "$imageUri",
                "$ingredients",
                "$steps",
                "$addedTime",
                "$timeStamp"
            )
            Toast.makeText(this,"Updated", Toast.LENGTH_SHORT).show()
        }
        else{
            val timestamp = System.currentTimeMillis()
            val id = dbHelper.insertRecord(
                ""+name,
                ""+imageUri,
                ""+ingredients,
                ""+steps,
                "$timestamp",
                "$timestamp"
            )

            Toast.makeText(this,"Success", Toast.LENGTH_SHORT).show()

        }


}
    private fun imagePickDialog () {
        val options = arrayOf("Camera", "Gallery")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Pick Image From")
        builder.setItems(options){imagePickDialog, which ->
            if (which==0) {
                if (!checkCameraPermission()){
                    requestCameraPermission()
                }
                else{
                    pickFromCamera()
                }

            }
            else{
                if(!checkStoragepermission()){
                    requestStoragePermission()
                }
                else{
                    pickFromGallery()
                }

        }
    }
        builder.show()
    }


    private fun pickFromGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK)
        galleryIntent.type="image/*"
        startActivityForResult(
            galleryIntent,
            IMAGE_PICK_GALLERY_CODE
        )
    }

    private fun requestStoragePermission() {
        ActivityCompat.requestPermissions(this, storagePermission, STORAGE_REQUEST_CODE)
    }

    private fun checkStoragepermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) ==PackageManager.PERMISSION_GRANTED

    }

    private fun pickFromCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "Image Title")
        values.put(MediaStore.Images.Media.DESCRIPTION,"Image Description")
        imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values)
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(
            cameraIntent,
            IMAGE_PICK_CAMERA_CODE
        )
    }

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(this, cameraPermission, CAMERA_REQUEST_CODE)

    }

    private fun checkCameraPermission(): Boolean{
        val results = ContextCompat.checkSelfPermission(this,
        Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        val results1 = ContextCompat.checkSelfPermission(this,
        Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        return results && results1

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode){
            CAMERA_REQUEST_CODE->{
             if (grantResults.isNotEmpty()){
                 val cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
                 val storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED
                 if (cameraAccepted && storageAccepted) {
                     pickFromCamera()
                 }
                 else{
                     Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                 }

                 }
             }

            STORAGE_REQUEST_CODE -> {
                if (grantResults.isNotEmpty()){
                    val storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
                    if (storageAccepted){
                        pickFromGallery()
                    }
                    else{
                        Toast.makeText(this, "Successfully from gallery", Toast.LENGTH_SHORT).show()
                    }
                }


            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == IMAGE_PICK_GALLERY_CODE) {
            CropImage.activity(data!!.data)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1,1)
                .start (this)
            } else if (requestCode == IMAGE_PICK_CAMERA_CODE) {
            CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1,1)
                .start (this)
            } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
                val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK){
                val resultUri = result.uri
                imageUri = resultUri
                cimage.setImageURI(resultUri)
            }

            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                val error = result.error
                Toast.makeText(this, ""+error, Toast.LENGTH_SHORT).show()
        }

        }
    }

    }




