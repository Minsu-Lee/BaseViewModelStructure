package com.jackson.basestructure.view.main

import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jackson.basestructure.R
import com.jackson.basestructure.base.adapter.BaseRecyclerAdapter
import com.jackson.basestructure.base.observer
import com.jackson.basestructure.base.toArrayList
import com.jackson.basestructure.base.toast
import com.jackson.basestructure.base.view.BaseViewModelActivity
import com.jackson.basestructure.base.viewModel.BaseViewModel
import com.jackson.basestructure.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseViewModelActivity<ActivityMainBinding, BaseViewModel>(R.layout.activity_main), BaseRecyclerAdapter.OnItemClickListener {

    override val vm: MainViewModel by viewModel()
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

        observer(vm.titleCount) { titleCnt ->
            val title = titleCnt.title
            val cntStr = if (titleCnt.count > 0) " - ${titleCnt.count}" else ""
            setActionBarTitle("$title$cntStr")
        }

        observer(vm.todos) { todos ->
            if (this::adapter.isInitialized) {
                adapter.updateItem(todos.toArrayList())
            }
        }
    }

    private fun loadData() {
        vm.laodTitleCount("TODOS")
        vm.requestTodoList()
    }

    override fun onItemClick(view: View, position: Int) {
        vm.todos.value?.get(position)?.title?.let { title ->
            toast(title)
        } ?: toast("item not found!")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.plus -> vm.plusTitleCount() > 0
            R.id.minus -> vm.minusTitleCount() > 0
            else -> super.onOptionsItemSelected(item)
        }
    }
}