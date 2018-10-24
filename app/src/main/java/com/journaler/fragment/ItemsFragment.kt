package com.journaler.fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.journaler.R
import com.journaler.activity.NoteActivity
import com.journaler.activity.TodoActivity
import com.journaler.model.MODE
import java.text.SimpleDateFormat
import java.util.*

class ItemsFragment:BaseFragment(){

    override val logTag = "Items Fragment"

    val NOTE_REQUEST =0
    val TODO_REQUEST =1
    override fun getLayout(): Int {
        return R.layout.fragment_items
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater?.inflate(getLayout(),container,false)
        val btn = view?.findViewById<FloatingActionButton>(R.id.new_item)
        btn?.setOnClickListener {
            val items = arrayOf(getString(R.string.todos), getString(R.string.notes))
            var builder =
                AlertDialog.Builder(this@ItemsFragment.context)
                    .setTitle(R.string.choose_a_type)
                    .setItems(
                        items,
                        { _, which ->
                            when (which) {
                                0 -> {
                                    openCreateTodo()
                                }
                                1 -> {
                                    openCreateNote()
                                }
                                else -> Log.e(logTag, "Unknown option selected [$which]")
                            }
                        }
                    )
            builder.show()

        }
        return view

    }

    private fun openCreateNote(){
        val intent = Intent(context,NoteActivity::class.java)
        val data = Bundle()
        data.putInt(MODE.EXTRAS_KEY,MODE.CREATE.mode)
        intent.putExtras(data)
        startActivityForResult(intent,NOTE_REQUEST)
    }

    private fun openCreateTodo(){
        val date = Date(System.currentTimeMillis())
        val dateFormat = SimpleDateFormat("MMM dd YYYY", Locale.ENGLISH)
        val timeFormat = SimpleDateFormat("MM:HH", Locale.ENGLISH)
        val intent = Intent(context,TodoActivity::class.java)
        val data = Bundle()
        data.putInt(MODE.EXTRAS_KEY,MODE.CREATE.mode)
        data.putString(TodoActivity.EXTRA_DATE,dateFormat.format(date))
        data.putString(TodoActivity.EXTRA_TIME,timeFormat.format(date))
        intent.putExtras(data)
        startActivityForResult(intent,TODO_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode){
            TODO_REQUEST -> {
                if(resultCode==Activity.RESULT_OK){
                    Log.i(logTag,"TODO created")
                } else {
                    Log.w(logTag,"TODO not created")
                }
            }
            NOTE_REQUEST -> {
                if(resultCode==Activity.RESULT_OK){
                    Log.i(logTag,"Note created")
                } else {
                    Log.w(logTag,"Note not created")
                }
            }
        }
    }


}