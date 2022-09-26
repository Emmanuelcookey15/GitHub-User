package com.emiko.githubuser.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.emiko.githubuser.R
import com.emiko.githubuser.utils.NetworkState
import com.emiko.githubuser.ui.adapter.UserListPagedAdapter
import com.emiko.githubuser.ui.viewmodels.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_users.*


@AndroidEntryPoint
class UsersFragment : Fragment() {

    val TAG = "UsersFragment"

    lateinit var userListPagedAdapter: UserListPagedAdapter

    private val viewModel: UserViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_users, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        setupRecyclerView()
        setUpObservers()
        userListPagedAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("userItem", it)
            }
            findNavController().navigate(
                R.id.action_usersFragment_to_moreDetailsFragment,
                bundle
            )
        }
    }

    private fun setUpObservers() {
        viewModel.usersPagedList("lagos").observe(viewLifecycleOwner, Observer {
            userListPagedAdapter.submitList(it)
        })

        viewModel.state.observe(viewLifecycleOwner, Observer {
            when(it){
                NetworkState.LOADED -> {
                    hideProgressBar()
                    Toast.makeText(requireContext(), NetworkState.LOADED.msg, Toast.LENGTH_SHORT).show()
                }
                NetworkState.ERROR -> {
                    hideProgressBar()
                    Toast.makeText(requireContext(), NetworkState.ERROR.msg, Toast.LENGTH_SHORT).show()
                }
                NetworkState.ENDOFLIST -> {
                    hideProgressBar()
                    Toast.makeText(requireContext(), NetworkState.ENDOFLIST.msg, Toast.LENGTH_SHORT).show()
                }
                NetworkState.LOADING -> {
                    showProgressBar()
                    Toast.makeText(requireContext(), NetworkState.LOADING.msg, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }


    private fun hideProgressBar() {
        paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        paginationProgressBar.visibility = View.VISIBLE
    }


    private fun setupRecyclerView() {

        userListPagedAdapter = UserListPagedAdapter()
        rvUsers.apply {
            adapter = userListPagedAdapter
            layoutManager = LinearLayoutManager(activity)
        }

    }

}