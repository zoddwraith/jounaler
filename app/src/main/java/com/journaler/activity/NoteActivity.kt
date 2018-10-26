package com.journaler.activity

import android.location.Location
import android.location.LocationListener
import android.os.AsyncTask
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import com.journaler.R
import com.journaler.R.id.note_title
import com.journaler.database.Db
import com.journaler.location.LocationProvider
import com.journaler.model.Note
import kotlinx.android.synthetic.main.activity_note.*
import kotlinx.android.synthetic.main.activity_todo.*


class NoteActivity : ItemActivity() {

    private var note: Note? = null
    private var location: Location? = null
    override val tag = "Note Activity"
    override fun getLayout() = R.layout.activity_note

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
            updateNote()
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }
    }

    private fun getNoteContent(): String {
        return note_content.text.toString()
    }

    private fun getNoteTitle(): String {
        return note_title.text.toString()
    }

    private val locationListener = object : LocationListener {
        override fun onLocationChanged(p0: Location?) {
            p0?.let {
                LocationProvider.unsubscribe(this)
                location = p0
                val title = getNoteTitle()
                val content = getNoteContent()
                note = Note(title, content, p0)
                val task = object : AsyncTask<Note, Void, Boolean>() {
                    override fun doInBackground(vararg params: Note?): Boolean {
                        if (!params.isEmpty()) {
                            val param = params[0]
                            param?.let {
                                return Db.NOTE.insert(param) > 0
                            }
                        }
                        return false
                    }

                    override fun onPostExecute(result: Boolean?) {
                        result?.let {
                            if (result) {
                                Log.i(tag, "Note inserted")
                            } else {
                                Log.i(tag, "Note not inserted")
                            }
                        }
                    }
                }
                task.execute(note)
            }
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onProviderEnabled(provider: String?) {


        }

        override fun onProviderDisabled(provider: String?) {

        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        note_title.addTextChangedListener(textWatcher)
        note_content.addTextChangedListener(textWatcher)
        val data = intent.extras
    }


    private fun updateNote() {
        if (note == null) {
            if (!TextUtils.isEmpty(getNoteTitle()) && !TextUtils.isEmpty(getNoteContent())) {
                LocationProvider.subscribe(locationListener)
            } else {
                note?.title = getNoteTitle()
                note?.message = getNoteContent()
                val task = object : AsyncTask<Note, Void, Boolean>() {
                    override fun doInBackground(vararg params: Note?): Boolean {
                        if (!params.isEmpty()) {
                            val param = params[0]
                            param?.let {
                                return Db.NOTE.update(param) > 0
                            }
                        }
                        return false
                    }

                    override fun onPostExecute(result: Boolean) {
                        result?.let {
                            if (result) {
                                Log.i(tag, "Note updated")
                            } else {
                                Log.e(tag, "Note nor updated")
                            }
                        }
                    }
                }
                task.execute(note)
            }
        }




    }
}