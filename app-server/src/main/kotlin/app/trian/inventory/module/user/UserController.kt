package app.trian.inventory.module.user

import app.trian.inventory.v1.getPagingRequest
import kotlinx.coroutines.coroutineScope
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.awt.print.Pageable

@RestController
class UserController(
    private val userService: UserService
) {
    @GetMapping(
        value = ["/api/v1/users"],
        produces = ["application/json"]
    )
    suspend fun getListUsers(@RequestParam(name = "page") nextPage: Long = 0) = coroutineScope {
        userService.getListUser(
            getPagingRequest {
                page = nextPage
            }
        )
    }


}