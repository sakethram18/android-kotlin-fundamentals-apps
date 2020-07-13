/*
 * Copyright (C) 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.devbyteviewer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.android.devbyteviewer.database.VideosDatabase
import com.example.android.devbyteviewer.database.asDomainModel
import com.example.android.devbyteviewer.domain.DevByteVideo
import com.example.android.devbyteviewer.network.DevByteNetwork
import com.example.android.devbyteviewer.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class VideosRepository(private val videosDatabase: VideosDatabase) {

    // Putting the conversion in Transformation ensures that the conversion only happens when an
    // active activity or fragment is observing the live data
    val videos: LiveData<List<DevByteVideo>> = Transformations.map(
            videosDatabase.videoDao.getVideos()) {
        it.asDomainModel()
    }

    /**
     * This function is responsible for fetching the videos list from the network and putting
     * them all in the database for offline use. It has to use the IO dispatcher because of
     * the nature of the database operation which is an I/O that could block the current thread.
     */
    suspend fun refreshVideos() {
        withContext(Dispatchers.IO) {
            Timber.d("Refresh videos is called..")

            // Suspends the coroutine until retrifit fetches the videos from the network
            val videos = DevByteNetwork.devbytes.getPlaylist().await()
            videosDatabase.videoDao.insertAll(videos.asDatabaseModel())
        }
    }
}