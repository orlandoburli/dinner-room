package br.com.orlandoburli.dinnerroom.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class Garcom(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nome: String,
    val login: String,
    val senha: String
) : Serializable {
}