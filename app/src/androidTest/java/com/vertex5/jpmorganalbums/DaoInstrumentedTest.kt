package com.vertex5.jpmorganalbums

import android.content.Context
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vertex5.jpmorganalbums.model.data.Album
import com.vertex5.jpmorganalbums.model.data.AlbumDAO
import com.vertex5.jpmorganalbums.model.data.AlbumDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.io.IOException

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class DaoInstrumentedTest {

    private lateinit var dao: AlbumDAO
    private lateinit var db: AlbumDatabase
    private lateinit var context: Context

    @Test
    fun useAppContext() {
        // Context of the app under test.
        //val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.vertex5.jpmorganalbums", context.packageName)
    }

    @Before
    fun createDb() {
       context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, AlbumDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        dao = db.getDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndRetrieveAlbumFromDb() = runBlocking {
        val album = Album(1,1, "Twice As Tall")
        dao.insert(album)
        val all = dao.getAlbums().first()
        assertEquals(all[0].title, album.title)
    }

    @Test
    @Throws(Exception::class)
    fun getAllAlbums() = runBlocking {
        val album = Album(2, 3, "My Twisted Dark Fantasy")
        val album2 = Album(3, 3, "JESUS is King")
        dao.insert(album)
        dao.insert(album2)
        val alllist = dao.getAlbums().first()
        assertEquals(alllist[0].userId, album.userId)
        assertEquals(alllist[1].title, album2.title)
    }

    @Test
    @Throws(Exception::class)
    fun deleteAll() = runBlocking {
        val album = Album(4, 3, "College Dropout")
        val album2 = Album(5, 3, "Graduation")
        dao.insert(album)
        dao.insert(album2)
        dao.deleteAll()
        val list = dao.getAlbums().first()
        assertTrue(list.isEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun getAlbumsInDescOrder()= runBlocking{
        val test = mutableListOf<Album>()
        val album = Album(6, 3, "College Dropout")
        val album2 = Album(7, 3, "Graduation")
        test.add(album)
        test.add(album2)
        test.sortByDescending {
            it.title
        }
        dao.insert(album)
        dao.insert(album2)

        val list = dao.getAlbumsDescendingOrderTitle().first()
        assertFalse(list.isEmpty())
        assertEquals(list[0].title, test[0].title)
    }

    @Test
    @Throws(Exception::class)
    fun getAlbumsInAscOrder()= runBlocking{
        val test = mutableListOf<Album>()
        val album = Album(6, 3, "College Dropout")
        val album2 = Album(7, 3, "Graduation")
        test.add(album)
        test.add(album2)
        test.sortBy {
            it.title
        }
        dao.insert(album)
        dao.insert(album2)

        val list = dao.getAlbumsAscendingOrderTitle().first()
        assertFalse(list.isEmpty())
        assertEquals(list[0].title, test[0].title)
    }
}