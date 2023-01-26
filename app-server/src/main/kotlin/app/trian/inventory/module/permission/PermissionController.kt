package app.trian.inventory.module.permission

import app.trian.inventory.v1.getPagingRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(
    value = ["/api/v1"]
)
class PermissionController(
    private val permissionService: PermissionService
) {

    @GetMapping(
        value = ["/permission"],
        produces = ["application/json"]
    )
    suspend fun getListPermission(
        @RequestParam(
            name = "page"
        )
        nextPage:Long=0
    ) = permissionService.getListPermission(
        getPagingRequest {
            page = nextPage
        }
    )
}