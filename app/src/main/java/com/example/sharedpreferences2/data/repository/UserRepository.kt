package com.example.sharedpreferences2.data.repository

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private val Context.dataStore by preferencesDataStore(name = "file_datastore")

class UserRepository(context: Context) {

    //o context não pode ser o cotexto da main e sim da aplicação e ai não pode usar "this"

    //Definição de um companion objectpara representar os campos do arquivo dataStore
    companion object{
        val ATTR_USERNAME = stringPreferencesKey("username")
    }
    //Atributro de referencia ao dATAstore do conmtext
    private val dataStore = context.dataStore

    val usernameFlow: Flow<String?> = dataStore
        .data.catch{expection ->
            if (expection is IOException){
                emit(emptyPreferences())

            }else{
               throw expection
            }
        }.map {
            it.get(ATTR_USERNAME)
        }
    suspend fun saveUserame(username: String){
        dataStore.edit{
            it[ATTR_USERNAME] = username
        }
    }
}