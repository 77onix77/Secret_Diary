package org.hyperskill.secretdiary

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val editText = findViewById<EditText>(R.id.etNewWriting)
        val textView = findViewById<TextView>(R.id.tvDiary)
        findViewById<Button>(R.id.btnSave).setOnClickListener{
            if (editText.text.trim().isEmpty()) {
                Toast.makeText(applicationContext, "Empty or blank input cannot be saved" ,Toast.LENGTH_SHORT).show()
            } else {
                textView.text = editText.text
                editText.text.clear()
            }
        }
    }

}