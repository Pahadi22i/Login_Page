package com.example.mvvmarchitechture

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.example.mvvmarchitechture.databinding.ActivityMainBinding
import com.example.mvvmarchitechture.databinding.ActivitySingingBinding
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_singing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class Singing : AppCompatActivity() {
    private lateinit var mAuth:FirebaseAuth
    private lateinit var binding: ActivitySingingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySingingBinding.inflate(layoutInflater)
        setContentView(binding.root)
         mAuth= FirebaseAuth.getInstance()
        binding.singup.setOnClickListener(){
            val email=email.text.toString()
            val password=password.text.toString()
            lifecycleScope.launch(Dispatchers.IO){
                singupWithEmailAndPassword(mAuth,email,password)
            }
        }
        binding.singing.setOnClickListener(){
            val email=email.text.toString()
            val password=password.text.toString()
            lifecycleScope.launch(Dispatchers.IO){
                singInWithEmailAndPassword (mAuth,email,password)
            }
        }

    }


     suspend fun singupWithEmailAndPassword(firebaseAuth:FirebaseAuth,email:String,password:String):AuthResult?{

        return try {
             val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()

            updateUI(result.user)
             result
         } catch (e:java.lang.Exception){
            withContext(Dispatchers.Main){
                Toast.makeText(this@Singing,"${e.message}", Toast.LENGTH_SHORT).show()
                Log.d("AuthResult","${e.message}")
            }

             null
         }


    }
    suspend fun singInWithEmailAndPassword(firebaseAuth:FirebaseAuth,email:String,password:String):AuthResult?{

        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()

            updateUI(result.user)
            result
        } catch (e:java.lang.Exception){
            withContext(Dispatchers.Main){
                Toast.makeText(this@Singing,"${e.message}", Toast.LENGTH_SHORT).show()
                Log.d("AuthResult","${e.message}")
            }

            null
        }


    }
  suspend private fun updateUI(firebaseUser:FirebaseUser?) {

        Log.d("AuthResult","${firebaseUser?.email}")
        withContext(Dispatchers.Main){
            Toast.makeText( this@Singing,"Success", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@Singing,MainActivity::class.java))
            startActivity(Intent())
        }

    }

    override fun onStart() {
        super.onStart()
        val currentUser=mAuth.currentUser
        if(currentUser!=null){
            startActivity(Intent(this@Singing,MainActivity::class.java))
        }
    }

}