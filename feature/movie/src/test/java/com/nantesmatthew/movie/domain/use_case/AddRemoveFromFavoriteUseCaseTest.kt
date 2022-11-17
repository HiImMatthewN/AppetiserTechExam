package com.nantesmatthew.movie.domain.use_case

import com.nantesmatthew.core.util.Status
import com.nantesmatthew.movie.domain.model.Movie
import com.nantesmatthew.movie.domain.repository.MovieRepositoryTestImpl
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class AddRemoveFromFavoriteUseCaseTest {
    private val repository = MovieRepositoryTestImpl()
    private val useCase by lazy {
        AddRemoveFromFavoritesUseCase(repository)
    }

    @Before
    fun setup(){
        repository.dummyFaveMovieDataSource.add(Movie(123,"Avengers","Tom Holland","Action,Superhero","dummyArtwork",150.0,10000,"dummyPreviewUrl","Superhero Movie","Superhero movie for everyone",true))
        repository.dummyFaveMovieDataSource.add(Movie(456,"Batman","Christian Bale","Action,Superhero","dummyArtwork",300.0,15000,"dummyPreviewUrl","Superhero Movie","Superhero movie for adults",true))
        repository.dummyFaveMovieDataSource.add(Movie(789,"Back to the future","Michael J. Fox","Sci-fi","dummyArtwork",200.0,2000,"dummyPreviewUrl","Time Traveling","Need to save doc",true))
    }

    @Test
    fun Should_Add_To_Favorite_When_Adding() = runBlocking {
        val newMovie = Movie(999,"Titanic","Tom Holland","Drama","dummyArtwork",150.0,10000,"dummyPreviewUrl","Sinking Ship Movie","Jack should have been saved",false)
        val addResult = useCase(newMovie)
        assert(addResult.status == Status.SUCCESS)

    }

    @Test
    fun Should_Remove_From_Favorite_When_Removing() = runBlocking {
        val movieToBeRemoved = Movie(789,"Back to the future","Michael J. Fox","Sci-fi","dummyArtwork",200.0,2000,"dummyPreviewUrl","Time Traveling","Need to save doc",true)
        val removeResult = useCase(movieToBeRemoved)
        assert(removeResult.status == Status.SUCCESS)

    }


    @After
    fun tearDown(){
        repository.dummyFaveMovieDataSource.clear()
    }

}