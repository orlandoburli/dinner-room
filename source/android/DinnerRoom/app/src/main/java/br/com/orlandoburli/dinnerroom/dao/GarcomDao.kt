package br.com.orlandoburli.dinnerroom.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import br.com.orlandoburli.dinnerroom.model.Garcom

@Dao
interface GarcomDao {

    @Query("SELECT * FROM garcom")
    fun all(): List<Garcom>

    @Query("SELECT * FROM garcom g WHERE g.login = :login")
    fun byLogin(login: String): Garcom?

    @Insert(onConflict = REPLACE)
    fun add(garcom: Garcom)

    @Update(onConflict = REPLACE)
    fun update(garcom: Garcom)

    @Delete
    fun delete(garcom: Garcom)
}