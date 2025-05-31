package com.example.domain

import com.example.domain.model.Movie
import com.example.domain.repository.MovieRepository
import com.example.domain.usecase.GetPopularMoviesUseCase
import org.junit.Test
import org.junit.Before
import org.mockito.Mockito.mock
import org.mockito.Mockito.whenever
import org.junit.Assert.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.verify
import org.hamcrest.MatcherAssert.assertThat
import org.mockito.kotlin.whenever

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}

class GetMoviesUseCaseTest {
    private lateinit var useCase: GetPopularMoviesUseCase
    private lateinit var repository: MovieRepository
    
    @Before
    fun setup() {
        repository = mock()
        useCase = GetPopularMoviesUseCase(repository)
    }
    
    @Test
    fun `when execute is called, then return movies from repository`() = runTest {
        // Given
        val movies = listOf(Movie(1, "Test Movie","","","","",5.0,"true))
        whenever(repository.getMovies()).thenReturn(flow { emit(movies) })
        
        // When
        val result = useCase().first()
        
        // Then
        assertThat(result).isEqualTo(movies)
        verify(repository).getMovies()
    }
}