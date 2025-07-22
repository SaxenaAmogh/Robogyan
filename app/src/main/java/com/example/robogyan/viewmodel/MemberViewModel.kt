package com.example.robogyan.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.robogyan.SupabaseClientProvider
import com.example.robogyan.data.local.AppDatabase
import com.example.robogyan.data.local.entities.AllMembers
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MemberViewModel(application: Application) : AndroidViewModel(application) {

    private val supabase = SupabaseClientProvider.client
    private val _members = MutableStateFlow<List<AllMembers>>(emptyList())
    private val _isLoading = MutableStateFlow(false)

    fun fetchMembers() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val isLoggedIn = supabase.auth.currentSessionOrNull() != null
                val tableName = if (isLoggedIn) "members" else "guestview"

                val result = supabase
                    .postgrest[tableName]
                    .select()
                    .decodeList<AllMembers>()

                Log.e("@@MembVM", "fetchMembers: $result")

                val finalList = if (!isLoggedIn) {
                    result.map {
                        it.copy(
                            email = "Login to view",
                            mobile = "Login to view",
                            enrollment = "Login to view",
                        )
                    }
                } else {
                    result
                }

                Log.e("@@MembVM", "fetchMembers: $finalList")
                _members.value = finalList
                CoroutineScope(Dispatchers.IO).launch {
                    val dao = AppDatabase.getDatabase(getApplication()).allMembersDao()
                    val count = dao.getMemberCount()

                    if (count > 0) {
                        dao.deleteAllMembers()
                        dao.insertAllMembers(finalList)
                    }else{
                        dao.insertAllMembers(finalList)
                    }
                }

            } catch (e: Exception) {
                Log.e("@@Error", "fetchMembers: $e")
                e.printStackTrace()
                _members.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
}

