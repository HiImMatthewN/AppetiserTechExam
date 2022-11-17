package com.nantesmatthew.movie.domain.use_case

import com.nantesmatthew.core.util.Status
import com.nantesmatthew.movie.domain.model.Movie
import com.nantesmatthew.movie.domain.repository.MovieRepositoryTestImpl
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class SortByGenreUseCaseTest {
    private val repository = MovieRepositoryTestImpl()
    private val useCase = SortByGenreUseCase()
    @Before
    fun setup(){
        repository.dummyMovieDataSource.add(Movie(123,"Avengers","Tom Holland","Action,Superhero","dummyArtwork",150.0,10000,"dummyPreviewUrl","Superhero Movie","Superhero movie for everyone",false))
        repository.dummyMovieDataSource.add(Movie(456,"Batman","Christian Bale","Action,Superhero","dummyArtwork",300.0,15000,"dummyPreviewUrl","Superhero Movie","Superhero movie for adults",false))
        repository.dummyMovieDataSource.add(Movie(789,"Back to the future","Michael J. Fox","Sci-fi","dummyArtwork",200.0,2000,"dummyPreviewUrl","Time Traveling","Need to save doc",false))
    }



    @Test
     fun Should_Contain_Same_Genre_When_Sorting() = runBlocking{
       val sortedMovies = useCase(repository.dummyMovieDataSource)
        var containsTwoOrMoreGenreInOneGenre = false
        sortedMovies.forEach {
            moviesByGenre ->
            containsTwoOrMoreGenreInOneGenre = moviesByGenre.movies.distinctBy {movie-> movie.primaryGenre }.size > 1
        }
        assert(!containsTwoOrMoreGenreInOneGenre)

    }




    @After
    fun tearDown(){
        repository.dummyFaveMovieDataSource.clear()
    }

}