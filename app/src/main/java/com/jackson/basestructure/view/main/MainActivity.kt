package com.jackson.basestructure.view.main

import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jackson.basestructure.R
import com.jackson.basestructure.base.adapter.BaseRecyclerAdapter
import com.jackson.basestructure.base.toArrayList
import com.jackson.basestructure.base.toast
import com.jackson.basestructure.base.view.BaseViewModelActivity
import com.jackson.basestructure.base.viewModel.BaseViewModel
import com.jackson.basestructure.databinding.ActivityMainBinding

class MainActivity : BaseViewModelActivity<ActivityMainBinding, BaseViewModel>(R.layout.activity_main), BaseRecyclerAdapter.OnItemClickListener {

    override val vm: MainViewModel = MainViewModel()
    lateinit var adapter: TodoListAdapter

    override fun onCreate() {
        initView()
        loadData()
    }

    private fun initView() {
        // toolbar, title
        setActionBarTitle("TODOS")
        // databinding, viewModel 주입
        binding.vm = vm

        with(binding.rvTodoList) {
            layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
            adapter = TodoListAdapter().apply {
                setOnItemClickListener(this@MainActivity)
            }.also { this@MainActivity.adapter = it }
        }
    }

    private fun loadData() {
        vm.todos.observe(this, Observer { todos ->
            if (this::adapter.isInitialized) {
                adapter.updateItem(todos.toArrayList())
            }
        })
        vm.requestTodoList()
    }

    override fun onItemClick(view: View, position: Int) {
        vm.todos.value?.get(position)?.title?.let { title ->
            toast(title)
        } ?: toast("item not found!")
    }
}