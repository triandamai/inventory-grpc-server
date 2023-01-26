package app.trian.inventory.module.user

import app.trian.inventory.v1.getPagingRequest
import app.trian.inventory.v1.user.AssignRoleRequest
import app.trian.inventory.v1.user.CreateUserByAdminRequest
import app.trian.inventory.v1.user.UpdateUserRequest
import app.trian.inventory.v1.user.deleteUserRequest
import kotlinx.coroutines.coroutineScope
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.awt.print.Pageable

@RestController
@RequestMapping(
    value = ["/api/v1"]
)
class UserController(
    private val userService: UserService
) {
    @GetMapping(
        value = ["/users"],
        produces = ["application/json"]
    )
    suspend fun getListUsers(@RequestParam(name = "page") nextPage: Long = 0) = coroutineScope {
        userService.getListUser(
            getPagingRequest {
                page = nextPage
            }
        )
    }

    @PostMapping(
        value = ["upload-image"],
        produces = ["application/json"]
    )
    suspend fun uploadImageUser() = coroutineScope {  }

    @PostMapping(
        value = ["add-user"],
        produces = ["application/json"]
    )
    suspend fun addUserByAdmin(@RequestBody request: CreateUserByAdminRequest) = coroutineScope {
        userService.addUserByAdmin(request)
    }

    @PostMapping(
        value = ["assign-roles"],
        produces = ["application/json"]
    )
    suspend fun assignToleUser(@RequestBody request:AssignRoleRequest) = coroutineScope {
        userService.assignRoleUser(request)
    }

    @PutMapping(
        value = ["update-user"],
        produces = ["application/json"]
    )
    suspend fun updateUser(@RequestBody request: UpdateUserRequest)= coroutineScope {
        userService.updateUser(request)
    }

    @DeleteMapping(
        value = ["delete-user/{userId}"],
        produces = ["application/json"]
    )
    suspend fun deleteUser(@RequestParam(name = "userId") paramUserId:String) = coroutineScope {
        userService.deleteUser(
            deleteUserRequest {
                userId = paramUserId
            }
        )
    }


}