package com.jackson.basestructure.view.main

import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.jackson.basestructure.R
import com.jackson.basestructure.base.adapter.BaseRecyclerAdapter
import com.jackson.basestructure.base.observer
import com.jackson.basestructure.base.toArrayList
import com.jackson.basestructure.base.toast
import com.jackson.basestructure.base.view.BaseViewModelActivity
import com.jackson.basestructure.base.viewModel.BaseViewModel
import com.jackson.basestructure.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseViewModelActivity<ActivityMainBinding, BaseViewModel>(R.layout.activity_main),
    BaseRecyclerAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    override val vm: MainViewModel by viewModel()

    override fun onCreate() {
        initView()
        loadData()
    }

    private fun initView() {
        // toolbar, title
        setActionBarTitle(R.string.app_name)

        // databinding, viewModel 주입
        with(binding) {
            // data variable 주입
            this.vm = this@MainActivity.vm
            this.layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
            this.adapter = TodoListAdapter(this@MainActivity)

            // viewModel todos 상태 구독
            observer(this@MainActivity.vm.todos) { todos ->
                // [ todos의 값이 변경될 경우 ]
                this.adapter?.updateItem(todos.toArrayList())
                // progress hide
                refreshLayout.isRefreshing = false
            }

            // init refresh listener
            refreshLayout.setOnRefreshListener(this@MainActivity)
        }

        // viewModel titleCount 상태 구독
        observer(vm.titleCount) { titleCnt ->
            val cntStr = if (titleCnt.count > 0) " ( ${titleCnt.count} )" else ""
            setActionBarTitle("${titleCnt.title}$cntStr")
        }
    }

    private fun loadData() {
        vm.loadTitleCount(R.string.app_name)
        vm.requestTodoList()
    }

    // 새로고침
    override fun onRefresh() {
        binding.adapter?.clearItem()
        vm.requestTodoList()
    }

    override fun onItemClick(view: View, position: Int) {
        vm.todos.value?.get(position)?.title?.let { title ->
            toast(title)
        } ?: toast(R.string.not_found_msg)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.plus -> vm.plusTitleCount()
            R.id.minus -> vm.minusTitleCount()
            else -> super.onOptionsItemSelected(item)
        }
    }
}