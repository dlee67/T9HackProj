package com.example.bob.t9hackproj

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;

class RegisterActivity : AppCompatActivity() {

    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var registerButton: Button
    lateinit var loginButton: Button
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.log_in_view)

        email = findViewById(R.id.uyeEmail);
        password = findViewById(R.id.uyeParola);
        registerButton = findViewById(R.id.yeniUyeButton);
        loginButton = findViewById(R.id.uyeGirisButton);

        firebaseAuth = FirebaseAuth.getInstance()

        registerButton.setOnClickListener{
            var emailString = email.getText().toString()
            var passwordString = password.getText().toString()

            if(TextUtils.isEmpty(emailString)){
                Toast.makeText(getApplicationContext(),"Please fill in the required fields",Toast.LENGTH_SHORT).show();
            }

            if(TextUtils.isEmpty(passwordString)){
                Toast.makeText(getApplicationContext(),"Please fill in the required fields",Toast.LENGTH_SHORT).show();
            }

            if(passwordString.length < 6){
                Toast.makeText(getApplicationContext(),"Password must be at least 6 characters",Toast.LENGTH_SHORT).show();
            }

            firebaseAuth.createUserWithEmailAndPassword(emailString, passwordString)
                    .addOnCompleteListener{
                        if(it.isSuccessful){
                            startActivity(Intent(getApplicationContext(),MainActivity::class.java))
                            finish()
                        }else{
                            Toast.makeText(getApplicationContext(),"E-mail or password is wrong",Toast.LENGTH_SHORT).show()
                        }
                    }

            if(firebaseAuth.currentUser != null){
                startActivity(Intent(getApplicationContext(), MainActivity::class.java))
            }
        }
    }
}