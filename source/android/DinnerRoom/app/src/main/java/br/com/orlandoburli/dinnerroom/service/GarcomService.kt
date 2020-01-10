package br.com.orlandoburli.dinnerroom.service

import android.content.Context
import android.os.AsyncTask
import br.com.orlandoburli.dinnerroom.R
import br.com.orlandoburli.dinnerroom.dao.GarcomDao
import br.com.orlandoburli.dinnerroom.exception.garcom.UsuarioSenhaInvalidosException
import br.com.orlandoburli.dinnerroom.model.Garcom

open class GarcomService(
    val context: Context,
    val garcomDao: GarcomDao
) {

    @Throws(UsuarioSenhaInvalidosException::class)
    fun login(login: String, password: String): Garcom {
        val garcom = byLogin(login)

        garcom?.let {
            if (garcom.login == login && garcom.senha == password) {
                return garcom
            }
        }

        throw UsuarioSenhaInvalidosException(context.getString(R.string.mensagem_usuario_senha_invalidos))
    }

    fun byLogin(login: String): Garcom? {
        return object : AsyncTask<Void, Void, Garcom>() {
            override fun doInBackground(vararg params: Void?): Garcom? {
                return garcomDao.byLogin(login)
            }
        }.execute().get()
    }

    fun sample() {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg params: Void?): Void? {
                sampleData()
                return null
            }
        }.execute()
    }

    private fun sampleData() {
        garcomDao.add(Garcom(nome = "Jose dos Santos", login = "jose", senha = "123456"))
    }
}