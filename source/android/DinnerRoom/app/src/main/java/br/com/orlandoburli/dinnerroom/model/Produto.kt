package br.com.orlandoburli.dinnerroom.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity
class Produto constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val nome: String,

    val preco: BigDecimal
) {
}