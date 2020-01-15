package br.com.orlandoburli.dinnerroom.ui.activity

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import androidx.test.InstrumentationRegistry
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import br.com.orlandoburli.dinnerroom.BuildConfig
import br.com.orlandoburli.dinnerroom.R
import br.com.orlandoburli.dinnerroom.dao.GarcomDao
import br.com.orlandoburli.dinnerroom.dao.MesaDao
import br.com.orlandoburli.dinnerroom.dao.ProdutoDao
import br.com.orlandoburli.dinnerroom.database.AppDatabase
import br.com.orlandoburli.dinnerroom.database.DbHelper
import br.com.orlandoburli.dinnerroom.extensions.formataMoeda
import br.com.orlandoburli.dinnerroom.model.Garcom
import br.com.orlandoburli.dinnerroom.model.Mesa
import br.com.orlandoburli.dinnerroom.model.Produto
import br.com.orlandoburli.dinnerroom.service.ComandaService
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.math.BigDecimal


class ComandaActivityTest {

    private val MAXIMO_MESAS = 12

    private val db: AppDatabase by lazy { DbHelper.db(InstrumentationRegistry.getTargetContext()) }

    private val mesaDao: MesaDao by lazy { db.mesaDao() }

    private val garcomDao: GarcomDao by lazy { db.garcomDao() }

    private val produtoDao: ProdutoDao by lazy { db.produtoDao() }

    private val comandaService by lazy { ComandaService(db) }

    @Rule
    @JvmField
    val loginTestRule = ActivityTestRule(LoginActivity::class.java, true, false)
    private val cocaCola = Produto(nome = "Coca cola Lata", preco = BigDecimal(7.00))
    private val fanta = Produto(nome = "Fanta Laranja", preco = BigDecimal(7.10))
    private val guaranaKuat = Produto(nome = "Guarana Kuat", preco = BigDecimal(7.20))
    private val sprite = Produto(nome = "Sprite", preco = BigDecimal(7.30))
    private val fantaUva = Produto(nome = "Fanta Uva", preco = BigDecimal(7.40))
    private val espetinhoAlcatra = Produto(nome = "Espetinho de alcatra", preco = BigDecimal(14.00))
    private val espetinhoFile = Produto(nome = "Espetinho de filé", preco = BigDecimal(25.00))

    private val espetinhoPicanha = Produto(nome = "Espetinho de picanha", preco = BigDecimal(30.00))

    @Before
    fun setup() {
        limpaDados()

        prepararDados()

        loginTestRule.launchActivity(Intent())

        entrar("jose", "123456")
    }

    @Test
    fun deve_AdicionarItemNaComanda_QuandoClicarNaMesa001() {
        clicarMesa("001")

        verificaTituloAppBar("Mesa 001 Comanda 1")

        verificaTotalComanda(BigDecimal.ZERO)

        clicarBotaoAdicionarItem()

        Thread.sleep(5000)

        verificaTituloAppBar("Seleção de Produtos")

        clicaNoProduto(0, cocaCola)

        Thread.sleep(5000)

        verificaTotalComanda(BigDecimal(7))

        Thread.sleep(5000)
    }

    private fun clicaNoProduto(
        posicao: Int,
        produto: Produto
    ) {
        onView(produtoNaPosicao(posicao, produto))
            .check(matches(isDisplayed()))
            .perform(click())
    }

    private fun produtoNaPosicao(posicao: Int, produto: Produto): Matcher<View> {
        return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {

            val displayed = isDisplayed()

            override fun describeTo(description: Description) {
                description.appendText("Buscando produto ${produto.nome} na posição $posicao")
            }

            override fun matchesSafely(item: RecyclerView?): Boolean {
                val holderView = item?.findViewHolderForAdapterPosition(posicao)?.itemView

                val hasDescricao = possuiDescricaoProduto(holderView)

                val hasPreco = possuiPrecoProduto(holderView)

                return hasDescricao && hasPreco && displayed.matches(holderView)
            }

            private fun possuiDescricaoProduto(holderView: View?): Boolean {
                val campoDescricaoProduto =
                    holderView?.findViewById<TextView>(R.id.item_selecao_produto_descricao)

                Log.w(
                    "comandaActivityTest",
                    "Campo descrição ${campoDescricaoProduto?.text}"
                )

                val hasDescricao =
                    campoDescricaoProduto?.let {
                        it.text == produto.nome
                    } ?: run {
                        false
                    }
                return hasDescricao
            }

            private fun possuiPrecoProduto(holderView: View?): Boolean {
                val campoPrecoProduto =
                    holderView?.findViewById<TextView>(R.id.item_selecao_produto_preco)

                Log.w(
                    "comandaActivityTest",
                    "Campo preço ${campoPrecoProduto?.text}"
                )

                val hasPreco = campoPrecoProduto?.let {
                    it.text == produto.preco.formataMoeda()
                } ?: run {
                    false
                }
                return hasPreco
            }
        }
    }


    private fun verificaTotalComanda(valorComanda: BigDecimal) {
        onView(withId(R.id.layout_comanda_screen_total))
            .check(matches(withText(valorComanda.formataMoeda())))
            .check(matches(isDisplayed()))
    }

    private fun clicarMesa(numeroMesa: String) {
        onView(
            allOf(
                withText(numeroMesa),
                instanceOf(TextView::class.java)
            )
        )
            .check(matches(isDisplayed()))
            .perform(click())
    }

    private fun verificaTituloAppBar(title: String) {
        onView(
            allOf(
                withParent(
                    allOf(
                        instanceOf(Toolbar::class.java),
                        withResourceName("action_bar")
                    )
                ),
                instanceOf(TextView::class.java),
                withText(title)
            )
        )
            .check(matches(isDisplayed()))
    }

    private fun clicarBotaoAdicionarItem() {
        onView(withId(R.id.layout_comanda_screen_botao_adicionar_item))
            .check(matches(isDisplayed()))
            .perform(click())
    }

    private fun entrar(login: String, senha: String) {
        preencheUsuarioSenha(login, senha)

        clicarBotaoEntrar()
    }

    private fun preencheUsuarioSenha(login: String, senha: String) {

        preencherLogin(login)

        preencherSenha(senha)
    }

    private fun preencherLogin(login: String) {
        onView(withId(R.id.layout_login_screen_login))
            .check(matches(isDisplayed()))
            .perform(click())
            .perform(ViewActions.typeText(login), ViewActions.closeSoftKeyboard())
    }

    private fun preencherSenha(senha: String) {
        onView(withId(R.id.layout_login_screen_password))
            .check(matches(isDisplayed()))
            .perform(click())
            .perform(ViewActions.typeText(senha), ViewActions.closeSoftKeyboard())
    }

    private fun clicarBotaoEntrar() {
        onView(withId(R.id.layout_login_screen_botao_entrar))
            .check(matches(isDisplayed()))
            .perform(click())
    }

    private fun prepararDados() {
        criaGarcomJose(garcomDao)
        criaMesas(mesaDao)
        criaProdutos(produtoDao)
    }

    private fun criaGarcomJose(garcomDao: GarcomDao) {
        garcomDao.add(Garcom(nome = "José da Silva", login = "jose", senha = "123456"))
    }

    private fun criaMesas(mesaDao: MesaDao) {
        for (i in 1..MAXIMO_MESAS) {
            mesaDao.add(Mesa(numero = i.toString().padStart(3, '0')))
        }
    }

    private fun criaProdutos(produtoDao: ProdutoDao) {
        cocaCola.id = produtoDao.add(cocaCola)
        fanta.id = produtoDao.add(fanta)
        guaranaKuat.id = produtoDao.add(guaranaKuat)
        sprite.id = produtoDao.add(sprite)
        fantaUva.id = produtoDao.add(fantaUva)
        espetinhoAlcatra.id = produtoDao.add(espetinhoAlcatra)
        espetinhoFile.id = produtoDao.add(espetinhoFile)
        espetinhoPicanha.id = produtoDao.add(espetinhoPicanha)
    }

    @After
    fun tearDown() {
        this.limpaDados()
        this.db.close()
    }

    fun limpaDados() {
        val context = InstrumentationRegistry.getTargetContext()
        context.deleteDatabase(BuildConfig.DATABASE_NAME)
    }
}