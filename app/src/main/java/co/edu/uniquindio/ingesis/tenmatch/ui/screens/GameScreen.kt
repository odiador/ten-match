package co.edu.uniquindio.ingesis.tenmatch.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import co.edu.uniquindio.ingesis.tenmatch.game.CardState
import co.edu.uniquindio.ingesis.tenmatch.game.GameViewModel
import co.edu.uniquindio.ingesis.tenmatch.ui.components.ConfirmAlertDialog
import co.edu.uniquindio.ingesis.tenmatch.ui.navigation.MainRoutes
import co.edu.uniquindio.ingesis.tenmatch.ui.theme.*

@Composable
fun GameScreen(
    navController: NavHostController,
    playerName: String,
    viewModel: GameViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showExitDialog by remember { mutableStateOf(false) }

    // Intercept back button
    BackHandler {
        showExitDialog = true
    }

    LaunchedEffect(Unit) {
        viewModel.startGame(playerName)
    }

    // Effect to navigate when game is over
    LaunchedEffect(uiState.isGameOver) {
        if (uiState.isGameOver) {
            navController.navigate(MainRoutes.Results.createRoute(playerName, uiState.score)) {
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
        // Updated Header with Row for Name and Score
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = uiState.playerName,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Puntaje: ${uiState.score}",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(uiState.cards) { index, card ->
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
    val backgroundColor by animateColorAsState(
        targetValue = when (state) {
            CardState.HIDDEN -> MaterialTheme.colorScheme.primaryContainer
            CardState.VISIBLE -> MaterialTheme.colorScheme.secondaryContainer
            CardState.REMOVED -> Color.Transparent
        },
        animationSpec = tween(durationMillis = 300),
        label = "cardColor"
    )

    Surface(
        modifier = Modifier
            .aspectRatio(1f)
            .clickable(enabled = state == CardState.HIDDEN) { onClick() },
        shape = MaterialTheme.shapes.medium,
        color = backgroundColor,
        shadowElevation = if (state == CardState.REMOVED) 0.dp else 4.dp
    ) {
        Box(contentAlignment = Alignment.Center) {
            AnimatedContent(
                targetState = state,
                transitionSpec = {
                    fadeIn(animationSpec = tween(300)).togetherWith(fadeOut(animationSpec = tween(300)))
                },
                label = "cardContent"
            ) { targetState ->
                when (targetState) {
                    CardState.VISIBLE -> {
                        Text(
                            text = value.toString(),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    CardState.HIDDEN -> {
                        Text(
                            text = "?",
                            fontSize = 24.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                    CardState.REMOVED -> {
                        Spacer(modifier = Modifier.fillMaxSize())
                    }
                }
            }
        }
    }
}
