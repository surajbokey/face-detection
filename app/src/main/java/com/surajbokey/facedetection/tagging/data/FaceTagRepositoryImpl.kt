package com.surajbokey.facedetection.tagging.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.surajbokey.facedetection.tagging.domain.FaceTagRepository
import com.surajbokey.facedetection.tagging.domain.model.FaceTag
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FaceTagRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : FaceTagRepository {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("face-store")
    private val dataStore = context.dataStore

    override suspend fun saveFaceTag(faceTag: FaceTag) {
        dataStore.edit { editor ->
            editor[stringPreferencesKey(faceTag.faceId)] = faceTag.name
        }
    }

    override fun getFaceTag(faceId: String): Flow<FaceTag> {
        return dataStore.data.map { prefs ->
            FaceTag(faceId, prefs[stringPreferencesKey(faceId)] ?: "")
        }
    }
}
