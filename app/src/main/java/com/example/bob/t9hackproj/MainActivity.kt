package com.example.bob.t9hackproj

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
// You won't be able to find an official documentation for the below package because
// it was developed by a third party library.
// https://github.com/firebase/FirebaseUI-Android
// The above link contains the doc for the below package.
import com.firebase.ui.auth.AuthUI
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var mFirebaseAnalytics: FirebaseAnalytics
    lateinit var providers: List<AuthUI.IdpConfig>
    lateinit var uID: String
    val memeForActivity = 1
    val LOG_TAG = MainActivity::class.java.getSimpleName()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        providers = Arrays.asList(
                AuthUI.IdpConfig.EmailBuilder().build()
        )
        initFirebase()
    }

    private fun initFirebase() {

        uID = FirebaseAuth.getInstance().uid!!
        Log.i(LOG_TAG, "uID: " + uID)

        if(uID == null) {
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .build(), memeForActivity
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == memeForActivity && resultCode == Activity.RESULT_OK){
            uID = FirebaseAuth.getInstance().uid!!
            Log.i(LOG_TAG, "New uID: " + uID);
        }
    }
}
