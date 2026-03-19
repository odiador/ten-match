package co.edu.uniquindio.ingesis.tenmatch.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import co.edu.uniquindio.ingesis.tenmatch.ui.components.CircularProfileImage

@Composable
fun ProfileScreen(userName: String, userEmail: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Mi Perfil",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Point 4: Circular Profile Image using Coil (AsyncImage)
        CircularProfileImage(
            imageUrl = "https://example.com/profile.jpg", // Placeholder URL
            contentDescription = "Foto de perfil",
            modifier = Modifier.size(120.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = userName, style = MaterialTheme.typography.titleLarge)
        Text(text = userEmail, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.secondary)

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = { /* Edit Profile Action */ }, modifier = Modifier.fillMaxWidth()) {
            Text("Editar Perfil")
        }
        
        OutlinedButton(onClick = { /* Logout */ }, modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
            Text("Cerrar Sesión")
        }
    }
}
