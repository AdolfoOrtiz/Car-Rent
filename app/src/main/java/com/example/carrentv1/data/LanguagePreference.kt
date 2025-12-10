package com.example.carrentv1.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.languageDataStore: DataStore<Preferences> by preferencesDataStore(name = "language_preferences")

class LanguagePreference(private val context: Context) {

    companion object {
        val LANGUAGE_KEY = stringPreferencesKey("selected_language")
        const val DEFAULT_LANGUAGE = "es" // Español como idioma por defecto
    }

    suspend fun saveLanguagePreference(languageCode: String) {
        context.languageDataStore.edit { preferences ->
            preferences[LANGUAGE_KEY] = languageCode
        }
    }

    val languagePreference: Flow<String> = context.languageDataStore.data
        .map { preferences ->
            preferences[LANGUAGE_KEY] ?: DEFAULT_LANGUAGE
        }
}
