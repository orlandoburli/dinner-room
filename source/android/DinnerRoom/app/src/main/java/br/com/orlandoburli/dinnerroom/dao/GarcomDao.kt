package br.com.orlandoburli.dinnerroom.dao

import androidx.room.*
import br.com.orlandoburli.dinnerroom.model.Garcom

@Dao
interface GarcomDao {

    @Query("SELECT * FROM garcom")
    fun all(): List<Garcom>

    @Query("SELECT * FROM garcom g WHERE g.login = :login")
    fun byLogin(login: String): Garcom?

    @Insert
    fun add(garcom: Garcom)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(vararg garcom: Garcom)

    @Delete
    fun delete(garcom: Garcom)
}