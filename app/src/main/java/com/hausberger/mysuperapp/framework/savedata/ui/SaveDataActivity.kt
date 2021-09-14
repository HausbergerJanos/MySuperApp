package com.hausberger.mysuperapp.framework.savedata.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hausberger.mysuperapp.databinding.ActivitySaveDataBinding
import com.hausberger.mysuperapp.framework.savedata.model.ExternalFileRepository
import com.hausberger.mysuperapp.framework.savedata.model.InternalFileRepository
import com.hausberger.mysuperapp.framework.savedata.model.Note
import com.hausberger.mysuperapp.framework.savedata.model.NoteRepository
import com.hausberger.mysuperapp.framework.util.showToast

class SaveDataActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySaveDataBinding

    private val repo: NoteRepository by lazy { ExternalFileRepository(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySaveDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnWrite.setOnClickListener {
                if (edtFileName.text.isNotEmpty()) {
                    try {
                        repo.addNote(Note(
                            fileName = edtFileName.text.toString(),
                            noteText = edtNoteText.text.toString()
                        ))
                    } catch (e: Exception) {
                        showToast("File Write Failed")
                    }
                    edtFileName.text.clear()
                    edtNoteText.text.clear()
                } else {
                    showToast("Please provide a Filename")
                }
            }

            btnRead.setOnClickListener {
                if (edtFileName.text.isNotEmpty()) {
                    try {
                        val note = repo.getNote(edtFileName.text.toString())
                        edtNoteText.setText(note.noteText)
                    } catch (e: Exception) {
                        showToast("File Read Failed")
                    }
                } else {
                    showToast("Please provide a Filename")
                }
            }

            btnDelete.setOnClickListener {
                if (edtFileName.text.isNotEmpty()) {
                    try {
                        if (repo.deleteNote(edtFileName.text.toString())) {
                            showToast("File Deleted")
                        } else {
                            showToast("File Could Not Be Deleted")
                        }
                    } catch (e: Exception) {
                        showToast("File Delete Failed")
                    }
                    edtFileName.text.clear()
                    edtNoteText.text.clear()
                } else {
                    showToast("Please provide a Filename")
                }
            }
        }
    }
}