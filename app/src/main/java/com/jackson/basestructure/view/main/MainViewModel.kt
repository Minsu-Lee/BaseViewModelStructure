package com.jackson.basestructure.view.main

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jackson.basestructure.base.isEmptyGuideVisible
import com.jackson.basestructure.base.isVisible
import com.jackson.basestructure.base.viewModel.BaseViewModel
import com.jackson.basestructure.repository.dataSource.dto.TodoResponse
import com.jackson.basestructure.network.API
import com.jackson.basestructure.network.TodoService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : BaseViewModel() {

    private val _todos: MutableLiveData<List<TodoResponse.Todo>> = MutableLiveData()
    val todos: LiveData<List<TodoResponse.Todo>> = _todos

    // recyclerView, visible
    private val _visible: MutableLiveData<Int> = MutableLiveData(View.GONE)
    val visible: LiveData<Int> = _visible

    // empty guide layout, visible
    private val _emptyVisible: MutableLiveData<Int> = MutableLiveData(View.VISIBLE)
    val emptyVisible: LiveData<Int> = _emptyVisible

    fun requestTodoList() = launch(Dispatchers.IO) {
        API.retrofit(TodoService::class.java)
            .getTodos().let { todos ->

                withContext(Dispatchers.Main) {
                    _todos.value = todos
                    _visible.value = todos.isVisible()
                    _emptyVisible.value = todos.isEmptyGuideVisible()
                }
            }
    }
}