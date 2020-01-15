package br.com.orlandoburli.dinnerroom.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.math.BigDecimal

@Entity
class Produto constructor(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    val nome: String,

    val preco: BigDecimal
): Serializable {
}