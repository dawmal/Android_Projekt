package com.example.projekt3

import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class StudentsViewModel : ViewModel() {
    private var _studentsList = MutableStateFlow<List<Student>>(emptyList())
    var studentsList = _studentsList.asStateFlow()

    init {
        getStudentsList()
    }

    fun getStudentsList() {
        var db = Firebase.firestore

        db.collection("students")
            .addSnapshotListener { value, error ->
                if(error != null){
                    return@addSnapshotListener
                }
                if(value != null) {
                    _studentsList.value = value.toObjects()
                }
            }

    }
}