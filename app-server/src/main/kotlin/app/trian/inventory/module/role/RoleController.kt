package app.trian.inventory.module.role

import app.trian.inventory.v1.getById
import app.trian.inventory.v1.getPagingRequest
import app.trian.inventory.v1.role.CreateRoleRequest
import app.trian.inventory.v1.role.UpdateRoleRequest
import app.trian.inventory.v1.role.copy
import app.trian.inventory.v1.role.deleteRoleRequest
import kotlinx.coroutines.coroutineScope
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(
    value = ["/api/v1"]
)
class RoleController(
    private val roleService: RoleService
) {
    @GetMapping(
        value = ["/roles"],
        produces = ["application/json"]
    )
    suspend fun getListRole(
        @RequestParam(name = "page") nextPage:Long =0
    ) = coroutineScope {
        roleService.getListRole(
            getPagingRequest {
                page = nextPage
            }
        )
    }

    @GetMapping(
        value = ["/role/{roleId}"],
        produces = ["application/json"]
    )
    suspend fun getDetailRoleById(@PathVariable(name = "roleId") roleId:String ="") = coroutineScope {
        roleService.getDetailRoleById(
            getById {
                resourceId = roleId
            }
        )
    }

    @PostMapping(
        value = ["/role/create"],
        consumes = ["application/json"],
        produces = ["application/json"]
    )
    suspend fun createNewRole(
        @RequestBody request:CreateRoleRequest
    ) = coroutineScope {
        roleService.createNewRole(request)
    }

    @PutMapping(
        value = ["/role"],
        consumes = ["application/json"],
        produces = ["application/json"]
    )
    suspend fun updateRole(
        @RequestBody request: UpdateRoleRequest
    )= coroutineScope {
        roleService.updateRole(
            request
        )
    }

    @DeleteMapping(
        value = ["/role/{roleId}"],
        produces = ["application/json"]
    )
    suspend fun deleteRole(
        @PathVariable(name = "roleId") roleIdReq: String=""
    ) = coroutineScope {
        roleService.deleteRole(deleteRoleRequest {
            roleId = roleIdReq
        })
    }
}