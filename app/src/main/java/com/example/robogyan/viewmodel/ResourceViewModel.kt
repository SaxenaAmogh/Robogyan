package com.example.robogyan.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.robogyan.SupabaseClientProvider
import com.example.robogyan.data.local.AppDatabase
import com.example.robogyan.data.local.entities.Resources
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ResourceViewModel(application: Application) : AndroidViewModel(application) {

    private val supabase = SupabaseClientProvider.client
    private val _resources = MutableStateFlow<List<Resources>>(emptyList())
    private val _isLoading = MutableStateFlow(false)

    fun fetchResources() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val isLoggedIn = SupabaseClientProvider.client.auth.currentSessionOrNull() != null
                val tableName = "resources"

                if (isLoggedIn){
                    val result = supabase
                        .postgrest[tableName]
                        .select()
                        .decodeList<Resources>()

                    Log.e("@@Res", "Res: $result")
                    _resources.value = result
                    CoroutineScope(Dispatchers.IO).launch {
                        val dao = AppDatabase.getDatabase(getApplication()).resourcesDao()
                        val count = dao.getResourcesCount()

                        if (count > 0) {
                            dao.deleteResources()
                            dao.insertResources(result)
                        }else{
                            dao.insertResources(result)
                        }
                    }
                }else{
                    Log.d("Fetch Projects", "Task aborted for Guest User.")
                }

            } catch (e: Exception) {
                Log.e("@@Error", "fetchMembers: $e")
                e.printStackTrace()
                _resources.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    private val dao = AppDatabase.getDatabase(application).resourcesDao()
    val resourcesFlow = dao.getResources().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

}