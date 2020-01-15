package br.com.orlandoburli.dinnerroom.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class Mesa(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val numero: String
) : Serializable {
}