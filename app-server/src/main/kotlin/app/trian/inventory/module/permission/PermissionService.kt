package app.trian.inventory.module.permission

import app.trian.inventory.module.error.DataNotFound
import app.trian.inventory.v1.GetPagingRequest
import app.trian.inventory.v1.permission.GetListPermissionResponse
import app.trian.inventory.v1.permission.getListPermissionResponse
import app.trian.inventory.v1.permission.permissionResponse
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class PermissionService(
    private val permissionRepository: PermissionRepository
) {
     suspend fun getListPermission(request: GetPagingRequest): GetListPermissionResponse {
        val datas = permissionRepository.findAll(
            PageRequest.of(
                request.page.toInt(),
                50
            )
        )
        if(datas.isEmpty) throw DataNotFound("Tidak ditemukan satupun!")

        return getListPermissionResponse {
            totalItem = datas.totalElements
            totalPage = datas.totalPages.toLong()
            data += datas.content.map {
                permissionResponse {
                    permissionId = it.id.orEmpty()
                    permissionName = it.permissionName.orEmpty()
                    permissionGroup = it.permissionGroup.orEmpty()
                    permissionType = it.permissionType.orEmpty()
                    createdAt = it.createdAt.toString()
                    updatedAt = it.updatedAt.toString()
                }
            }
            currentPage = datas.number.toLong()
        }
    }
}