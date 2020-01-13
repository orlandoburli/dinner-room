package br.com.orlandoburli.dinnerroom.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Mesa(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val numero: String
) {
}