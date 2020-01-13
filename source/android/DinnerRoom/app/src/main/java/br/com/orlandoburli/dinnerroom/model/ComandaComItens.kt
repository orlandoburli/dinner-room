package br.com.orlandoburli.dinnerroom.model

import androidx.room.Embedded
import androidx.room.Relation

class ComandaComItens(

    @Embedded
    val comanda: Comanda,

    @Relation(parentColumn = "id", entityColumn = "idMesa")
    val itens: List<ItemComanda> = listOf()
) {
}