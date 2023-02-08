package com.example.mvvmarchitechture

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mvvmarchitechture.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.zip.Inflater

class MainActivity : AppCompatActivity() {
    private lateinit var mAuth:FirebaseAuth
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
       mAuth= FirebaseAuth.getInstance()
          binding.logout.setOnClickListener(){
                   mAuth.signOut()
              Toast.makeText(this, "Log out success full", Toast.LENGTH_SHORT).show()
              finish()
          }
    }

}