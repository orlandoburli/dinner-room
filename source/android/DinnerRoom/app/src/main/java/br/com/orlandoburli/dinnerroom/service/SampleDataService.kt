package br.com.orlandoburli.dinnerroom.service

import android.content.Context
import android.os.AsyncTask
import br.com.orlandoburli.dinnerroom.database.AppDatabase
import br.com.orlandoburli.dinnerroom.database.DbHelper
import br.com.orlandoburli.dinnerroom.model.Garcom
import br.com.orlandoburli.dinnerroom.model.Mesa
import br.com.orlandoburli.dinnerroom.model.Produto
import java.math.BigDecimal
import java.text.NumberFormat

class SampleDataService(val context: Context) {

    fun sample() {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg params: Void?): Void? {
                sampleData()
                return null
            }
        }.execute()
    }

    fun sampleData() {
        val db = DbHelper.db(context)

        sampleGarcom(db)
        sampleMesa(db)
        sampleProdutos(db)
    }

    private fun sampleProdutos(db: AppDatabase) {
        val produtoDao = db.produtoDao()

        produtoDao.add(Produto(nome = "Coca cola Lata", preco = BigDecimal(7.00)))
        produtoDao.add(Produto(nome = "Fanta Laranja", preco = BigDecimal(7.00)))
        produtoDao.add(Produto(nome = "Guarana Kuat", preco = BigDecimal(7.00)))
        produtoDao.add(Produto(nome = "Sprite", preco = BigDecimal(7.00)))
        produtoDao.add(Produto(nome = "Fanta Uva", preco = BigDecimal(7.00)))

        produtoDao.add(Produto(nome = "Espetinho de alcatra", preco = BigDecimal(14.00)))
        produtoDao.add(Produto(nome = "Espetinho de filé", preco = BigDecimal(25.00)))
        produtoDao.add(Produto(nome = "Espetinho de picanha", preco = BigDecimal(30.00)))
    }

    private fun sampleMesa(db: AppDatabase) {
        val mesaDao = db.mesaDao()

        for (i in 1..12) {
            mesaDao.add(Mesa(numero = i.toString().padStart(3, '0')))
        }
    }

    private fun sampleGarcom(db: AppDatabase) {
        val garcomDao = db.garcomDao()

        garcomDao.add(Garcom(nome = "Jose dos Santos", login = "jose", senha = "123456"))
        garcomDao.add(Garcom(nome = "Alex dos Santos", login = "alex", senha = "abcdef"))
        garcomDao.add(Garcom(nome = "Carlos Bragança", login = "carlosb", senha = "poilkj"))
    }
}