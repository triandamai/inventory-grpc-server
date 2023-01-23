package app.trian.inventory.module.role

import app.trian.inventory.v1.getPagingRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class RoleController(
    private val roleService: RoleService
) {
    @GetMapping(
        value = ["/api/v1/roles"],
        produces = ["application/json"]
    )
    fun getListRole(
        @RequestParam(name = "page") nextPage:Long =0
    ) = roleService.getListRole(
        getPagingRequest {
            page =nextPage
        }
    )
}