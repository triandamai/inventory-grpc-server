package app.trian.inventory.module.common

import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter


private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME



fun toOffsetDateTime(value:String):OffsetDateTime{
    return formatter.parse(value,OffsetDateTime::from)
}


fun OffsetDateTime.fromOffsetDateTime():String{
    return this.format(formatter)
}
