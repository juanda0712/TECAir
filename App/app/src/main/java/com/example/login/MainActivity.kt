package com.example.login

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.login.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //init UI
        initUI()
    }

    private fun initUI(){
        binding.fabBack.setOnClickListener{

        }
        binding.btnSignin.setOnClickListener{

        }

        binding.tvForgotpass.setOnClickListener{

        }

        binding.tvSignup.setOnClickListener{

        }


}
}
