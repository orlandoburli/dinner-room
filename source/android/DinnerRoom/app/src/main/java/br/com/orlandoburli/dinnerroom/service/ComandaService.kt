package br.com.orlandoburli.dinnerroom.service

import android.os.AsyncTask
import br.com.orlandoburli.dinnerroom.dao.ComandaDao
import br.com.orlandoburli.dinnerroom.dao.ItemComandaDao
import br.com.orlandoburli.dinnerroom.dao.ProdutoDao
import br.com.orlandoburli.dinnerroom.database.AppDatabase
import br.com.orlandoburli.dinnerroom.model.Comanda
import br.com.orlandoburli.dinnerroom.model.ItemComanda
import br.com.orlandoburli.dinnerroom.model.Mesa
import br.com.orlandoburli.dinnerroom.model.Produto
import java.math.BigDecimal

class ComandaService(database: AppDatabase) {
    val comandaDao: ComandaDao by lazy {
        database.comandaDao()
    }

    val itemComandaDao: ItemComandaDao by lazy {
        database.itemComandaDao()
    }

    val produtoDao: ProdutoDao by lazy {
        database.produtoDao()
    }

    fun getByMesa(mesa: Mesa): Comanda {
        val comanda = this.byIdMesa(mesa.id)

        return if (comanda == null) {
            salva(Comanda(idMesa = mesa.id))
        } else {
            comanda
        }
    }

    fun salva(comanda: Comanda): Comanda {
        val idComanda = object : AsyncTask<Void, Void, Long>() {
            override fun doInBackground(vararg params: Void?): Long {
                return comandaDao.insert(comanda)
            }
        }.execute().get()

        return Comanda(
            id = idComanda,
            quantidadePessoas = comanda.quantidadePessoas,
            idMesa = comanda.idMesa,
            status = comanda.status
        )
    }

    fun adicionaItem(itemComanda: ItemComanda) {
        val idItemComanda = object : AsyncTask<ItemComanda, Void, Long>() {
            override fun doInBackground(vararg params: ItemComanda?): Long? {
                return params[0]?.let { itemComandaDao.insert(it) }
            }
        }
            .execute(itemComanda)
            .get()

        itemComanda.id = idItemComanda
    }

    fun adicionaItem(comanda: Comanda, produto: Produto, quantidade: Long = 1) {
        this.adicionaItem(
            ItemComanda(
                idComanda = comanda.id,
                quantidade = BigDecimal(quantidade),
                produto = produto,
                idProduto = produto.id,
                precoUnitario = produto.preco
            )
        )
    }

    fun itensComanda(idComanda: Long): List<ItemComanda> {
        val list = object : AsyncTask<Long, Void, List<ItemComanda>>() {
            override fun doInBackground(vararg params: Long?): List<ItemComanda>? {
                return params[0]?.let { itemComandaDao.byComandaId(it) }
            }
        }
            .execute(idComanda)
            .get()

        preencheProdutosComanda(list)

        return list
    }

    private fun preencheProdutosComanda(list: List<ItemComanda>) {
        for (item in list) item.produto = produto(item)
    }

    private fun produto(item: ItemComanda): Produto? {
        return object : AsyncTask<Long, Void, Produto?>() {
            override fun doInBackground(vararg params: Long?): Produto? {
                return params[0]?.let { produtoDao.byId(it) }
            }
        }
            .execute(item.idProduto)
            .get()
    }

    private fun byIdMesa(idMesa: Long): Comanda? {
        return object : AsyncTask<Void, Void, Comanda>() {
            override fun doInBackground(vararg params: Void?): Comanda? {
                return comandaDao.byIdMesa(idMesa)
            }
        }
            .execute()
            .get()
    }

}