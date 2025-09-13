package com.allenth17.casestudy2.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.allenth17.casestudy2.nav.Destination
import com.allenth17.casestudy2.nav.Navigator
import com.allenth17.casestudy2.networking.User
import com.allenth17.casestudy2.networking.domain.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(
    private val navigator: Navigator
): ViewModel() {
    private val apiInterface = RetrofitInstance.apiInterface

    private val _result = MutableStateFlow<UiState<List<User>>>(UiState.Idle)
    val result : StateFlow<UiState<List<User>>> = _result

    private val _selectedUser = MutableStateFlow<User?>(null)
    fun getSelectedUser(): User? { return _selectedUser.value }

    init {
        getData()
    }

    fun getData() {
        _result.value = UiState.Loading
        viewModelScope.launch {
            try {
                val response = apiInterface.getUsers()
                if (response.isSuccessful) {
                    val list = response.body()?.users ?: emptyList()
                    _result.value = UiState.Success(list)
                } else {
                    _result.value = UiState.Error("HTTP ${response.code()} ${response.message()}")
                }
            } catch (e: Exception) {
                _result.value = UiState.Error("Error: ${e.message}")
            }
        }
    }

    fun goToDetail(id: Int) {
        viewModelScope.launch {
            navigator.navigate(destination = Destination.UserDetailScreen(id))
        }
    }
    fun goToHome() {
        viewModelScope.launch {
            navigator.navigateUp()
        }
    }
}