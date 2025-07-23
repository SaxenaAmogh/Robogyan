package com.example.robogyan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
class UpdateViewModel : ViewModel() {

    private val databaseRef = FirebaseDatabase.getInstance().getReference("Updates")

    private val _labGate = MutableStateFlow<Boolean?>(null)
    val labGate: StateFlow<Boolean?> = _labGate

    private val _updateA = MutableStateFlow<String?>(null)
    val updateA: StateFlow<String?> = _updateA

    private val _updateB = MutableStateFlow<String?>(null)
    val updateB: StateFlow<String?> = _updateB

    private val listener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            viewModelScope.launch {
                _labGate.value = snapshot.child("LabGate").getValue(Boolean::class.java)
                _updateA.value = snapshot.child("UpdateA").getValue(String::class.java)
                _updateB.value = snapshot.child("UpdateB").getValue(String::class.java)
            }
        }

        override fun onCancelled(error: DatabaseError) {
            // Handle error
        }
    }

    init {
        databaseRef.addValueEventListener(listener)
    }

    override fun onCleared() {
        super.onCleared()
        databaseRef.removeEventListener(listener)
    }

    // ✅ Function to update LabGate (true or false)
    fun updateLabGate(state: Boolean) {
        databaseRef.child("LabGate").setValue(state)
    }

    // ✅ Function to update either UpdateA or UpdateB
    fun updateMessage(updateKey: String, message: String) {
        if (updateKey == "UpdateA" || updateKey == "UpdateB") {
            databaseRef.child(updateKey).setValue(message)
        } else {
            // Optional: log or throw error for unsupported keys
        }
    }
}
