package com.vertex5.jpmorganalbums.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.vertex5.jpmorganalbums.R
import com.vertex5.jpmorganalbums.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), FragmentListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun setupActionBar(){
        with(binding){
            toolbar = appbarHome.appToolbar
            toolbar.visibility = View.VISIBLE
            setSupportActionBar(toolbar)
        }

        val ab = supportActionBar

        ab!!.setDisplayShowCustomEnabled(true) // enable overriding the default toolbar layout
        ab.setDisplayHomeAsUpEnabled(false)
        ab.setDisplayShowCustomEnabled(false) // enable overriding the default toolbar layout
        ab.setDisplayShowTitleEnabled(false)
    }
}