package br.com.orlandoburli.dinnerroom.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable
import java.math.BigDecimal

@Entity
class ItemComanda
@Ignore constructor
    (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    @ColumnInfo(name = "comanda_id")
    var idComanda: Long = 0,

    @ColumnInfo(name = "produto_id")
    val idProduto: Long,

    val quantidade: BigDecimal,

    val precoUnitario: BigDecimal,

    @Ignore
    var produto: Produto? = null
) : Serializable {

    constructor(
        id: Long,
        idComanda: Long,
        idProduto: Long,
        quantidade: BigDecimal,
        precoUnitario: BigDecimal
    ) : this(id, idComanda, idProduto, quantidade, precoUnitario, null)

    val precoTotal: BigDecimal get() = quantidade.multiply(precoUnitario)
}