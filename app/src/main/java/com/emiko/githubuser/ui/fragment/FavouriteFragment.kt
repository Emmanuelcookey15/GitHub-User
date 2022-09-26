package com.emiko.githubuser.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emiko.githubuser.R
import com.emiko.githubuser.ui.adapter.FavouriteAdapter
import com.emiko.githubuser.utils.Resource
import com.emiko.githubuser.ui.viewmodels.FavouriteViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_favourite.*
import kotlinx.android.synthetic.main.fragment_users.*

@AndroidEntryPoint
class FavouriteFragment : Fragment() {

    val TAG = "FavouriteFragment"
    lateinit var favouriteAdapter: FavouriteAdapter
    private val viewModel: FavouriteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setUpObservers()
        recyclerviewTouchListener(view)
        favouriteAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("userItem", it)
            }
            findNavController().navigate(
                R.id.action_favouriteFragment_to_moreDetailsFragment,
                bundle
            )
        }
    }

    private fun setUpObservers() {

        viewModel.listOfFavouriteUsers.observe(viewLifecycleOwner, Observer { resource ->
            when (resource) {
                is Resource.Success -> {
                    hideProgressBar()
                    Log.d("SOME", "${resource.data?.size}")
                    favouriteAdapter.differ.submitList(resource.data?.toList())
                }
                is Resource.Error -> {
                    hideProgressBar()
                    resource.message?.let { message ->
                        Log.e(TAG, "An error occured: $message")
                        Toast.makeText(requireContext(), message,
                            Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })

    }

    private fun hideProgressBar() {
        paginationProgressBarFav.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        paginationProgressBar.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        favouriteAdapter = FavouriteAdapter()
        rvUsersFav.apply {
            adapter = favouriteAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun recyclerviewTouchListener(view: View) {
        val itemTouchHelperCallBack = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val user = favouriteAdapter.differ.currentList[position]
                user.favourite = false
                viewModel.addToFavourite(user)
                Snackbar.make(view, "User Successfully Removed as Favourite", Snackbar.LENGTH_LONG)
                    .apply {
                        setAction("Undo") {
                            //action saved user
                            user.favourite = true
                            viewModel.addToFavourite(user)
                        }
                        show()
                    }
            }
        }

        ItemTouchHelper(itemTouchHelperCallBack).apply {
            attachToRecyclerView(rvUsersFav)
        }
    }


}