package com.nantesmatthew.movie.data.repository

import com.nantesmatthew.core.util.Resource
import com.nantesmatthew.core.util.networkBoundResource
import com.nantesmatthew.movie.data.data_source.MovieDao
import com.nantesmatthew.movie.data.data_source.MovieService
import com.nantesmatthew.movie.data.data_source.UserFaveDao
import com.nantesmatthew.movie.data.entity.FavoriteMovieInfoEntity
import com.nantesmatthew.movie.data.entity.toDomain
import com.nantesmatthew.movie.data.entity.toLocalEntity
import com.nantesmatthew.movie.domain.model.Movie
import com.nantesmatthew.movie.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * A [MovieRepository] implementation
 * @param api remote data source of movie
 * @param dao local data source of movie
 * @param userFaveDao local datasource of user faved movies
 */
class MovieRepositoryImpl @Inject constructor(
    private val api: MovieService,
    private val dao: MovieDao,
    private val userFaveDao: UserFaveDao
) : MovieRepository {


    override suspend fun getMovies(
        term: String,
        country: String,
        media: String
    ): Flow<Resource<List<Movie>>> {
        return networkBoundResource(
            query = {
                dao.get().map { it.map { it.toDomain() } }
            }, fetch = {
                api.search(term, country, media)
            }, saveFetchResult = { response ->
                val movieNetworkList = response.body()?.results ?: emptyList()
                val movieLocalList = movieNetworkList.map { it.toLocalEntity() }
                dao.insert(movieLocalList)
            },
            shouldFetch = { true }
        )
    }

    override suspend fun getMovie(trackId: Int): Resource<Movie> {
        val favoriteMovie = userFaveDao.get(trackId)
        if (favoriteMovie != null)
            return Resource.success(favoriteMovie.toDomain())


        val movieResult = dao.get(trackId) ?: return Resource.error("Movie not found", null)
        return Resource.success(movieResult.toDomain())
    }

    override fun getFavoriteMovies(): Flow<List<Movie>> = userFaveDao.get().map { it.map { it.toDomain() } }

    override suspend fun addToFavorites(movie: Movie): Resource<Unit> {
        val addResult = userFaveDao.insert(FavoriteMovieInfoEntity(movie.trackId))
        if (addResult == 0L)
            return Resource.error("Movie not added to favorites")
        else
            return Resource.success(Unit)
    }

    override suspend fun removeToFavorites(movie: Movie): Resource<Unit> {
        val deleteResult = userFaveDao.delete(FavoriteMovieInfoEntity(movie.trackId))
        if (deleteResult == 0)
            return Resource.error("Movie not deleted from favorites")
        else
            return Resource.success(Unit)
    }

}