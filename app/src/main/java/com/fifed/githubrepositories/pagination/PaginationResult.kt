package com.fifed.githubrepositories.pagination

data class PaginationResult<out T>(val data: T, val currentPage: Int, val lastPage: Boolean)