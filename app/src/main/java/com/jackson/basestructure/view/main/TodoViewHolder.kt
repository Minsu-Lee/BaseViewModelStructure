package com.jackson.basestructure.view.main

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import com.jackson.basestructure.databinding.ListitemTodoBinding
import com.jackson.basestructure.repository.model.Todo

class TodoViewHolder(private val binding: ListitemTodoBinding): RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: Todo, position: Int) {
        with(binding) {
            tvOrderNum.text = "idx: ${position.plus(1)}"
            tvUserId.text = "userId : ${item.userId}"
            tvId.text = "id : ${item.id}"
            tvTitle.text = "title : ${item.title}"

            if (item.completed) {
                tvComplete.setBackgroundColor(Color.parseColor("#00ff00"))
                tvComplete.text = "complete : true"
            } else {
                tvComplete.setBackgroundColor(Color.parseColor("#ff0000"))
                tvComplete.text = "complete : false"
            }
        }
    }
}