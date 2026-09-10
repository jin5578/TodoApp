package com.example.manage_categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.DeleteCategoryUseCase
import com.example.domain.GetCategoryByIdUseCase
import com.example.domain.GetManageCategoriesDataUseCase
import com.example.domain.InsertCategoryUseCase
import com.example.domain.UpdateCategoryUseCase
import com.example.manage_categories.model.ManageCategoriesUiState
import com.example.manage_categories.model.ManageCategoryUiModel
import com.example.model.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageCategoriesViewModel @Inject constructor(
    private val getManageCategoriesDataUseCase: GetManageCategoriesDataUseCase,
    private val insertCategoryUseCase: InsertCategoryUseCase,
    private val getCategoryByIdUseCase: GetCategoryByIdUseCase,
    private val deleteCategoryUseCase: DeleteCategoryUseCase,
    private val updateCategoryUseCase: UpdateCategoryUseCase,
) : ViewModel() {
    private val _errorFlow: MutableSharedFlow<Throwable> = MutableSharedFlow()
    val errorFlow = _errorFlow.asSharedFlow()

    private val _uiState: MutableStateFlow<ManageCategoriesUiState> =
        MutableStateFlow(value = ManageCategoriesUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        fetchManageCategoriesUiState()
    }

    private fun fetchManageCategoriesUiState() =
        viewModelScope.launch {
            getManageCategoriesDataUseCase().map { data ->
                val taskCountByCategoryId =
                    data.tasks.groupingBy { task -> task.categoryId }.eachCount()
                ManageCategoriesUiState.Screen(
                    categoryUiModels = data.categories.map { category ->
                        ManageCategoryUiModel(
                            category = category,
                            taskCount = taskCountByCategoryId[category.id] ?: 0
                        )
                    }.toPersistentList()
                )
            }.catch { throwable ->
                _errorFlow.emit(value = throwable)
            }.collect {
                _uiState.value = it
            }
        }

    fun insertCategory(title: String, colorValue: Long) =
        viewModelScope.launch {
            val category = Category(
                title = title,
                colorValue = colorValue
            )
            insertCategoryUseCase(category = category)
        }

    fun deleteCategory(id: Long) =
        viewModelScope.launch {
            val category = getCategoryByIdUseCase(id = id)
            deleteCategoryUseCase(category = category)
        }

    fun updateCategory(id: Long, title: String, colorValue: Long) =
        viewModelScope.launch {
            val category = Category(
                id = id,
                title = title,
                colorValue = colorValue
            )
            updateCategoryUseCase(category = category)
        }
}