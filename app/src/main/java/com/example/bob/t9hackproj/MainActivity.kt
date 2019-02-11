package com.example.bob.t9hackproj

import android.content.Intent
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import java.io.IOException
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    // I've pretty much copy pasted what's below.
    // https://code.tutsplus.com/tutorials/firebase-for-android-file-storage--cms-27376
    // The only thing that's not in the above tutorial is putting
    // google-service.json being in the project directory.

    private lateinit var fbAnalytics: FirebaseAnalytics
    private lateinit var fbAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fbAuth = FirebaseAuth.getInstance()

        //Referred: https://firebase.google.com/docs/analytics/android/events
        //Apparently, Analytics won't show up unless some time has passed:
        //https://firebase.googleblog.com/2016/11/how-long-does-it-take-for-my-firebase-analytics-data-to-show-up.html
        fbAnalytics = FirebaseAnalytics.getInstance(this)
        val newBundle = Bundle()
        newBundle.putString(FirebaseAnalytics.Param.VALUE, "some app")
        // The below is a way to create a custom event.
        fbAnalytics.logEvent("STARTING", newBundle)

        var storage = FirebaseStorage.getInstance()
        // The below two lines are pretty self documenting...
        var storageRef = storage.getReferenceFromUrl("gs://t9hackproj.appspot.com/")
                .child("GoroAndAoba.jpg")
        var animeStuff = findViewById<ImageView>(R.id.aoba_and_goro)
        // Download the image from Firebase and display it on the device.
        // Also, the image never shows up immediately
        // (if this were a interview question, using a Service API wouldn't
        // such bad answer).
        try{
            var localFile = File.createTempFile("images", "jpg ");
            storageRef.getFile(localFile).addOnSuccessListener {
                Log.i("dhl", "In the block of OnSuccessListener.")
                var bitMap = BitmapFactory.decodeFile(localFile.absolutePath)
                animeStuff.setImageBitmap(bitMap)
            }
        }catch(e: IOException){
            Log.i("dhl", "Everything breaks!");
        }

        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "Aoba_and_Goro")
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Aoba_and_Goro")
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image")
        fbAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM, bundle)

        //The below three lines uploads a text file to the Firebase storage.
        //Unfortunately, if those three lines are uncommented, the image
        //does not show.
        //var uploadReference = storage.getReferenceFromUrl("gs://t9hackproj.appspot.com/")
        //        .child("test.txt")
        //var stream = resources.openRawResource(R.raw.test)
        //var uploadTask = uploadReference.putStream(stream)

    }

    fun startLogin(view: View){
        startActivity(Intent(getApplicationContext(), RegisterActivity::class.java))
    }
}
