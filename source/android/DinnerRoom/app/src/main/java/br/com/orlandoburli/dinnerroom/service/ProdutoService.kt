package br.com.orlandoburli.dinnerroom.service

import android.content.Context
import android.os.AsyncTask
import br.com.orlandoburli.dinnerroom.dao.ProdutoDao
import br.com.orlandoburli.dinnerroom.database.AppDatabase
import br.com.orlandoburli.dinnerroom.database.DbHelper
import br.com.orlandoburli.dinnerroom.model.Mesa
import br.com.orlandoburli.dinnerroom.model.Produto

class ProdutoService(val context: Context) {

    private val database: AppDatabase by lazy {
        DbHelper.db(context = context)
    }

    val produtoDao: ProdutoDao by lazy {
        database.produtoDao()
    }

    fun all() : List<Produto> {
        return object : AsyncTask<Void, Void, List<Produto>>() {
            override fun doInBackground(vararg params: Void?): List<Produto> {
                return produtoDao.all()
            }
        }.execute().get()
    }
}