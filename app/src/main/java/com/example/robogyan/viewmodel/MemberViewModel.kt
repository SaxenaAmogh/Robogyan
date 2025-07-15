package com.example.robogyan.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.robogyan.SupabaseClientProvider
import com.example.robogyan.model.Member
import com.example.robogyan.repository.MemberRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MemberViewModel : ViewModel() {

    val supabase = SupabaseClientProvider.client
    private val repository: MemberRepository = MemberRepository(supabase)
    private val _members = MutableStateFlow<List<Member>>(emptyList())
    val members: StateFlow<List<Member>> = _members

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        fetchMembers()
    }

    private fun fetchMembers() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _members.value = repository.getAllMembers()
                Log.e("MemberSuccess", "H: ${_members.value.size} members fetched successfully")
            } catch (e: Exception) {
                Log.e("MemberVM", "Error: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}
