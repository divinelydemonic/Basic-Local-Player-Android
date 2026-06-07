package kr.android.basiclocalplayerpractice.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore
    by preferencesDataStore(name = "favorites_preferences")

class FavoritesDataStore(
    private val context: Context
) {

    companion object {
        private val FAVORITE_SONG_IDS =
            stringSetPreferencesKey("favorite_song_ids")
    }

    val favoriteSongIds: Flow<Set<Long>> =
        context.dataStore.data.map { preferences ->
            preferences[FAVORITE_SONG_IDS]
                ?.map { it.toLong() }
                ?.toSet()
                ?: emptySet()

        }

    suspend fun saveFavoriteSongIds(
        favoriteSongIds: Set<Long>
    ) {

        context.dataStore.edit { preferences ->
            preferences[FAVORITE_SONG_IDS] =
                favoriteSongIds.map { it.toString() }.toSet()

        }

    }

}