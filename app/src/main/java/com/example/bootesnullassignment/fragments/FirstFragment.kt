package com.example.bootesnullassignment.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.bootesnullassignment.Constants
import com.example.bootesnullassignment.NewsAdapter
import com.example.bootesnullassignment.R
import com.example.bootesnullassignment.Repository
import com.example.bootesnullassignment.databinding.FragmentFirstBinding
import com.example.bootesnullassignment.network.ApiClient
import com.example.bootesnullassignment.network.ApiService
import com.example.bootesnullassignment.viewmodels.MainViewModel
import com.example.bootesnullassignment.viewmodels.MainViewModelFactory

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    lateinit var loader: LinearLayout

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        val apiClient = ApiClient.retrofit.create(ApiService::class.java)
        var repository = Repository(apiClient)
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(activity!!, viewModelFactory)[MainViewModel::class.java]
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.visibility = View.GONE


        val adapter = NewsAdapter()
        binding.recyclerView.adapter = adapter
        viewModel.liveNews.observe(this, Observer {
            adapter.setNewsList(it.results)
            binding.recyclerView.visibility = View.VISIBLE
        })

        viewModel.loading.observe(this, Observer {
            if (it) {
                if (internetCheck(context!!)) {
                    binding.llLoader.llLoader.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.VISIBLE
//                    viewModel.getNewsList()
                } else
                    setEmptyState(Constants.NO_INTERNET)
            } else {
                binding.llLoader.llLoader.visibility = View.GONE
            }
        })

//        viewModel.fabClick.observe(this, Observer {
//
//        })

    }

    fun internetCheck(c: Context): Boolean {
        val connectionManager =
            c.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectionManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setEmptyState(emptyState: String) {
        binding.llEmptyState.llEmptyState.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.GONE
        binding.llLoader.llLoader.visibility = View.GONE
        if (emptyState == Constants.NO_INTERNET) {
            binding.llEmptyState.ivEsImage.setImageResource(R.drawable.es_no_internet)
            binding.llEmptyState.tvEsTitle.text = getString(R.string.no_internet_connection)
            binding.llEmptyState.tvEsDescription.text = getString(R.string.no_internet_desc)
        } else if (emptyState == Constants.NO_CONTENT) {
            binding.llEmptyState.ivEsImage.setImageResource(R.drawable.es_no_data)
            binding.llEmptyState.tvEsTitle.text = getString(R.string.no_content)
            binding.llEmptyState.tvEsDescription.text = getString(R.string.no_content_desc)
        } else if (emptyState == Constants.NO_SERVER) {
            binding.llEmptyState.ivEsImage.setImageResource(R.drawable.es_no_server)
            binding.llEmptyState.tvEsTitle.text = getString(R.string.no_server)
            binding.llEmptyState.tvEsDescription.text = getString(R.string.no_server_desc)
        }
    }
}

