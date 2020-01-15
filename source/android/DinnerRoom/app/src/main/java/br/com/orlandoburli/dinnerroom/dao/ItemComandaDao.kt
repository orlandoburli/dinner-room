package br.com.orlandoburli.dinnerroom.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.orlandoburli.dinnerroom.model.Comanda
import br.com.orlandoburli.dinnerroom.model.ItemComanda

@Dao
interface ItemComandaDao {

    @Query("SELECT * FROM ItemComanda i WHERE i.comanda_id = :idComanda")
    fun byComandaId(idComanda: Long): List<ItemComanda>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(itemComanda: ItemComanda): Long
}