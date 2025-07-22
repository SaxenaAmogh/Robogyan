package com.example.robogyan.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.robogyan.SupabaseClientProvider
import com.example.robogyan.data.local.AppDatabase
import com.example.robogyan.data.local.entities.Projects
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProjectsViewModel(application: Application) : AndroidViewModel(application) {

    private val supabase = SupabaseClientProvider.client
    private val _projects = MutableStateFlow<List<Projects>>(emptyList())
    private val _isLoading = MutableStateFlow(false)

    fun fetchProjects() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val isLoggedIn = SupabaseClientProvider.client.auth.currentSessionOrNull() != null
                val tableName = "projects"

                if (isLoggedIn){
                    val result = supabase
                        .postgrest[tableName]
                        .select()
                        .decodeList<Projects>()

                    Log.e("@@Projects", "Projects: $result")
                    _projects.value = result
                    CoroutineScope(Dispatchers.IO).launch {
                        val dao = AppDatabase.getDatabase(getApplication()).projectsDao()
                        val count = dao.getProjectsCount()

                        if (count > 0) {
                            dao.deleteProjects()
                            dao.insertProjects(result)
                        }else{
                            dao.insertProjects(result)
                        }
                    }
                }else{
                    Log.d("Fetch Projects", "Task aborted for Guest User.")
                }

            } catch (e: Exception) {
                Log.e("@@Error", "fetchMembers: $e")
                e.printStackTrace()
                _projects.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    private val dao = AppDatabase.getDatabase(application).projectsDao()
    val projectsFlow = dao.getProjects().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

}