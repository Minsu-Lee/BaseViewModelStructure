package com.jackson.basestructure.view.main

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jackson.basestructure.base.isEmptyGuideVisible
import com.jackson.basestructure.base.isVisible
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
                _isEmptyVisible.value = todos.isEmptyGuideVisible()
            }
        }
    }

    // [ DB ] title count 불러오기
    fun loadTitleCount(defaultTitle: String) = launch(Dispatchers.IO) {
        titleCountRepository.getTitleCount().let { titleCount ->
            if (titleCount == null) {
                val titleCount = titleCountRepository.setTitleCount(defaultTitle, 0)
                _titleCount.postValue(titleCount)
            } else {
                _titleCount.postValue(titleCount)
            }
        }
    }

    // [ DB ] title count 증가
    fun plusTitleCount(increaseCnt: Int = 1): Int {
        titleCount.value.let {
            val title = it?.title ?: ""
            val count = it?.count?.plus(increaseCnt) ?: 0
            launch(Dispatchers.IO) {
                val titleCount = titleCountRepository.setTitleCount(title, count)
                _titleCount.postValue(titleCount)
            }
            return count
        }
    }

    // [ DB ] title count 감소
    fun minusTitleCount(decreaseCnt: Int = 1): Int {
        titleCount.value.let {
            val title = it?.title ?: ""
            val count = it?.count?.minus(decreaseCnt) ?: 0

            if (count >= 0) {
                launch(Dispatchers.IO) {
                    _titleCount.postValue(titleCountRepository.setTitleCount(title, count))
                }
            }

            return count
        }
    }
}