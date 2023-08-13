package com.example.calculator30

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_new_note.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream
import java.util.*


private const val CAMERA_REQUEST_CODE = 1
class NewNote : AppCompatActivity(){
    val fs = FirebaseFirestore.getInstance().collection("notes")
    val storage = FirebaseStorage.getInstance().reference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_note)

        //popup window
        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
        val width = dm.widthPixels
        val height = dm.heightPixels
        window.setLayout((width * 0.9).toInt(),(height * 0.9).toInt())

        //hide action bar
        supportActionBar?.hide()

        takeNewIMG.setOnClickListener{
            //open  camera
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (intent.resolveActivity(packageManager) != null) {
                startActivityForResult(intent, CAMERA_REQUEST_CODE)
            }
        }
        btnSave.setOnClickListener{
            if(canSave()){
                //upload img
                uploadImg()
            }
        }

    }
    private fun canSave(): Boolean
        = !etNewNoteSubject.text.isNullOrBlank() && !etNewNoteTitle.text.isNullOrBlank() && !etNewNoteTopic.text.isNullOrBlank()
    fun uploadImg(){
        //upload img
        val randomUUID = UUID.randomUUID()
        val bitmap = (imgNewNote.drawable as BitmapDrawable).bitmap
        val boas = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,boas)
        val data = boas.toByteArray()
        storage.child("$randomUUID").putBytes(data).addOnSuccessListener {
            Toast.makeText(this,"DONE",Toast.LENGTH_LONG).show()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            //set taken photo
            val imgBitmap = data?.extras?.get("data") as Bitmap
            imgNewNote.setImageBitmap(imgBitmap)
        }
    }
}