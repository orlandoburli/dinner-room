package br.com.orlandoburli.dinnerroom.converters

import androidx.room.TypeConverter
import br.com.orlandoburli.dinnerroom.model.StatusMesa
import java.math.BigDecimal

class BigDecimalConverter {
    @TypeConverter
    fun fromStringToBigDecimal(value: String?): BigDecimal? {
        return if (value == null) null else BigDecimal(value)
    }

    @TypeConverter
    fun fromDoubleToBigDecimal(value: Double?): BigDecimal? {
        return value?.let { BigDecimal(value) }
    }

    @TypeConverter
    fun fromBigDecimalToDouble(value: BigDecimal?): Double? {
        return value?.let { value.toDouble() }
    }

    @TypeConverter
    fun fromStatusMesaToString(value: StatusMesa?): String? {
        return value?.let { value.name }
    }

    @TypeConverter
    fun fromStringToStatusMesa(value: String?) : StatusMesa?{
        return value?.let { StatusMesa.valueOf(value) }
    }
}


