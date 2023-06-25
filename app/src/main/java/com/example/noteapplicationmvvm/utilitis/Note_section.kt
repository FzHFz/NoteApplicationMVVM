package com.example.noteapplicationmvvm.utilitis

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.noteapplicationmvvm.Models.Note
import com.example.noteapplicationmvvm.R
import com.example.noteapplicationmvvm.databinding.ActivityNoteSectionBinding
import java.lang.Exception

class note_section : AppCompatActivity() {
    private lateinit var binding: ActivityNoteSectionBinding
    private lateinit var note: Note
    private lateinit var old_note: Note
    var isUpdate = false
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityNoteSectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        try{
            old_note =intent.getSerializableExtra("current_note")as Note
            binding.title.setText(old_note.title)
            binding.noteContent.setText(old_note.note)
            isUpdate = true
        }catch (e: Exception){
            e.printStackTrace()
        }

        binding.save.setOnClickListener{
            val title_test:String = binding.title.text.toString()
            val content_test:String = binding.noteContent.text.toString()
            if(title_test.isEmpty() || content_test.isEmpty()){
                Toast.makeText(this,"Please fill all boxes",Toast.LENGTH_LONG).show()
            }else {
                if(isUpdate){
                    note = Note(old_note.id,title_test,content_test)

                }else{
                    note = Note(null,title_test,content_test)
                }
                val intent = Intent()
                intent.putExtra("note",note)
                Toast.makeText(this,"Note Successfully Saved",Toast.LENGTH_LONG).show()
                setResult(Activity.RESULT_OK,intent)
                finish()
            }
        }

    }
}
//      Toast.makeText(this,"Note Successfully Saved",Toast.LENGTH_LONG).show()
//                val intent = Intent(this, MainActivity::class.java)
//                startActivity(intent)