package app.trian.inventory.module.error

import org.springframework.http.HttpStatus

data class BaseResponse<DATA>(
    var code:Int,
    var data:DATA,
    var message:String,
    var errorCode:Int=0,
)