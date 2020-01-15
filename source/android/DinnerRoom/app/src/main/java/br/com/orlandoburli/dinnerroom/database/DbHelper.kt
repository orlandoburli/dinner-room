package br.com.orlandoburli.dinnerroom.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.orlandoburli.dinnerroom.BuildConfig


class DbHelper() {

    companion object {

        /**
         * Retorna uma instancia do databasebuilder
         */
        fun db(context: Context): AppDatabase {
            return Room
                .databaseBuilder(context, AppDatabase::class.java, BuildConfig.DATABASE_NAME)
                .setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
                .build()
        }
    }
}
