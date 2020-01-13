package br.com.orlandoburli.dinnerroom.dao

import androidx.room.*
import br.com.orlandoburli.dinnerroom.model.Mesa

@Dao
interface MesaDao {

    @Query("SELECT * FROM Mesa")
    fun all(): List<Mesa>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(mesa: Mesa)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(mesa: Mesa)

    @Delete
    fun delete(mesa: Mesa)
}