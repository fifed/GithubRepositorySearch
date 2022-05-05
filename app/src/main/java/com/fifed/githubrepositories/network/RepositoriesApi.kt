package com.fifed.githubrepositories.network

import com.fifed.githubrepositories.model.api_model.responses.RepositoryApiModel
import com.fifed.githubrepositories.model.api_model.responses.SearchRepositoriesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RepositoriesApi {
    @GET("search/repositories")
    suspend fun searchRepositories(
        @Query("q") searchQuery: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): SearchRepositoriesResponse

    @GET("repos/{owner}/{name}")
    suspend fun getRepository(
        @Path("owner") ownerName: String,
        @Path("name") repositoryName: String
    ): RepositoryApiModel
}