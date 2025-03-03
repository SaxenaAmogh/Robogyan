package com.example.robogyan.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.robogyan.model.GateLogs
import com.example.robogyan.repository.GateLogsRepository
import kotlinx.coroutines.launch

class GateLogsViewModel: ViewModel() {

    private val gateLogsRepository = GateLogsRepository()
    private val _gateLogs = MutableLiveData<List<GateLogs>>()
    val gateLogs: LiveData<List<GateLogs>> get() = _gateLogs

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    init {
        fetchGateLogs()
    }

    private fun fetchGateLogs() {
        viewModelScope.launch {
            try {
                val response2 = gateLogsRepository.getGateLogs()
                if (response2.isSuccessful && response2.body() != null) {
                    _gateLogs.postValue(response2.body()!!.items)
                } else {
                    _error.postValue("Error: ${response2.code()}")
                }
            } catch (e: Exception) {
                _error.postValue(e.message ?: "Unknown Error")
            }
        }
    }
}