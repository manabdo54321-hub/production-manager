package com.abdo.feature.finance

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.abdo.core.domain.model.Expense
import com.abdo.core.domain.model.ExpenseCategory
import com.abdo.core.domain.model.Income
import com.abdo.core.domain.model.IncomeCategory
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinanceScreen(
    onNavigateBack: () -> Unit,
    viewModel: FinanceViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var showAddExpense by remember { mutableStateOf(false) }
    var showAddIncome by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("المالية", fontWeight = FontWeight.Bold) },
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
        },
        floatingActionButton = {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FloatingActionButton(
                    onClick = { showAddExpense = true },
                    containerColor = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(Icons.Default.Remove, contentDescription = "مصروف",
                        tint = Color.White)
                }
                FloatingActionButton(
                    onClick = { showAddIncome = true },
                    containerColor = MaterialTheme.colorScheme.secondary
                ) {
                    Icon(Icons.Default.Add, contentDescription = "دخل",
                        tint = Color.White)
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // كارد الملخص المالي
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "الملخص المالي الشهري",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        FinanceStat(
                            label = "الدخل",
                            amount = uiState.summary.totalIncome,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        FinanceStat(
                            label = "المصاريف",
                            amount = uiState.summary.totalExpense,
                            color = MaterialTheme.colorScheme.error
                        )
                        FinanceStat(
                            label = "صافي الربح",
                            amount = uiState.summary.netProfit,
                            color = if (uiState.summary.netProfit >= 0)
                                MaterialTheme.colorScheme.secondary
                            else
                                MaterialTheme.colorScheme.error
                        )
                    }
                }
            }

            // Tabs
            TabRow(selectedTabIndex = selectedTab) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = { Text("الدخل (${uiState.summary.incomes.size})") }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = { Text("المصاريف (${uiState.summary.expenses.size})") }
                )
            }

            // القوائم
            when (selectedTab) {
                0 -> IncomeList(
                    incomes = uiState.summary.incomes,
                    onDelete = { viewModel.onEvent(FinanceEvent.DeleteIncome(it)) }
                )
                1 -> ExpenseList(
                    expenses = uiState.summary.expenses,
                    onDelete = { viewModel.onEvent(FinanceEvent.DeleteExpense(it)) }
                )
            }
        }
    }

    if (showAddExpense) {
        AddExpenseDialog(
            onDismiss = { showAddExpense = false },
            onConfirm = { title, amount, category, notes ->
                viewModel.onEvent(
                    FinanceEvent.AddExpense(title, amount, category, notes)
                )
                showAddExpense = false
            }
        )
    }

    if (showAddIncome) {
        AddIncomeDialog(
            onDismiss = { showAddIncome = false },
            onConfirm = { title, amount, category, notes ->
                viewModel.onEvent(
                    FinanceEvent.AddIncome(title, amount, category, notes)
                )
                showAddIncome = false
            }
        )
    }
}

@Composable
fun FinanceStat(label: String, amount: Double, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "${String.format("%.0f", amount)} ج",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = color,
            fontSize = 18.sp
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

@Composable
fun IncomeList(incomes: List<Income>, onDelete: (Income) -> Unit) {
    val formatter = DateTimeFormatter.ofPattern("dd/MM")
    if (incomes.isEmpty()) {
        EmptyFinanceState(message = "لا يوجد دخل هذا الشهر", icon = "💰")
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(incomes) { income ->
                FinanceItemCard(
                    title = income.title,
                    amount = income.amount,
                    date = income.date.format(formatter),
                    categoryLabel = when (income.category) {
                        IncomeCategory.SALES -> "مبيعات"
                        IncomeCategory.ADVANCE -> "عربون"
                        IncomeCategory.BONUS -> "مكافأة"
                        IncomeCategory.OTHER -> "أخرى"
                    },
                    isIncome = true,
                    onDelete = { onDelete(income) }
                )
            }
            item { Spacer(modifier = Modifier.height(80.dp)) }
        }
    }
}

@Composable
fun ExpenseList(expenses: List<Expense>, onDelete: (Expense) -> Unit) {
    val formatter = DateTimeFormatter.ofPattern("dd/MM")
    if (expenses.isEmpty()) {
        EmptyFinanceState(message = "لا يوجد مصاريف هذا الشهر", icon = "🎉")
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(expenses) { expense ->
                FinanceItemCard(
                    title = expense.title,
                    amount = expense.amount,
                    date = expense.date.format(formatter),
                    categoryLabel = when (expense.category) {
                        ExpenseCategory.RAW_MATERIALS -> "خامات"
                        ExpenseCategory.TOOLS -> "أدوات"
                        ExpenseCategory.MAINTENANCE -> "صيانة"
                        ExpenseCategory.SALARY -> "رواتب"
                        ExpenseCategory.UTILITIES -> "مرافق"
                        ExpenseCategory.OTHER -> "أخرى"
                    },
                    isIncome = false,
                    onDelete = { onDelete(expense) }
                )
            }
            item { Spacer(modifier = Modifier.height(80.dp)) }
        }
    }
}

@Composable
fun FinanceItemCard(
    title: String,
    amount: Double,
    date: String,
    categoryLabel: String,
    isIncome: Boolean,
    onDelete: () -> Unit
) {
    var showDelete by remember { mutableStateOf(false) }
    val amountColor = if (isIncome)
        MaterialTheme.colorScheme.secondary
    else
        MaterialTheme.colorScheme.error

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = amountColor.copy(alpha = 0.1f),
                    modifier = Modifier.size(44.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = if (isIncome) "+" else "-",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = amountColor
                        )
                    }
                }
                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = categoryLabel,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.outline
                        )
                        Text(
                            text = "• $date",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "${String.format("%.0f", amount)} ج",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = amountColor
                )
                IconButton(
                    onClick = { showDelete = true },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "حذف",
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }

    if (showDelete) {
        AlertDialog(
            onDismissRequest = { showDelete = false },
            title = { Text("حذف") },
            text = { Text("هل أنت متأكد من حذف \"$title\"؟") },
            confirmButton = {
                Button(
                    onClick = { onDelete(); showDelete = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) { Text("حذف") }
            },
            dismissButton = {
                TextButton(onClick = { showDelete = false }) { Text("إلغاء") }
            }
        )
    }
}

@Composable
fun EmptyFinanceState(message: String, icon: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(text = icon, fontSize = 60.sp)
            Text(
                text = message,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}

@Composable
fun AddExpenseDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, Double, ExpenseCategory, String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var category by remember { mutableStateOf(ExpenseCategory.OTHER) }
    var notes by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    val categoryLabels = mapOf(
        ExpenseCategory.RAW_MATERIALS to "خامات",
        ExpenseCategory.TOOLS to "أدوات",
        ExpenseCategory.MAINTENANCE to "صيانة",
        ExpenseCategory.SALARY to "رواتب",
        ExpenseCategory.UTILITIES to "مرافق",
        ExpenseCategory.OTHER to "أخرى"
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("إضافة مصروف", fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("العنوان *") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text("المبلغ (ج) *") },
                    keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                        keyboardType = androidx.compose.ui.text.input.KeyboardType.Decimal
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = it }
                ) {
                    OutlinedTextField(
                        value = categoryLabels[category] ?: "",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("التصنيف") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        },
                        modifier = Modifier.fillMaxWidth().menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        categoryLabels.forEach { (cat, label) ->
                            DropdownMenuItem(
                                text = { Text(label) },
                                onClick = { category = cat; expanded = false }
                            )
                        }
                    }
                }
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("ملاحظات") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(title, amount.toDoubleOrNull() ?: 0.0, category, notes)
                },
                enabled = title.isNotBlank() && (amount.toDoubleOrNull() ?: 0.0) > 0,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) { Text("إضافة") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("إلغاء") }
        }
    )
}

@Composable
fun AddIncomeDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, Double, IncomeCategory, String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var category by remember { mutableStateOf(IncomeCategory.SALES) }
    var notes by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    val categoryLabels = mapOf(
        IncomeCategory.SALES to "مبيعات",
        IncomeCategory.ADVANCE to "عربون",
        IncomeCategory.BONUS to "مكافأة",
        IncomeCategory.OTHER to "أخرى"
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("إضافة دخل", fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("العنوان *") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text("المبلغ (ج) *") },
                    keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                        keyboardType = androidx.compose.ui.text.input.KeyboardType.Decimal
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = it }
                ) {
                    OutlinedTextField(
                        value = categoryLabels[category] ?: "",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("التصنيف") }
     trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        },
                        modifier = Modifier.fillMaxWidth().menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        categoryLabels.forEach { (cat, label) ->
                            DropdownMenuItem(
                                text = { Text(label) },
                                onClick = { category = cat; expanded = false }
                            )
                        }
                    }
                }
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("ملاحظات") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(title, amount.toDoubleOrNull() ?: 0.0, category, notes)
                },
                enabled = title.isNotBlank() && (amount.toDoubleOrNull() ?: 0.0) > 0,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) { Text("إضافة") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("إلغاء") }
        }
    )
}
