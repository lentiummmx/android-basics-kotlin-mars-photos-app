package com.example.android.marsphotos

import com.example.android.marsphotos.network.MarsApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MarsApiServiceTests: BaseTest() {

    private lateinit var srvc: MarsApiService

    @Before
    fun setup() {
        val url = mockWebServer.url("/")
        srvc = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(
                Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()
            ))
            .baseUrl(url)
            .build()
            .create(MarsApiService::class.java)
    }

    @Test
    fun api_service() {
        enqueue("mars_photos.json")
        runBlocking {
            val apiResponse = srvc.getPhotos()
            assertNotNull(apiResponse)
            assertTrue("The list was empty", apiResponse.isNotEmpty())
            assertEquals("The size of the list did not equals", 2, apiResponse.size)
            assertEquals("The IDs did not match", "424905", apiResponse[0].id)
        }
    }

}