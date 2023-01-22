package app.trian.inventory.module.role

import app.trian.inventory.v1.role.CreateRoleRequest
import app.trian.inventory.v1.role.RoleGrpcKt
import app.trian.inventory.v1.role.RoleResponse
import app.trian.inventory.v1.role.roleResponse
import net.devh.boot.grpc.server.service.GrpcService
import java.util.*

@GrpcService
class RoleGrpcService(
    private val roleRepository: RoleRepository
):RoleGrpcKt.RoleCoroutineImplBase() {

    override suspend fun createNewRole(request: CreateRoleRequest): RoleResponse {
        val date = Date()
        val savedData = roleRepository.save(Role(
            roleName = request.roleName,
            roleDescription = request.roleDescription,
            createdAt = date,
            updatedAt = date,
            id = null
        ))

        return roleResponse {
            roleName = savedData.roleName
            roleDescription = savedData.roleDescription
            createdAt = savedData.createdAt.toString()
            updatedAt = savedData.updatedAt.toString()
        }
    }
}