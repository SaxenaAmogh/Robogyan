package com.example.robogyan.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.robogyan.SupabaseClientProvider
import com.example.robogyan.data.local.AppDatabase
import com.example.robogyan.data.local.entities.AssetUsage
import com.example.robogyan.data.local.entities.ProjectUpdateData
import com.example.robogyan.data.local.entities.Projects
import com.example.robogyan.utils.SharedPrefManager
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AssetUsageViewModel(application: Application) : AndroidViewModel(application) {

    private val supabase = SupabaseClientProvider.client
    private val _usage = MutableStateFlow<List<AssetUsage>>(emptyList())
    private val _isLoading = MutableStateFlow(false)

    fun fetchUsages() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val isLoggedIn = SupabaseClientProvider.client.auth.currentSessionOrNull() != null
                val tableName = "asset_usage"

                if (isLoggedIn){
                    val result = supabase
                        .postgrest[tableName]
                        .select()
                        .decodeList<AssetUsage>()

                    Log.e("@@Usages", "Usages: $result")
                    _usage.value = result
                    CoroutineScope(Dispatchers.IO).launch {
                        val dao = AppDatabase.getDatabase(getApplication()).assetUsageDao()
                        val count = dao.getUsageCount()

                        if (count > 0) {
                            dao.deleteUsage()
                            dao.insertUsage(result)
                        }else{
                            dao.insertUsage(result)
                        }
                    }
                }else{
                    Log.d("Fetch Projects", "Task aborted for Guest User.")
                }

            } catch (e: Exception) {
                Log.e("@@Error", "fetchUsages: $e")
                e.printStackTrace()
                _usage.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addNewUsage(
        assetId: Int,
        name : String,
        approvedBy : String,
        quantity : String,
        useCase : String,
        status : String,
        projectName : String,
        returnDate : String,
        notes : String,
    ){
        val usage = AssetUsage(
            asset_id = assetId,
            granted_to = name,
            quantity = quantity.toInt(),
            use_case = useCase,
            project_name = projectName,
            status = status,
            return_date = returnDate,
            approved_by = approvedBy,
            notes = notes
        )
        CoroutineScope(Dispatchers.IO).launch {
            val result = SupabaseClientProvider.client
                .postgrest["asset_usage"]
                .insert(usage)
            Log.d("@@SupabaseInsert", "Usage inserted: $result")
        }
    }

    fun updateUsage(
        id: Int,
        assetId: Int,
        name : String,
        approvedBy : String,
        quantity : String,
        useCase : String,
        status : String,
        projectName : String,
        returnDate : String,
        notes : String,
    ){
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val isLoggedIn = SharedPrefManager.isLoggedIn(getApplication())
                if (isLoggedIn) {
                    val tableName = "asset_usage"
                    val usage = AssetUsage(
                        asset_id = assetId,
                        granted_to = name,
                        quantity = quantity.toInt(),
                        use_case = useCase,
                        project_name = projectName,
                        status = status,
                        return_date = returnDate,
                        approved_by = approvedBy,
                        notes = notes
                    )
                    val result = supabase
                        .postgrest[tableName]
                        .update(usage) {
                            filter {
                                eq("id", id)
                            }
                        }
                    Log.e("@@AssetUsage", "usageUpdate: $result && $usage")
                } else {
                    Log.d("Update Usage", "Task aborted for Guest User.")
                }
            } catch (e: Exception) {
                Log.e("@@Error", "usage update: $e")
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}