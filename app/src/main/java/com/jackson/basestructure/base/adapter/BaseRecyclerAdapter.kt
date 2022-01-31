package com.jackson.basestructure.base.adapter

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerAdapter<T, H: RecyclerView.ViewHolder>(private var itemList: ArrayList<T> = arrayListOf()): RecyclerView.Adapter<H>() {

    private var onItemClickListener: OnItemClickListener? = null
    private var onItemLongClickListener: OnItemLongClickListener? = null


    abstract fun onBindView(holder: H, viewType: Int, position: Int)

    override fun onBindViewHolder(holder: H, position: Int) {
        val viewType = getItemViewType(position)
        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(holder.itemView, position)
        }
        holder.itemView.setOnLongClickListener {
            onItemLongClickListener?.onItemLongClick(holder.itemView, position)
            false
        }

        onBindView(holder, viewType, position)
    }

    override fun getItemCount() = itemList.size

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    fun setOnItemLongClickListener(onItemLongClickListener: OnItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener
    }

    fun addItem(position: Int, item: T) {
        if (isEmpty()) {
            this.itemList = arrayListOf()
        }
        this.itemList.add(position, item)
        notifyItemInserted(position)
    }

    fun addItem(items: ArrayList<T>) {
        if (isEmpty()) {
            this.itemList = arrayListOf()
        }
        this.itemList.addAll(items)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        if (isEmpty()) {
            this.itemList.removeAt(position)
        }
        notifyItemRemoved(position)
    }

    fun updateItem(itemList: ArrayList<T>) {
        if (isEmpty()) {
            this.itemList = arrayListOf()
        }
        this.itemList.clear()
        this.itemList.addAll(itemList)
        notifyDataSetChanged()
    }

    fun clearItem() {
        if (isEmpty()) {
            this.itemList = arrayListOf()
        } else {
            this.itemList.clear()
        }
        notifyDataSetChanged()
    }

    fun getItem(position: Int): T? = if (position < itemCount) {
        itemList[position]
    } else {
        Log.e("BaseRecyclerAdapter", "getItem($position) is null")
        null
    }

    fun getList(): ArrayList<T> = itemList ?: arrayListOf()

    fun isNotEmpty() = this.itemList?.size ?: 0 > 0
    fun isEmpty() = !isNotEmpty()

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(view: View, position: Int)
    }
}