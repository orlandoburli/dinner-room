package br.com.orlandoburli.dinnerroom.service

import android.content.Context
import android.os.AsyncTask
import br.com.orlandoburli.dinnerroom.dao.MesaDao
import br.com.orlandoburli.dinnerroom.database.AppDatabase
import br.com.orlandoburli.dinnerroom.database.DbHelper
import br.com.orlandoburli.dinnerroom.model.Mesa

class MesaService(val context: Context) {

    private val database: AppDatabase by lazy {
        DbHelper.db(context = context)
    }

    val mesaDao: MesaDao by lazy {
        database.mesaDao()
    }

    fun all(): List<Mesa> {
        return object : AsyncTask<Void, Void, List<Mesa>>() {
            override fun doInBackground(vararg params: Void?): List<Mesa> {
                return mesaDao.all()
            }
        }.execute().get()
    }
}