package com.example.robogyan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.robogyan.SupabaseClientProvider
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.auth.AuthState
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.exceptions.RestException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class AuthState { // Renamed from LoginState to AuthState for broader scope
    /** Initial state, nothing is happening or user is logged out. */
    data object Idle : AuthState()
    /** Authentication process (login or logout) is currently ongoing. */
    object Loading : AuthState()
    /** Login was successful. */
    object LoggedIn : AuthState() // Renamed from Success to LoggedIn for clarity
    /** Authentication failed, contains an error message. */
    data class Error(val message: String) : AuthState()
}

/**
 * ViewModel responsible for handling user authentication with Supabase.
 * It exposes a StateFlow to observe the authentication status from the UI.
 */
class AuthViewModel : ViewModel() {

    // MutableStateFlow to hold the current authentication state, private to prevent external modification.
    private val _authState = MutableStateFlow<AuthState>(com.example.robogyan.viewmodel.AuthState.Idle)

    // Publicly exposed StateFlow for the UI to observe changes in authentication state.
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    /**
     * Initiates the login process with email and password using Supabase.
     * This function runs in a coroutine within the ViewModel's scope.
     *
     * @param email The user's email address.
     * @param password The user's password.
     */
    fun loginWithEmailAndPassword(email: String, password: String) {
        // Launch a coroutine in the viewModelScope to perform network operation safely.
        viewModelScope.launch {
            // Set the state to Loading immediately to show a progress indicator in the UI.
            _authState.value = com.example.robogyan.viewmodel.AuthState.Loading
            try {
                val response = SupabaseClientProvider.client.auth.signInWith(Email) {
                    this.email = email
                    this.password = password
                }
                _authState.value = com.example.robogyan.viewmodel.AuthState.LoggedIn
            } catch (e: RestException) {
                // Catch specific Supabase REST API exceptions (e.g., wrong credentials).
                // Extract the error message or provide a default.
                _authState.value = com.example.robogyan.viewmodel.AuthState.Error(e.message ?: "Authentication failed: Unknown Supabase error")
            } catch (e: Exception) {
                // Catch any other unexpected exceptions during the process.
                _authState.value = com.example.robogyan.viewmodel.AuthState.Error("An unexpected error occurred: ${e.localizedMessage ?: "Unknown error"}")
            }
        }
    }

    /**
     * Initiates the logout process for the current user.
     * This function runs in a coroutine within the ViewModel's scope.
     */
    fun logout() {
        viewModelScope.launch {
            _authState.value = com.example.robogyan.viewmodel.AuthState.Loading // Indicate that a process is ongoing
            try {
                // Call the Supabase client's signOut function to terminate the user's session.
                SupabaseClientProvider.client.auth.signOut()
                // If logout is successful, reset the state to Idle (meaning no user is logged in).
                _authState.value = com.example.robogyan.viewmodel.AuthState.Idle
            } catch (e: RestException) {
                // Catch specific Supabase REST API exceptions during logout.
                _authState.value = com.example.robogyan.viewmodel.AuthState.Error(e.message ?: "Logout failed: Unknown Supabase error")
            } catch (e: Exception) {
                // Catch any other unexpected exceptions during the logout process.
                _authState.value = com.example.robogyan.viewmodel.AuthState.Error("An unexpected error occurred during logout: ${e.localizedMessage ?: "Unknown error"}")
            }
        }
    }

    /**
     * Resets the authentication state to Idle.
     * This is useful after a successful authentication event (login/logout) or an error,
     * so the UI can be prepared for another attempt or navigate away.
     */
    fun resetAuthState() { // Renamed from resetLoginState to resetAuthState
        _authState.value = com.example.robogyan.viewmodel.AuthState.Idle
    }
}
