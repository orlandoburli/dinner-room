package br.com.orlandoburli.dinnerroom.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class Comanda(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "mesa_id")
    val idMesa: Long,

    val quantidadePessoas: Long = 1,

    val status: StatusMesa = StatusMesa.ABERTA
) : Serializable {
}