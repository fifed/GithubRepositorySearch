package com.fifed.githubrepositories.mapper.impl

import com.fifed.githubrepositories.mapper.PaginationResultMapper
import com.fifed.githubrepositories.pagination.PaginationResult

class PaginationResultMapperImpl : PaginationResultMapper {
    override fun <T> map(data: T, currentPage: Int, endCalculation: () -> Boolean): PaginationResult<T> {
        return PaginationResult(data, currentPage, endCalculation())
    }
}