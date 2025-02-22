package com.example.robogyan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.robogyan.model.Member
import com.example.robogyan.repository.MemberRepository
import kotlinx.coroutines.launch

class MemberViewModel : ViewModel() {
    private val repository = MemberRepository()

    private val _members = MutableLiveData<List<Member>>()
    val members: LiveData<List<Member>> get() = _members

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    init {
        fetchMembers()
    }

    private fun fetchMembers() {
        viewModelScope.launch {
            try {
                val response = repository.getMembers()
                if (response.isSuccessful && response.body() != null) {
                    _members.postValue(response.body()!!.items)
                } else {
                    _error.postValue("Error: ${response.code()}")
                }
            } catch (e: Exception) {
                _error.postValue(e.message ?: "Unknown Error")
            }
        }
    }
}