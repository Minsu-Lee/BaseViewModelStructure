package com.jackson.basestructure.view.main

import android.view.LayoutInflater
import android.view.ViewGroup
import com.jackson.basestructure.base.adapter.BaseRecyclerAdapter
import com.jackson.basestructure.databinding.ListitemTodoBinding
import com.jackson.basestructure.repository.vo.Todo

class TodoListAdapter(itemList: ArrayList<Todo> = arrayListOf()): BaseRecyclerAdapter<Todo, TodoViewHolder>(itemList) {

    constructor(clickListener: OnItemClickListener): this(itemList = arrayListOf()) {
        setOnItemClickListener(clickListener)
    }

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