package br.com.orlandoburli.dinnerroom.dao

import androidx.room.Dao
import androidx.room.Query
import br.com.orlandoburli.dinnerroom.model.Comanda

@Dao
interface ComandaDao {

    @Query("SELECT * FROM Comanda c WHERE c.mesa_id = :idMesa AND c.status = 'ABERTA'")
    fun byIdMesa(idMesa: Int): Comanda
}