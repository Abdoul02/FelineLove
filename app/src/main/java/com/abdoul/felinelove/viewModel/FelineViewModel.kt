package com.abdoul.felinelove.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.abdoul.felinelove.data.FelineRepository
import com.abdoul.felinelove.model.LocalFeline
import com.abdoul.felinelove.other.FelineDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FelineViewModel @Inject constructor(
    private val repository: FelineRepository,
    private val felineDataSource: FelineDataSource
) : ViewModel() {

    private val _dataState = MutableStateFlow<ViewAction>(ViewAction.Empty)
    val dataState: StateFlow<ViewAction> = _dataState

    val felines = Pager(PagingConfig(pageSize = 10)) {
        felineDataSource
    }.flow.cachedIn(viewModelScope)

    fun getAllFeline() {
        _dataState.value = ViewAction.Loading
        viewModelScope.launch {
            repository.getAllFeline()
                .collect { localFeline ->
                    _dataState.value = ViewAction.FelineInfo(localFeline)
                }
        }
    }

    fun insertFeline(localFeline: LocalFeline) {
        repository.insertFelineInfo(localFeline)
    }

    fun deleteFeline(localFeline: LocalFeline) {
        repository.deleteFeline(localFeline)
    }

    sealed class ViewAction {
        object Loading : ViewAction()
        data class FelineInfo(val localFelines: List<LocalFeline>) : ViewAction()
        object Empty : ViewAction()
    }
}
