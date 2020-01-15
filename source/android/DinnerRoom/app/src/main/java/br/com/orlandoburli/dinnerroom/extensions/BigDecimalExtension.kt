package br.com.orlandoburli.dinnerroom.extensions

import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

val formatoBrasileiro: NumberFormat = DecimalFormat.getCurrencyInstance(Locale("pt", "br"))
val formatoInteiro: NumberFormat = DecimalFormat.getIntegerInstance()

fun BigDecimal.formataMoeda(): String {
    return formatoBrasileiro
        .format(this)
        .replace("-R$", "R$ -")
}

fun BigDecimal.formataInteiro(): String {
    return formatoInteiro.format(this)
}

fun<T> Iterable<T>.sum(selector: (T) -> BigDecimal): BigDecimal {
    var sum: BigDecimal = BigDecimal.ZERO
    for (element in this) {
        sum += selector(element)
    }
    return sum
}
