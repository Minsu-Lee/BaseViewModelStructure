package com.jackson.basestructure.view.main

import android.view.LayoutInflater
import android.view.ViewGroup
import com.jackson.basestructure.base.adapter.BaseRecyclerAdapter
import com.jackson.basestructure.databinding.ListitemTodoBinding
import com.jackson.basestructure.repository.dataSource.dto.TodoResponse

class TodoListAdapter(itemList: ArrayList<TodoResponse.Todo> = arrayListOf()): BaseRecyclerAdapter<TodoResponse.Todo, TodoViewHolder>(itemList) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val binding = ListitemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoViewHolder(binding)
    }

    override fun onBindView(holder: TodoViewHolder, viewType: Int, position: Int) {
        getItem(position)?.let { item ->
            holder.onBind(item, position)
        }
    }
}