package app.trian.inventory.module.user

import app.trian.inventory.v1.user.GetListUserRequest
import app.trian.inventory.v1.user.GetListUserResponse
import app.trian.inventory.v1.user.getListUserRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.awt.print.Pageable

@RestController
class UserController(
    private val userService: UserService
) {
    @GetMapping(
        value = [
            "/api/v1/users"
        ],
        produces = [
            "application/json"
        ],
        consumes = [
            "*/*"
        ]
    )
    fun getListUsers(@RequestParam(name = "page") nextPage: Long = 0) = userService.getListUser(
        getListUserRequest {
            page = nextPage
        }
    )

}