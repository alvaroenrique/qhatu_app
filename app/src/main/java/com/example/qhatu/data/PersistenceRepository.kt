package com.example.qhatu.data

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.qhatu.ui.model.User
import java.lang.Exception

class PersistenceRepository {

    /*fun saveOnUsersDatabase(context: Context, user: User) {
        val db = Room.databaseBuilder(context, AppDatabase::class.java, "qhatu_db").build()
        Thread {
            db.userDAO().insert(user)
        }.start()
    }

    fun findUserByUid(context: Context, uid: String, block: (user: User) -> Unit) {
        val db = Room.databaseBuilder(context, AppDatabase::class.java, "qhatu_db").build()
        Thread {
            try {
                val user = db.userDAO().findByUID(uid)
                Log.d("PersistenceLog::", "Usuario Encontrado")
                block(user)
            } catch (ex:Exception){
                Log.d("PersistenceLog::", "Usuario No Encontrado")
                block(User())
            }
        }
    }*/
}