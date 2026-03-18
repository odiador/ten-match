package com.example.tenmatch.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tenmatch.domain.Report
import com.example.tenmatch.domain.ReportStatus

@Composable
fun HomeScreen(reports: List<Report>) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Reportes Recientes",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        // Point 5: Using LazyColumn for vertical lists
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(reports) { report ->
                ReportItem(report)
            }
        }
    }
}

@Composable
fun ReportItem(report: Report) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = report.title, style = MaterialTheme.typography.titleLarge)
            Text(
                text = "Estado: ${report.status}",
                style = MaterialTheme.typography.bodyMedium,
                color = when (report.status) {
                    ReportStatus.RESOLVED -> androidx.compose.ui.graphics.Color.Green
                    ReportStatus.PENDING -> androidx.compose.ui.graphics.Color.Blue
                    ReportStatus.REJECTED -> androidx.compose.ui.graphics.Color.Red
                }
            )
            Text(
                text = "Ubicación: ${report.location.address}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
