package com.abdo.productionmanager

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.abdo.core.domain.model.TaskPriority
import com.abdo.core.domain.model.TaskStatus
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun DashboardScreen(
    onNavigateToProductionEntry: () -> Unit,
    onNavigateToHistory: () -> Unit,
    onNavigateToProducts: () -> Unit,
    onNavigateToTasks: () -> Unit,
    onNavigateToFinance: () -> Unit,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.primaryContainer
                            )
                        )
                    )
                    .padding(24.dp)
            ) {
                Column {
                    Text(
                        text = "مرحباً يا عبده 👋",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
                    )
                    Text(
                        text = "مدير الإنتاج",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = uiState.todayDate.let {
                            "${it.dayOfWeek.getDisplayName(TextStyle.FULL, Locale("ar"))} " +
                            "${it.dayOfMonth} " +
                            it.month.getDisplayName(TextStyle.FULL, Locale("ar"))
                        },
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                // كارد إنتاج اليوم
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    ),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "إنتاج اليوم",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.8f)
                            )
                            Icon(
                                Icons.Default.Factory,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.6f),
                                modifier = Modifier.size(28.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "${uiState.dailyProduction?.totalQuantity ?: 0}",
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSecondary,
                            fontSize = 48.sp
                        )
                        Text(
                            text = "قطعة منتجة",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.7f)
                        )
                        if ((uiState.dailyProduction?.totalValue ?: 0.0) > 0) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "القيمة: ${String.format("%.2f", uiState.dailyProduction?.totalValue)} ج",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.8f)
                            )
                        }
                    }
                }

                // أزرار الإجراءات السريعة
                Text(
                    text = "إجراءات سريعة",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    QuickActionCard(
                        icon = Icons.Default.Add,
                        label = "تسجيل\nإنتاج",
                        color = MaterialTheme.colorScheme.secondary,
                        onClick = onNavigateToProductionEntry,
                        modifier = Modifier.weight(1f)
                    )
                    QuickActionCard(
                        icon = Icons.Default.History,
                        label = "سجل\nالإنتاج",
                        color = MaterialTheme.colorScheme.primary,
                        onClick = onNavigateToHistory,
                        modifier = Modifier.weight(1f)
                    )
                    QuickActionCard(
                        icon = Icons.Default.Inventory,
                        label = "إدارة\nالمنتجات",
                        color = Color(0xFF16A085),
                        onClick = onNavigateToProducts,
                        modifier = Modifier.weight(1f)
                    )
                    QuickActionCard(
                        icon = Icons.Default.Task,
                        label = "المهام",
                        color = Color(0xFF8E44AD),
                        onClick = onNavigateToTasks,
                        modifier = Modifier.weight(1f)
                    )
                }

                // آخر سجلات الإنتاج
                if ((uiState.dailyProduction?.entries?.size ?: 0) > 0) {
                    Text(
                        text = "سجلات اليوم",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            uiState.dailyProduction?.entries?.forEach { entry ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 6.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            Icons.Default.Circle,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.secondary,
                                            modifier = Modifier.size(8.dp)
                                        )
                                        Text(
                                            text = entry.productName.ifBlank { "منتج #${entry.productId}" },
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    }
                                    Text(
                                        text = "${entry.quantity} قطعة",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.secondary
                                    )
                                }
                                if (entry != uiState.dailyProduction?.entries?.last()) {
                                    HorizontalDivider(
                                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                                    )
                                }
                            }
                        }
                    }
                }

                // المهام العاجلة
                if (uiState.pendingTasks.isNotEmpty()) {
                    Text(
                        text = "المهام العاجلة",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            uiState.pendingTasks.forEach { task ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 6.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            when (task.status) {
                                                TaskStatus.IN_PROGRESS -> Icons.Default.Autorenew
                                                else -> Icons.Default.RadioButtonUnchecked
                                            },
                                            contentDescription = null,
                                            tint = when (task.priority) {
                                                TaskPriority.HIGH -> MaterialTheme.colorScheme.error
                                                TaskPriority.MEDIUM -> MaterialTheme.colorScheme.secondary
                                                TaskPriority.LOW -> MaterialTheme.colorScheme.outline
                                            },
                                            modifier = Modifier.size(20.dp)
                                        )
                                        Text(
                                            text = task.title,
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    }
                                    Surface(
                                        shape = RoundedCornerShape(4.dp),
                                        color = when (task.priority) {
                                            TaskPriority.HIGH -> MaterialTheme.colorScheme.error.copy(alpha = 0.15f)
                                            TaskPriority.MEDIUM -> MaterialTheme.colorScheme.secondary.copy(alpha = 0.15f)
                                            TaskPriority.LOW -> MaterialTheme.colorScheme.outline.copy(alpha = 0.15f)
                                        }
                                    ) {
                                        Text(
                                            text = when (task.priority) {
                                                TaskPriority.HIGH -> "عالية"
                                                TaskPriority.MEDIUM -> "متوسطة"
                                                TaskPriority.LOW -> "منخفضة"
                                            },
                                            style = MaterialTheme.typography.labelSmall,
                                            color = when (task.priority) {
                                                TaskPriority.HIGH -> MaterialTheme.colorScheme.error
                                                TaskPriority.MEDIUM -> MaterialTheme.colorScheme.secondary
                                                TaskPriority.LOW -> MaterialTheme.colorScheme.outline
                                            },
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                                if (task != uiState.pendingTasks.last()) {
                                    HorizontalDivider(
                                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            TextButton(
                                onClick = onNavigateToTasks,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("عرض كل المهام ←")
                            }
                        }
                    }
                }

                // لو مفيش بيانات
                if ((uiState.dailyProduction?.totalQuantity ?: 0) == 0 &&
                    uiState.pendingTasks.isEmpty()
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(text = "🌟", fontSize = 40.sp)
                            Text(
                                text = "ابدأ يومك!",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "سجل أول إنتاج أو أضف مهمة جديدة",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun QuickActionCard(
    icon: ImageVector,
    label: String,
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.1f)
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(28.dp)
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = color,
                fontWeight = FontWeight.Bold,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}
