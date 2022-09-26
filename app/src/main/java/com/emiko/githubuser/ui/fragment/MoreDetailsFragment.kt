package com.emiko.githubuser.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.emiko.githubuser.R
import com.emiko.githubuser.data.models.UsersItem
import com.emiko.githubuser.ui.viewmodels.DetailsViewModel
import com.emiko.githubuser.ui.viewmodels.UserViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_more_details.*
import kotlinx.android.synthetic.main.item_users.view.*

@AndroidEntryPoint
class MoreDetailsFragment : Fragment() {

    val TAG = "MoreDetailsFragment"

    private val viewModel: DetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_more_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val user = arguments?.getSerializable("userItem") as UsersItem
        user.html_url?.let {
            webView.apply {
                webViewClient = WebViewClient()
                loadUrl(it)
            }
        }

        floatingActionButton.setOnClickListener {
            val favUserItem = user
            favUserItem.favourite = true
            viewModel.addToFavourite(favUserItem)
            Toast.makeText(requireContext(), "User Successfully Added as Favourite", Snackbar.LENGTH_SHORT)

        }

    }


}