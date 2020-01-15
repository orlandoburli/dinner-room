package br.com.orlandoburli.dinnerroom.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.orlandoburli.dinnerroom.converters.BigDecimalConverter
import br.com.orlandoburli.dinnerroom.dao.*
import br.com.orlandoburli.dinnerroom.model.*

@Database(
    version = 1,
    entities = [Garcom::class, Mesa::class, Produto::class, Comanda::class, ItemComanda::class],
    exportSchema = false
)
@TypeConverters(BigDecimalConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun garcomDao(): GarcomDao

    abstract fun mesaDao(): MesaDao

    abstract fun produtoDao(): ProdutoDao

    abstract fun comandaDao(): ComandaDao

    abstract fun itemComandaDao(): ItemComandaDao
}