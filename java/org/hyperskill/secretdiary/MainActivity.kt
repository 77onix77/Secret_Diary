package org.hyperskill.secretdiary

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.datetime.Clock
import java.text.SimpleDateFormat
import java.util.*

const val PREFERENCES_NAME = "PREF_DIARY"

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val editText = findViewById<EditText>(R.id.etNewWriting)
        val textView = findViewById<TextView>(R.id.tvDiary)
        textView.text = sharedPreferences.getString("KEY_DIARY_TEXT", "")

        findViewById<Button>(R.id.btnSave).setOnClickListener{
            if (editText.text.trim().isEmpty()) {
                Toast.makeText(applicationContext, "Empty or blank input cannot be saved" ,Toast.LENGTH_SHORT).show()
            } else {
                val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                val time = dateFormat.format(Clock.System.now().toEpochMilliseconds())
                val text = "$time\n${editText.text}\n\n" + textView.text
                textView.text = text.trim()
                editor.putString("KEY_DIARY_TEXT", text.trim()).apply()
                editText.text.clear()
            }
        }
        findViewById<Button>(R.id.btnUndo).setOnClickListener{
            AlertDialog.Builder(this)
                .setTitle("Remove last note")
                .setMessage("Do you really want to remove the last writing? This operation cannot be undone!")
                .setPositiveButton("Yes") {_, _ ->

                    val text = textView.text.toString()
                    if (!text.contains("\n\n")) textView.text = ""
                    else {
                        textView.text = text.substringAfter("\n\n").trim()
                        editor.putString("KEY_DIARY_TEXT", textView.text.toString()).apply()
                    }
                }
                .setNegativeButton("No", null)
                .show()
        }
    }
}