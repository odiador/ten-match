package co.edu.uniquindio.ingesis.tenmatch.ui.screens
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Face    // Icono garantizado para Entrena
import androidx.compose.material.icons.filled.Build   // Icono garantizado para Rápido
import androidx.compose.material.icons.filled.Star    // Icono garantizado para Compite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import com.example.cardgame.ui.theme.*

@Composable
fun MemoryGameStartScreen(onStartGameClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(60.dp))

        // --- 1. ICONO DE CEREBRO ---
        Box(
            modifier = Modifier
                .size(100.dp)
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(28.dp))
                .background(Color.White, shape = RoundedCornerShape(28.dp))
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "🧠", fontSize = 50.sp)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- 2. TÍTULOS ---
        Text(
            text = "Memoria",
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )
        Text(
            text = "Match Game",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // --- 3. TARJETA DE INSTRUCCIONES ---
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = InstructionCardBg)
        ) {
            Text(
                text = "Voltea las tarjetas y encuentra los pares iguales. Memoriza la posición de cada carta para completar el tablero en el menor tiempo posible.",
                fontSize = 16.sp,
                color = ModeText,
                textAlign = TextAlign.Center,
                lineHeight = 22.sp,
                modifier = Modifier.padding(24.dp)
            )
        }

        Spacer(modifier = Modifier.height(48.dp))

        // --- 4. MODOS DE JUEGO (FILA CON ICONOS FILLED) ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            GameModeCard(icon = Icons.Filled.Face, text = "Entrena")
            GameModeCard(icon = Icons.Filled.Build, text = "Rápido")
            GameModeCard(icon = Icons.Filled.Star, text = "Compite")
        }

        Spacer(modifier = Modifier.weight(1f))

        // --- 5. TEXTO DE PREPARACIÓN ---
        Text(
            text = "¿ESTAS PREPARAD@?",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // --- 6. BOTÓN "JUGAR AHORA" ---
        Button(
            onClick = { onStartGameClick() },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PlayButtonBg,
                contentColor = TextPrimary
            )
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "Jugar ahora",
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
            .width(100.dp)
            .border(width = 1.dp, color = CardBorderColor, shape = RoundedCornerShape(16.dp))
            .background(Color.White, shape = RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            tint = TextPrimary,
            modifier = Modifier.size(32.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = text,
            fontSize = 14.sp,
            color = TextPrimary,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MenuPreview() {
    CardGameTheme {
        MemoryGameStartScreen(onStartGameClick = {})
    }
}