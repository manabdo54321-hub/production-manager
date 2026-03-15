package com.abdo.feature.production

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdo.core.domain.model.Product
import com.abdo.core.domain.model.ProductionEntry
import com.abdo.core.domain.usecase.AddProductUseCase
import com.abdo.core.domain.usecase.AddProductionEntryUseCase
import com.abdo.core.domain.usecase.GetDailyProductionUseCase
import com.abdo.core.domain.usecase.GetProductionHistoryUseCase
import com.abdo.core.domain.usecase.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ProductionViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val addProductUseCase: AddProductUseCase,
    private val addProductionEntryUseCase: AddProductionEntryUseCase,
    private val getDailyProductionUseCase: GetDailyProductionUseCase,
    private val getProductionHistoryUseCase: GetProductionHistoryUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductionUiState())
    val uiState: StateFlow<ProductionUiState> = _uiState.asStateFlow()

    init {
        loadProducts()
        loadDailyProduction(LocalDate.now())
        loadHistory()
    }

    fun onEvent(event: ProductionEvent) {
        when (event) {
            is ProductionEvent.AddEntry -> addEntry(event)
            is ProductionEvent.AddProduct -> addProduct(event)
            is ProductionEvent.DeleteEntry -> deleteEntry(event.entry)
            is ProductionEvent.SelectDate -> {
                _uiState.update { it.copy(selectedDate = event.date) }
                loadDailyProduction(event.date)
            }
            is ProductionEvent.ClearMessages -> {
                _uiState.update { it.copy(errorMessage = null, successMessage = null) }
            }
        }
    }

    private fun loadProducts() {
        viewModelScope.launch {
            getProductsUseCase().collect { products ->
                _uiState.update { it.copy(products = products) }
            }
        }
    }

    private fun loadDailyProduction(date: LocalDate) {
        viewModelScope.launch {
            getDailyProductionUseCase(date).collect { daily ->
                _uiState.update { it.copy(dailyProduction = daily) }
            }
        }
    }

    private fun loadHistory() {
        viewModelScope.launch {
            getProductionHistoryUseCase().collect { history ->
                _uiState.update { it.copy(history = history) }
            }
        }
    }

    private fun addEntry(event: ProductionEvent.AddEntry) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val entry = ProductionEntry(
                productId = event.productId,
                quantity = event.quantity,
                notes = event.notes
            )
            addProductionEntryUseCase(entry)
                .onSuccess {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            successMessage = "تم تسجيل الإنتاج بنجاح ✓"
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.message
                        )
                    }
                }
        }
    }

    private fun addProduct(event: ProductionEvent.AddProduct) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val product = Product(
                name = event.name,
                targetPerDay = event.targetPerDay,
                pricePerUnit = event.pricePerUnit
            )
            addProductUseCase(product)
                .onSuccess {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            successMessage = "تم إضافة المنتج بنجاح ✓"
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.message
                        )
                    }
                }
        }
    }

    private fun deleteEntry(entry: ProductionEntry) {
        viewModelScope.launch {
            // TODO: إضافة DeleteEntryUseCase في الخطوة الجاية
        }
    }
}
