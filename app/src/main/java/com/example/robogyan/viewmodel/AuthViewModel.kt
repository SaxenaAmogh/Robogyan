package com.example.robogyan.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.robogyan.SupabaseClientProvider
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val auth = SupabaseClientProvider.client.auth

    // Expose auth state
    private val _isLoggedIn = mutableStateOf(auth.currentSessionOrNull() != null)
    val isLoggedIn: State<Boolean> = _isLoggedIn

    private val _userEmail = mutableStateOf(auth.currentUserOrNull()?.email)
    val userEmail: State<String?> = _userEmail

    // Login
    fun login(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            try {
                auth.signInWith(Email) {
                    this.email = email
                    this.password = password
                }
                _isLoggedIn.value = true
                _userEmail.value = auth.currentUserOrNull()?.email
                onResult(true, null)
            } catch (e: Exception) {
                onResult(false, e.message)
            }
        }
    }

    // Logout
    fun logout() {
        viewModelScope.launch {
            auth.signOut()
            _isLoggedIn.value = false
            _userEmail.value = null
        }
    }

}
