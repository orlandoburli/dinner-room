package br.com.orlandoburli.dinnerroom.dao

import androidx.room.*
import br.com.orlandoburli.dinnerroom.model.Produto

@Dao
interface ProdutoDao {

    @Query("SELECT * FROM Produto")
    fun all(): List<Produto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(produto: Produto)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(produto: Produto)

    @Delete
    fun delete(produto: Produto)
}