package br.com.orlandoburli.dinnerroom.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Comanda(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "mesa_id")
    val idMesa: Int,

    val quantidadePessoas: Int = 1,

    val status: StatusMesa
) {
}