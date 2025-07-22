package com.example.robogyan.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.robogyan.SupabaseClientProvider
import com.example.robogyan.data.local.AppDatabase
import com.example.robogyan.data.local.entities.Inventory
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class InventoryViewModel(application: Application) : AndroidViewModel(application)  {

    private val supabase = SupabaseClientProvider.client
    private val _inventory = MutableStateFlow<List<Inventory>>(emptyList())
    private val _isLoading = MutableStateFlow(false)

    fun fetchAssets() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val isLoggedIn = SupabaseClientProvider.client.auth.currentSessionOrNull() != null
                val tableName = "inventory"

                if (isLoggedIn){
                    val result = supabase
                        .postgrest[tableName]
                        .select()
                        .decodeList<Inventory>()

                    Log.e("@@Inventory", "Inventory: $result")
                    _inventory.value = result
                    CoroutineScope(Dispatchers.IO).launch {
                        val dao = AppDatabase.getDatabase(getApplication()).inventoryDao()
                        val count = dao.getInventoryCount()

                        if (count > 0) {
                            dao.deleteInventory()
                            dao.insertInventory(result)
                        }else{
                            dao.insertInventory(result)
                        }
                    }
                }else{
                    Log.d("Fetch Inventory", "Task aborted for Guest User.")
                }

            } catch (e: Exception) {
                Log.e("@@Error", "fetchMembers: $e")
                e.printStackTrace()
                _inventory.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    private val dao = AppDatabase.getDatabase(application).inventoryDao()
    val inventoryFlow = dao.getInventory().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
}