package com.journaler.activity

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import kotlinx.android.synthetic.main.activity_header.*
import kotlinx.android.synthetic.main.activity_item.*
import com.journaler.R

abstract class BaseActivity : AppCompatActivity(){

    protected abstract val tag : String
    protected abstract fun getLayout():Int
    protected abstract fun getActivityTitle():Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
        setSupportActionBar(toolbar)
        Log.v(tag,"[ ON CREATE ]")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main,menu)
        return true

    }
}