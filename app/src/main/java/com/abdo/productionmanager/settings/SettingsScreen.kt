package com.abdo.productionmanager.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.abdo.core.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var showDailyTimePicker by remember { mutableStateOf(false) }
    var showSummaryTimePicker by remember { mutableStateOf(false) }
    var editName by remember { mutableStateOf(false) }
    var editJob by remember { mutableStateOf(false) }
    var tempName by remember { mutableStateOf("") }
    var tempJob by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("الإعدادات", fontWeight = FontWeight.Bold)
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "رجوع")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // ===== معلومات المستخدم =====
            SettingsSection(title = "👤 معلومات المستخدم") {
                SettingsItem(
                    icon = Icons.Default.Person,
                    title = "الاسم",
                    subtitle = uiState.userName,
                    onClick = {
                        tempName = uiState.userName
                        editName = true
                    }
                )
                SettingsItem(
                    icon = Icons.Default.Work,
                    title = "المهنة",
                    subtitle = uiState.userJob,
                    onClick = {
                        tempJob = uiState.userJob
                        editJob = true
                    }
                )
            }

            // ===== الثيم =====
            SettingsSection(title = "🎨 المظهر") {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "اختر ثيم التطبيق",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    // الثيمات الـ 5
                    AppTheme.entries.forEach { theme ->
                        ThemeOptionRow(
                            theme = theme,
                            isSelected = theme.id == uiState.themeId,
                            onClick = { viewModel.setTheme(theme.id) }
                        )
                    }

                    HorizontalDivider()

                    // Dark Mode
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                if (uiState.darkMode) Icons.Default.DarkMode
                                else Icons.Default.LightMode,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Column {
                                Text(
                                    text = "الوضع الليلي",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = if (uiState.darkMode) "مفعّل" else "معطّل",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.outline
                                )
                            }
                        }
                        Switch(
                            checked = uiState.darkMode,
                            onCheckedChange = { viewModel.setDarkMode(it) }
                        )
                    }
                }
            }

            // ===== الإشعارات =====
            SettingsSection(title = "🔔 الإشعارات") {
                // تذكير يومي
                NotificationItem(
                    icon = "⏰",
                    title = "تذكير تسجيل الإنتاج",
                    subtitle = "كل يوم الساعة ${
                        String.format("%02d:%02d", uiState.notifDailyHour, uiState.notifDailyMinute)
                    }",
                    enabled = uiState.notifDailyEnabled,
                    onToggle = {
                        viewModel.setNotifDaily(
                            it,
                            uiState.notifDailyHour,
                            uiState.notifDailyMinute
                        )
                    },
                    onTimeClick = { showDailyTimePicker = true }
                )

                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))

                // ملخص مسائي
                NotificationItem(
                    icon = "📊",
                    title = "ملخص الإنتاج المسائي",
                    subtitle = "كل يوم الساعة ${
                        String.format("%02d:%02d", uiState.notifSummaryHour, uiState.notifSummaryMinute)
                    }",
                    enabled = uiState.notifSummaryEnabled,
                    onToggle = {
                        viewModel.setNotifSummary(
                            it,
                            uiState.notifSummaryHour,
                            uiState.notifSummaryMinute
                        )
                    },
                    onTimeClick = { showSummaryTimePicker = true }
                )

                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))

                // تذكير المهام
                NotificationItem(
                    icon = "✅",
                    title = "تذكير المهام المتأخرة",
                    subtitle = "كل يوم الساعة 9:00 صباحاً",
                    enabled = uiState.notifTasksEnabled,
                    onToggle = { viewModel.setNotifTasks(it) },
                    onTimeClick = null
                )
            }

            // ===== عن التطبيق =====
            SettingsSection(title = "ℹ️ عن التطبيق") {
                SettingsInfoItem("الإصدار", "1.0.0")
                SettingsInfoItem("المطور", "عبده")
                SettingsInfoItem("التقنية", "Kotlin + Jetpack Compose")
            }
        }
    }

    // Time Pickers
    if (showDailyTimePicker) {
        TimePickerDialog(
            initialHour = uiState.notifDailyHour,
            initialMinute = uiState.notifDailyMinute,
            onDismiss = { showDailyTimePicker = false },
            onConfirm = { h, m ->
                viewModel.setNotifDaily(true, h, m)
                showDailyTimePicker = false
            }
        )
    }

    if (showSummaryTimePicker) {
        TimePickerDialog(
            initialHour = uiState.notifSummaryHour,
            initialMinute = uiState.notifSummaryMinute,
            onDismiss = { showSummaryTimePicker = false },
            onConfirm = { h, m ->
                viewModel.setNotifSummary(true, h, m)
                showSummaryTimePicker = false
            }
        )
    }

    // Edit Name Dialog
    if (editName) {
        EditTextDialog(
            title = "تعديل الاسم",
            value = tempName,
            onValueChange = { tempName = it },
            onDismiss = { editName = false },
            onConfirm = {
                viewModel.setUserName(tempName)
                editName = false
            }
        )
    }

    // Edit Job Dialog
    if (editJob) {
        EditTextDialog(
            title = "تعديل المهنة",
            value = tempJob,
            onValueChange = { tempJob = it },
            onDismiss = { editJob = false },
            onConfirm = {
                viewModel.setUserJob(tempJob)
                editJob = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    initialHour: Int,
    initialMinute: Int,
    onDismiss: () -> Unit,
    onConfirm: (Int, Int) -> Unit
) {
    val timePickerState = rememberTimePickerState(
        initialHour = initialHour,
        initialMinute = initialMinute,
        is24Hour = true
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "اختر الوقت",
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                TimePicker(state = timePickerState)
            }
        },
        confirmButton = {
            Button(onClick = {
                onConfirm(timePickerState.hour, timePickerState.minute)
            }) {
                Text("تأكيد")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("إلغاء")
            }
        }
    )
}

@Composable
fun ThemeOptionRow(
    theme: AppTheme,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(
                if (isSelected) MaterialTheme.colorScheme.primaryContainer
                else MaterialTheme.colorScheme.surfaceVariant
            )
            .clickable { onClick() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(
                        if (isSelected) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(text = theme.emoji, fontSize = 20.sp)
            }
            Text(
                text = theme.nameAr,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer
                else MaterialTheme.colorScheme.onSurface
            )
        }
        if (isSelected) {
            Icon(
                Icons.Default.CheckCircle,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun NotificationItem(
    icon: String,
    title: String,
    subtitle: String,
    enabled: Boolean,
    onToggle: (Boolean) -> Unit,
    onTimeClick: (() -> Unit)?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Text(text = icon, fontSize = 24.sp)
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                    if (onTimeClick != null && enabled) {
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = "تعديل الوقت",
                            modifier = Modifier
                                .size(14.dp)
                                .clickable { onTimeClick() },
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
        Switch(
            checked = enabled,
            onCheckedChange = onToggle
        )
    }
}

@Composable
fun SettingsSection(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp, start = 4.dp)
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(2.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(content = content)
        }
    }
}

@Composable
fun SettingsItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }
        Icon(
            Icons.Default.ChevronRight,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.outline
        )
    }
}

@Composable
fun SettingsInfoItem(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.outline
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun EditTextDialog(
    title: String,
    value: String,
    onValueChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title, fontWeight = FontWeight.Bold) },
        text = {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                enabled = value.isNotBlank()
            ) { Text("حفظ") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("إلغاء") }
        }
    )
}
