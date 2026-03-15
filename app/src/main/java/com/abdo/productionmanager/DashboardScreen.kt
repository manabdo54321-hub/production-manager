package com.abdo.productionmanager

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun DashboardScreen(
    onNavigateToProductionEntry: () -> Unit,
    onNavigateToHistory: () -> Unit,
    onNavigateToProducts: () -> Unit,
    onNavigateToTasks: () -> Unit,
    onNavigateToFinance: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "مدير الإنتاج",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = "اختر ما تريد فعله",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.outline
        )

        Spacer(modifier = Modifier.height(16.dp))

        // أزرار التنقل
        DashboardButton(
            text = "تسجيل الإنتاج",
            icon = Icons.Default.Add,
            onClick = onNavigateToProductionEntry
        )
        DashboardButton(
            text = "سجل الإنتاج",
            icon = Icons.Default.History,
            onClick = onNavigateToHistory
        )
        DashboardButton(
            text = "إدارة المنتجات",
            icon = Icons.Default.Inventory,
            onClick = onNavigateToProducts
        )
        DashboardButton(
            text = "المهام — قريباً",
            icon = Icons.Default.Task,
            onClick = onNavigateToTasks,
            enabled = false
        )
        DashboardButton(
            text = "المالية — قريباً",
            icon = Icons.Default.AttachMoney,
            onClick = onNavigateToFinance,
            enabled = false
        )
    }
}

@Composable
fun DashboardButton(
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Icon(icon, contentDescription = null)
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
    }
}
