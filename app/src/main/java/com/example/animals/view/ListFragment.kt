package com.example.animals.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager

import com.example.animals.R
import com.example.animals.model.Animal
import com.example.animals.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.fragment_list.*


class ListFragment : Fragment() {

    private val animalListDataObserver = Observer<List<Animal>> { list ->
        list?.let {
            animalList.visibility = View.VISIBLE
            listAdapter.updateAnimalList(it)
        }

    }
    private val errorLiveDataObserver = Observer<Boolean> { iserror ->
        loadError.visibility = if (iserror) View.VISIBLE else View.GONE

    }
    private val loadingLiveDataObserver = Observer<Boolean> { isloading ->
        loadingView.visibility = if (isloading) View.VISIBLE else View.GONE
        if (isloading) {
            animalList.visibility = View.GONE
            loadError.visibility = View.GONE
        }

    }

    private lateinit var viewModel: ListViewModel
    private var listAdapter = AnimalListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        viewModel.animals.observe(this, animalListDataObserver)
        viewModel.loading.observe(this, loadingLiveDataObserver)
        viewModel.loadError.observe(this, errorLiveDataObserver)

        viewModel.refresh()

        animalList.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = listAdapter
        }
        refreshlayout.setOnRefreshListener {
            animalList.visibility = View.GONE
            loadError.visibility = View.GONE
            loadingView.visibility = View.VISIBLE
            viewModel.hardRefresh()
            refreshlayout.isRefreshing = false
        }

    }

}
