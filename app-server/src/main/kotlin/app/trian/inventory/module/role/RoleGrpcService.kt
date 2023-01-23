package app.trian.inventory.module.role

import app.trian.inventory.module.error.DataNotFound
import app.trian.inventory.v1.GetPagingRequest
import app.trian.inventory.v1.role.*
import net.devh.boot.grpc.server.service.GrpcService
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import java.util.*

@GrpcService
class RoleGrpcService(
    private val roleService: RoleService
) : RoleGrpcKt.RoleCoroutineImplBase() {

    override suspend fun getListRole(request: GetPagingRequest): GetListRoleResponse = roleService.getListRole(request)

    override suspend fun createNewRole(request: CreateRoleRequest): RoleResponse = roleService.createNewRole(request)

    override suspend fun updateRole(request: UpdateRoleRequest): RoleResponse = roleService.updateRole(request)

    override suspend fun deleteRole(request: DeleteRoleRequest): RoleResponse = roleService.deleteRole(request)
}
