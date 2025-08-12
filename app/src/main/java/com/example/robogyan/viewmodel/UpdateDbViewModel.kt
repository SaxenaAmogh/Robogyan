package com.example.robogyan.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.robogyan.SupabaseClientProvider
import com.example.robogyan.data.local.AppDatabase
import com.example.robogyan.data.local.entities.AllMembers
import com.example.robogyan.data.local.entities.AssetUsage
import com.example.robogyan.data.local.entities.Projects
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UpdateDbViewModel(application: Application) : AndroidViewModel(application) {

    private val supabase = SupabaseClientProvider.client

    fun updateMemberDb(id: String){
        viewModelScope.launch(Dispatchers.IO) {
            val result = supabase
                .postgrest["members"]
                .select{
                    filter {
                        eq("id", id)
                    }
                    limit(1)
                }
                .decodeSingle<AllMembers>()

            AppDatabase.getDatabase(getApplication()).allMembersDao().updateMember(result)
        }
    }

    fun updateProjectDb(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            val result = supabase
                .postgrest["projects"]
                .select{
                    filter {
                        eq("id", id)
                    }
                    limit(1)
                }
                .decodeSingle<Projects>()
            Log.d("&&UpdateDbProj", "updateProjectDb: $result")

            val up = AppDatabase.getDatabase(getApplication()).projectsDao().updateProject(result)
            Log.d("&&UpdateDbProj", "updateProjectDb: $up")
        }
    }

    fun updateUsageDb(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            val result = supabase
                .postgrest["asset_usage"]
                .select{
                    filter {
                        eq("id", id)
                    }
                    limit(1)
                }
                .decodeSingle<AssetUsage>()
            Log.d("&&UpdateDbProj", "updateUsageDb: $result")

            val up = AppDatabase.getDatabase(getApplication()).assetUsageDao().updateUsage(result)
            Log.d("&&UpdateDbUsage", "updateUsageDb: $up")
        }
    }
}