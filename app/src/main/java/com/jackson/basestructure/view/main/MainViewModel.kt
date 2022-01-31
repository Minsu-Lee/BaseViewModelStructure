package com.jackson.basestructure.view.main

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jackson.basestructure.base.isEmptyGuideVisible
import com.jackson.basestructure.base.isVisible
import com.jackson.basestructure.base.viewModel.BaseViewModel
import com.jackson.basestructure.repository.TitleCountRepository
import com.jackson.basestructure.repository.TodoRepository
import com.jackson.basestructure.repository.model.TitleCount
import com.jackson.basestructure.repository.model.Todo
import kotlinx.coroutines.*

class MainViewModel(private val todoRepository: TodoRepository, private val titleCountRepository: TitleCountRepository) : BaseViewModel() {

    private val _titleCount: MutableLiveData<TitleCount> = MutableLiveData()
    val titleCount: LiveData<TitleCount> = _titleCount

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

    fun laodTitleCount(defaultTitle: String) = launch(Dispatchers.IO) {
        titleCountRepository.getTitleCount().let { titleCount ->
            if (titleCount == null) {
                val titleCount = titleCountRepository.setTitleCount(defaultTitle, 0)
                _titleCount.postValue(titleCount)
            } else {
                _titleCount.postValue(titleCount)
            }
        }
    }

    fun plusTitleCount(): Int {
        titleCount.value.let {
            val title = it?.title ?: ""
            val count = it?.count?.plus(1) ?: 0
            launch(Dispatchers.IO) {
                val titleCount = titleCountRepository.setTitleCount(title, count)
                _titleCount.postValue(titleCount)
            }
            return count
        }
    }

    fun minusTitleCount(): Int {
        titleCount.value.let {
            val title = it?.title ?: ""
            val count = it?.count?.minus(1) ?: 0

            if (count >= 0) {
                launch(Dispatchers.IO) {
                    _titleCount.postValue(titleCountRepository.setTitleCount(title, count))
                }
            }

            return count
        }
    }
}