package com.jackson.basestructure.view.main

import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jackson.basestructure.base.string
import com.jackson.basestructure.base.viewModel.BaseViewModel
import com.jackson.basestructure.repository.TitleCountRepository
import com.jackson.basestructure.repository.TodoRepository
import com.jackson.basestructure.repository.vo.TitleCount
import com.jackson.basestructure.repository.vo.Todo
import kotlinx.coroutines.*

class MainViewModel(private val todoRepository: TodoRepository, private val titleCountRepository: TitleCountRepository) : BaseViewModel() {

    private val _titleCount: MutableLiveData<TitleCount> = MutableLiveData()
    val titleCount: LiveData<TitleCount> = _titleCount

    private val _todos: MutableLiveData<List<Todo>> = MutableLiveData()
    val todos: LiveData<List<Todo>> = _todos

    // empty guide layout, visible
    private val _isEmptyVisible: MutableLiveData<Int> = MutableLiveData(View.VISIBLE)
    val isEmptyVisible: LiveData<Int> = _isEmptyVisible

    // [ API ] todos api 호출
    fun requestTodoList() = launch(Dispatchers.IO) {
        todoRepository.getTodoList().let { todos ->
            withContext(Dispatchers.Main) {
                _todos.value = todos
                _isEmptyVisible.value = if (todos.isNotEmpty()) View.GONE else View.VISIBLE
            }
        }
    }

    // [ DB ] title count 불러오기
    fun loadTitleCount(defaultTitle: String) = launch(Dispatchers.IO) {
        titleCountRepository.getTitleCount().let { titleCount ->
            (titleCount ?: titleCountRepository.setTitleCount(defaultTitle, 0)).let(_titleCount::postValue)
        }
    }

    // [ DB ] title count 불러오기
    fun loadTitleCount(@StringRes defaultTitleRes: Int) = loadTitleCount(string(defaultTitleRes))

    // [ DB ] title count 증가
    fun plusTitleCount(increaseCnt: Int = 1): Boolean {
        launch(Dispatchers.IO) {
            titleCount.value.let {
                val title = it?.title ?: ""
                val count = it?.count?.plus(increaseCnt) ?: 0
                titleCountRepository.setTitleCount(title, count).let(_titleCount::postValue)
            }
        }
        return true
    }

    // [ DB ] title count 감소
    fun minusTitleCount(decreaseCnt: Int = 1): Boolean {
        launch(Dispatchers.IO) {
            titleCount.value.let {
                val title = it?.title ?: ""
                val count = it?.count?.minus(decreaseCnt) ?: -1
                if (count >= 0) {
                    titleCountRepository.setTitleCount(title, count).let(_titleCount::postValue)
                }
            }
        }
        return true
    }
}