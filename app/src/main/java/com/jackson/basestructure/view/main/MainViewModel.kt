package com.jackson.basestructure.view.main

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jackson.basestructure.base.isEmptyGuideVisible
import com.jackson.basestructure.base.isVisible
import com.jackson.basestructure.base.viewModel.BaseViewModel
import com.jackson.basestructure.repository.TodoRepository
import com.jackson.basestructure.repository.model.Todo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val todoRepository: TodoRepository) : BaseViewModel() {

    private val _todos: MutableLiveData<List<Todo>> = MutableLiveData()
    val todos: LiveData<List<Todo>> = _todos

    // recyclerView, visible
    private val _visible: MutableLiveData<Int> = MutableLiveData(View.GONE)
    val visible: LiveData<Int> = _visible

    // empty guide layout, visible
    private val _emptyVisible: MutableLiveData<Int> = MutableLiveData(View.VISIBLE)
    val emptyVisible: LiveData<Int> = _emptyVisible

    fun requestTodoList() = launch(Dispatchers.IO) {
        todoRepository.getTodoList().let { todos ->

            withContext(Dispatchers.Main) {
                _todos.value = todos
                _visible.value = todos.isVisible()
                _emptyVisible.value = todos.isEmptyGuideVisible()
            }
        }
    }
}