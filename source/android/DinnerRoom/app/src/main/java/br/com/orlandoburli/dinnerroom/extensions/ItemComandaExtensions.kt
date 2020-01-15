package br.com.orlandoburli.dinnerroom.extensions

import br.com.orlandoburli.dinnerroom.model.ItemComanda
import java.math.BigDecimal

fun List<ItemComanda>.total(): BigDecimal {
    return this.map { item -> item.precoTotal }.sum { it }
}
