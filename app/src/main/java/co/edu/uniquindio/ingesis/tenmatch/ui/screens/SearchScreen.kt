package co.edu.uniquindio.ingesis.tenmatch.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SearchScreen() {
    var searchQuery by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Buscar Reportes",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Point 2: OutlinedTextField with leadingIcon (MD3)
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Buscar por título o ciudad") },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = "Search Icon")
            }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Resultados para: $searchQuery",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
