package com.example.bob.t9hackproj

import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import java.io.IOException
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var storage = FirebaseStorage.getInstance()
        var storageRef = storage.getReferenceFromUrl("gs://t9hackproj.appspot.com/")
                .child("GoroAndAoba.jpg")
        var animeStuff = findViewById<ImageView>(R.id.aoba_and_goro)
        try{
            var localFile = File.createTempFile("images", "jpg ");
            storageRef.getFile(localFile).addOnSuccessListener (
                OnSuccessListener<FileDownloadTask.TaskSnapshot>(){
                    Log.i("dhl", "In the block of OnSuccessListener.")
                    var bitMap = BitmapFactory.decodeFile(localFile.absolutePath)
                    animeStuff.setImageBitmap(bitMap)
                })
        }catch(e: IOException){
            Log.i("dhl", "Everything breaks!");
        }

    }
}
