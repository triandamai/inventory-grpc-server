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
    private val roleRepository: RoleRepository
):RoleGrpcKt.RoleCoroutineImplBase() {

    override suspend fun getListRole(request: GetPagingRequest): GetListRoleResponse {
        val findData = roleRepository.findAll(PageRequest.of(
            request.page.toInt(),
            50
        ))
        return getListRoleResponse {
            totalItem = findData.totalElements
            totalPage = findData.totalPages.toLong()
            data += findData.content.map {
                roleResponse {
                    roleId = it.id.orEmpty()
                    roleName = it.roleName.orEmpty()
                    roleDescription = it.roleDescription.orEmpty()
                    createdAt = it.createdAt.toString()
                    updatedAt = it.updatedAt.toString()
                }
            }
            currentPage = findData.number.toLong()
        }
    }

    override suspend fun createNewRole(request: CreateRoleRequest): RoleResponse {
        val date = Date()
        val payload = Role()
        val savedData = roleRepository.save(
            payload.copy(
                roleName = request.roleName,
                roleDescription = request.roleDescription,
                createdAt = date,
                updatedAt = date,
                id = null
            )
        )

        return roleResponse {
            roleId = savedData.id.orEmpty()
            roleName = savedData.roleName.orEmpty()
            roleDescription = savedData.roleDescription.orEmpty()
            createdAt = savedData.createdAt.toString()
            updatedAt = savedData.updatedAt.toString()
        }
    }

    override suspend fun updateRole(request: UpdateRoleRequest): RoleResponse {
        val findRole = roleRepository.findByIdOrNull(request.roleId) ?: throw DataNotFound(
            "Role yang akan di update tidak ditemukan atau sudah dihapus"
        )

        val requestUpdateData = with(request) {
            findRole.copy(
                roleName = if (roleName.isNullOrEmpty()) findRole.roleName else roleName,
                roleDescription = if (roleDescription.isNullOrEmpty()) findRole.roleDescription else roleDescription,
                updatedAt = Date()
            )
        }

        val saveData = roleRepository.save(requestUpdateData)

        return roleResponse {
            roleId = saveData.id.orEmpty()
            roleName = saveData.roleName.orEmpty()
            roleDescription = saveData.roleDescription.orEmpty()
            createdAt = saveData.createdAt.toString()
            updatedAt = saveData.updatedAt.toString()
        }
    }
}