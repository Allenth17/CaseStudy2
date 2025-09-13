package com.allenth17.casestudy2.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.allenth17.casestudy2.networking.User
import com.allenth17.casestudy2.screen.ui.UserCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: UserViewModel,
    onUserClick: (User) -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.result.collectAsState()
    val (ascending, setAscending) = remember { mutableStateOf(true) }
    val (query, setQuery) = remember { mutableStateOf("") }

    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "User List")
                },
                actions = {
                    IconButton(onClick = { setAscending(!ascending) }) {
                        val icon = if (ascending) Icons.Filled.ArrowUpward else Icons.Filled.ArrowDownward
                        val cd = if (ascending) "Sort ascending" else "Sort descending"
                        Icon(imageVector = icon, contentDescription = cd)
                    }
                }
            )
        }
    ) { paddingValues ->

        when (val ui = state) {
            is UiState.Loading, UiState.Idle -> {
                LoadingContent(paddingValues)
            }
            is UiState.Error -> {
                ErrorContent(message = ui.message, paddingValues = paddingValues) {
                    viewModel.getData()
                }
            }
            is UiState.Success -> {
                // Filter first by query, then sort
                val filtered = ui.data.filter { user ->
                    val name = (user.firstName + " " + user.lastName).lowercase()
                    name.contains(query.lowercase())
                }
                androidx.compose.foundation.layout.Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    OutlinedTextField(
                        value = query,
                        onValueChange = setQuery,
                        singleLine = true,
                        placeholder = { Text("Search by name") },
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .fillMaxWidth(),
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search"
                            )
                        }
                    )
                    val sorted = if (ascending) filtered.sortedBy { it.firstName } else filtered.sortedByDescending { it.firstName }
                    UserList(users = sorted, paddingValues = PaddingValues(0.dp)) { user ->
                        onUserClick(user)
                    }
                }
            }
        }
    }
}

@Composable
private fun UserList(
    users: List<User>,
    paddingValues: PaddingValues,
    onClick: (User) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 300.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
    ) {
        items(users, key = { it.id }) { user ->
            UserCard(
                user = user,
                onClick = { onClick(user) },
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
private fun LoadingContent(paddingValues: PaddingValues) {
    androidx.compose.foundation.layout.Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        androidx.compose.material3.CircularProgressIndicator()
    }
}

@Composable
private fun ErrorContent(
    message: String,
    paddingValues: PaddingValues,
    onRetry: () -> Unit
) {
    androidx.compose.foundation.layout.Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(24.dp),
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        Text(text = message)
        androidx.compose.foundation.layout.Spacer(modifier = Modifier.padding(8.dp))
        androidx.compose.material3.Button(onClick = onRetry) {
            Text(text = "Retry")
        }
    }
}

