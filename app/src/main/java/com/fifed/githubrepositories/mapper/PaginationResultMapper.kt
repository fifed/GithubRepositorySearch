package com.fifed.githubrepositories.mapper

import com.fifed.githubrepositories.pagination.PaginationResult

interface PaginationResultMapper {
    fun <T> map(data: T, currentPage: Int, endCalculation: () -> Boolean): PaginationResult<T>
}