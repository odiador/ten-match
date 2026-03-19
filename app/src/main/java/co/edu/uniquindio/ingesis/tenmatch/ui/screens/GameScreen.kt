package co.edu.uniquindio.ingesis.tenmatch.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import co.edu.uniquindio.ingesis.tenmatch.game.CardState
import co.edu.uniquindio.ingesis.tenmatch.game.GameViewModel
import co.edu.uniquindio.ingesis.tenmatch.ui.components.ConfirmAlertDialog
import co.edu.uniquindio.ingesis.tenmatch.ui.navigation.MainRoutes

@Composable
fun GameScreen(
    navController: NavHostController,
    playerName: String,
    viewModel: GameViewModel = viewModel()
) {
    var showExitDialog by remember { mutableStateOf(false) }

    // Intercept back button
    BackHandler {
        showExitDialog = true
    }

    LaunchedEffect(Unit) {
        viewModel.startGame(playerName)
    }

    // Effect to navigate when game is over
    LaunchedEffect(viewModel.isGameOver.value) {
        if (viewModel.isGameOver.value) {
            navController.navigate(MainRoutes.Results.createRoute(playerName, viewModel.score.value)) {
                popUpTo(MainRoutes.Start.route) { inclusive = false }
            }
        }
    }

    if (showExitDialog) {
        ConfirmAlertDialog(
            title = "¿Salir del juego?",
            text = "Se perderá tu progreso actual.",
            onConfirm = { navController.popBackStack() },
            onDismiss = { showExitDialog = false }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Jugador: $playerName",
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = "Puntaje: ${viewModel.score.value}",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.secondary
        )

        Spacer(modifier = Modifier.height(24.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(viewModel.cards) { index, card ->
                CardItem(
                    value = card.value,
                    state = card.state,
                    onClick = { viewModel.onCardClick(index) }
                )
            }
        }
    }
}

@Composable
fun CardItem(value: Int, state: CardState, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .aspectRatio(1f)
            .clickable(enabled = state == CardState.HIDDEN) { onClick() },
        shape = MaterialTheme.shapes.medium,
        color = when (state) {
            CardState.HIDDEN -> MaterialTheme.colorScheme.primaryContainer
            CardState.VISIBLE -> MaterialTheme.colorScheme.secondaryContainer
            CardState.REMOVED -> Color.Transparent
        },
        shadowElevation = if (state == CardState.REMOVED) 0.dp else 4.dp
    ) {
        Box(contentAlignment = Alignment.Center) {
            if (state == CardState.VISIBLE) {
                Text(
                    text = value.toString(),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            } else if (state == CardState.HIDDEN) {
                Text(
                    text = "?",
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}
