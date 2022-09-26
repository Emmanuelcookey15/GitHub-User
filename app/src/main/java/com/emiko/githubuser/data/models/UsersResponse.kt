package com.emiko.githubuser.data.models

data class UsersResponse(
    val incomplete_results: Boolean,
    val items: MutableList<UsersItem>,
    val total_count: Int
)