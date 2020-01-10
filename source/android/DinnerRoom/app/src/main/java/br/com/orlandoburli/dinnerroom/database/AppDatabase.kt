package br.com.orlandoburli.dinnerroom.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.orlandoburli.dinnerroom.dao.GarcomDao
import br.com.orlandoburli.dinnerroom.model.Garcom

@Database(version = 1, entities = [Garcom::class], exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun garcomDao(): GarcomDao

    fun clearData() {
        this.clearAllTables()
    }
}