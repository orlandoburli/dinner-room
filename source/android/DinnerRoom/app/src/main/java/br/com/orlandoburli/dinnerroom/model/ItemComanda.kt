package br.com.orlandoburli.dinnerroom.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity
class ItemComanda(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "comanda_id")
    val idComanda: Int = 0,

    @ColumnInfo(name = "produto_id")
    val idProduto: Int,

    val quantidade: BigDecimal,
    val precoUnitario: BigDecimal
) {

}
