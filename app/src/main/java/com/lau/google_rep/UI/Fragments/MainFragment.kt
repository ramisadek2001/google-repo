package com.lau.google_rep.UI.Fragments

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import com.lau.google_rep.R
import com.lau.google_rep.ViewModel.MainAdapter
import com.lau.google_rep.ViewModel.MainViewModel
import com.lau.google_rep.ViewModel.ViewModelFactory
import com.lau.google_rep.data.RetrofitService
import com.lau.google_rep.data.repo
import com.lau.google_rep.databinding.FragmentMainBinding
import com.lau.google_rep.repository.PostDataSource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: MainViewModel
    lateinit var repos : List<repo>

    var flag = false

    lateinit var mainAdapter: MainAdapter
    private lateinit var dataFromViewModel: ArrayList<PagingData<repo>>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentMainBinding = FragmentMainBinding.inflate(inflater, container, false)

        this.binding = FragmentMainBinding.inflate(layoutInflater)

        dataFromViewModel = arrayListOf()


        setupViewModel()
        setupList()
        setupView()


        return binding.root;
    }
    private fun setupView() {
        lifecycleScope.launchWhenCreated {
            viewModel.listData.collect {
                mainAdapter.submitData(it)
            }
        }

        }


    private fun setupList() {
        mainAdapter = MainAdapter()

        val gridLayoutManager: GridLayoutManager = GridLayoutManager(context, 2)

        binding.recyclerview.apply {
            layoutManager = gridLayoutManager
            adapter = mainAdapter
        }

//        mainAdapter.addLoadStateListener { loadState ->
//
//            if (loadState.refresh is LoadState.Loading && !flag) {
//                binding.progressbar.visibility = View.VISIBLE
//                flag = true
//            } else {
//                binding.progressbar.visibility = View.GONE
//            }
//        }
    }

    private fun setupViewModel() {

        viewModel =
            ViewModelProvider(
                this,
                ViewModelFactory(RetrofitService.getApiService())
            )[MainViewModel::class.java]
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MainFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}