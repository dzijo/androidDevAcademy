package com.gmail.matej.dzijan.cv.ui.main

import android.content.ClipData
import android.content.ClipboardManager
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.gmail.matej.dzijan.cv.R
import com.gmail.matej.dzijan.cv.common.aboutString
import com.gmail.matej.dzijan.cv.common.educationString
import com.gmail.matej.dzijan.cv.common.experienceString
import com.gmail.matej.dzijan.cv.common.languagesString
import com.gmail.matej.dzijan.cv.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private var myClipboard: ClipboardManager? = null
    private var myClip: ClipData? = null

    override fun getLayoutResourceId(): Int = R.layout.activity_main

    override fun setUpUi() {

        myClipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager?

        contactNumberText.setOnClickListener { copyContact(contactNumberText) }
        contactEmailText.setOnClickListener { copyContact(contactEmailText) }

        aboutText.text = aboutString
        educationText.text = educationString
        experienceText.text = experienceString
        languagesText.text = languagesString
    }

    private fun copyContact(view: TextView) {
        myClip = ClipData.newPlainText("contact", view.text)
        myClipboard?.primaryClip = myClip

        Toast.makeText(this, "Copied to clipboard", Toast.LENGTH_SHORT).show()
        Log.d("tag", "Pressed")
    }

}
