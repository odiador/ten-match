package co.edu.uniquindio.ingesis.tenmatch.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import co.edu.uniquindio.ingesis.tenmatch.ui.navigation.MainRoutes
import co.edu.uniquindio.ingesis.tenmatch.ui.theme.*

@Composable
fun MemoryGameStartScreen(navController: NavController) {
    var playerName by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        // --- 1. ICONO DE CEREBRO ---
        Box(
            modifier = Modifier
                .size(80.dp)
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(20.dp))
                .background(Color.White, shape = RoundedCornerShape(20.dp))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "🧠", fontSize = 40.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- 2. TÍTULOS ---
        Text(
            text = "Ten Match",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )
        Text(
            text = "Encuentra los pares",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = TextPrimary,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // --- 3. TARJETA DE INSTRUCCIONES ---
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = InstructionCardBg)
        ) {
            Text(
                text = "Selecciona dos cartas que sumen 10 para eliminarlas. ¡Limpia el tablero para ganar!",
                fontSize = 15.sp,
                color = ModeText,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp,
                modifier = Modifier.padding(16.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- 4. CAPTURA DE NOMBRE (Requisito de la guía) ---
        OutlinedTextField(
            value = playerName,
            onValueChange = { playerName = it },
            label = { Text("Nombre del Jugador") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PlayButtonBg,
                unfocusedBorderColor = CardBorderColor
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        // --- 5. MODOS DE JUEGO ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            GameModeCard(icon = Icons.Filled.Face, text = "Entrena")
            GameModeCard(icon = Icons.Filled.Build, text = "Rápido")
            GameModeCard(icon = Icons.Filled.Star, text = "Compite")
        }

        Spacer(modifier = Modifier.weight(1f))

        // --- 6. BOTÓN "JUGAR AHORA" ---
        Button(
            onClick = {
                if (playerName.isNotBlank()) {
                    navController.navigate(MainRoutes.Game.createRoute(playerName))
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            shape = RoundedCornerShape(16.dp),
            enabled = playerName.isNotBlank(),
            colors = ButtonDefaults.buttonColors(
                containerColor = PlayButtonBg,
                contentColor = TextPrimary,
                disabledContainerColor = CardBorderColor
            )
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "¡Jugar ahora!",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterStart)
                )
                Icon(
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = null,
                    modifier = Modifier
                        .size(28.dp)
                        .align(Alignment.CenterEnd)
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun GameModeCard(icon: ImageVector, text: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(95.dp)
            .border(width = 1.dp, color = CardBorderColor, shape = RoundedCornerShape(16.dp))
            .background(Color.White, shape = RoundedCornerShape(16.dp))
            .padding(12.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            tint = TextPrimary,
            modifier = Modifier.size(28.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = text,
            fontSize = 12.sp,
            color = TextPrimary,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MenuPreview() {
    CardGameTheme {
        MemoryGameStartScreen(navController = rememberNavController())
    }
}