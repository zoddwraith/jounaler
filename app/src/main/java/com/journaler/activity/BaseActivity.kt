package com.journaler.activity

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_header.*
import kotlinx.android.synthetic.main.activity_item.*
import com.journaler.R
import com.journaler.permission.PermissionCompatActivity
import java.util.*

abstract class BaseActivity : PermissionCompatActivity(){

    companion object {
        val REQUEST_GPS = 0
        private var fontExoBold: Typeface? = null
        private var fontExoRegular: Typeface? = null

        fun applyFonts(view:View, ctx:Context){
            var vTag=""
            if(view.tag is String){
                vTag=view.tag as String
            }
            when(view){
                is ViewGroup ->{
                    for(x in 0..view.childCount - 1){
                        applyFonts(view.getChildAt(x),ctx)
                    }
                }
                is Button -> {
                    when (vTag){
                        ctx.getString(R.string.tag_font_bold) ->{
                            view.typeface= fontExoBold
                        }
                        else -> {
                            view.typeface= fontExoRegular
                        }
                    }
                }
                is TextView -> {
                    when (vTag){
                        ctx.getString(R.string.tag_font_bold) ->{
                            view.typeface= fontExoBold
                        }
                        else -> {
                            view.typeface= fontExoRegular
                        }
                    }
                }
                is EditText -> {
                    when (vTag){
                        ctx.getString(R.string.tag_font_bold) ->{
                            view.typeface= fontExoBold
                        }
                        else -> {
                            view.typeface= fontExoRegular
                        }
                    }
                }
            }
        }

    }

    protected abstract val tag : String
    protected abstract fun getLayout():Int
    protected abstract fun getActivityTitle():Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out)
        setContentView(getLayout())
        setSupportActionBar(toolbar)
        requestPermissions(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET)

        Log.v(tag,"[ ON CREATE ]")
    }

    override fun onResume() {
        super.onResume()
        val animation = getAnimation(R.anim.top_to_bottom)
        findViewById<View>(R.id.toolbar).startAnimation(animation)
    }

    override fun onPause() {
        super.onPause()
        val animation = getAnimation(R.anim.hide_to_top)
        findViewById<View>(R.id.toolbar).startAnimation(animation)
    }



    fun Activity.getAnimation(animation:Int): Animation = AnimationUtils.loadAnimation(this, animation)

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main,menu)
        return true

    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        applyFonts()
    }

    protected fun applyFonts(){
        initFonts()
        val rootView =findViewById<View>(android.R.id.content)
        applyFonts(rootView,this)

    }

    private fun initFonts(){
        if(fontExoBold==null){
            fontExoBold=Typeface.createFromAsset(assets,"fonts/Exo2-Bold.otf")
        }
        if(fontExoRegular==null){
            fontExoRegular=Typeface.createFromAsset(assets,"fonts/Exo2-Regular.otf")
        }
    }

}