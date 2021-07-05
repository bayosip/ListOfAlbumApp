package com.vertex5.jpmorganalbums

import com.vertex5.jpmorganalbums.model.data.Album
import com.vertex5.jpmorganalbums.model.network.AlbumRetrivalService
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class RESTApiTest {

    val expectedRepos = listOf(
        Album(1, 9, "album-1"),
        Album(2, 7,"album-2")
    )

    @Mock
    private lateinit var service:AlbumRetrivalService


    @Before
    internal fun setUp() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(AlbumRetrivalService::class.java)
    }

    @Test
    internal fun should_callServiceWithCoroutine() {
        runBlocking {
            val repos = service.retrieveAlbumsFromNetwork()
            assertNotNull(repos)
            repos.forEach(::println)
        }
    }

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}