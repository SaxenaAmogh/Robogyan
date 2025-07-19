package com.example.robogyan.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.robogyan.data.local.AppDatabase
import com.example.robogyan.data.local.dao.MemberDao
import com.example.robogyan.data.local.entities.MemberData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class UserViewModel(context: Context) : ViewModel() {
    private val dao = AppDatabase.getDatabase(context).memberDao()

    fun insert(member: MemberData) = viewModelScope.launch {
        dao.insertMember(member)
    }

    fun deleteById(id: String) = viewModelScope.launch {
        dao.deleteMemberById(id)
    }

    val memberFlow: (String) -> Flow<MemberData?> = { id ->
        dao.getMemberById(id)
    }
}