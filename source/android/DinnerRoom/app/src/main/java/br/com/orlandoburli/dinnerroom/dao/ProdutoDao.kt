package br.com.orlandoburli.dinnerroom.dao

import androidx.room.*
import br.com.orlandoburli.dinnerroom.model.Produto

@Dao
interface ProdutoDao {

    @Query("SELECT * FROM Produto ORDER BY nome")
    fun all(): List<Produto>

    @Query("SELECT * FROM Produto p WHERE p.id = :idProduto")
    fun byId(idProduto: Long): Produto?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(produto: Produto): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(produto: Produto)

    @Delete
    fun delete(produto: Produto)
}