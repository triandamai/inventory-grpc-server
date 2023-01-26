package app.trian.inventory.module.permission

import app.trian.inventory.module.error.DataNotFound
import app.trian.inventory.v1.GetPagingRequest
import app.trian.inventory.v1.permission.GetListPermissionResponse
import app.trian.inventory.v1.permission.PermissionGrpcKt
import app.trian.inventory.v1.permission.getListPermissionResponse
import app.trian.inventory.v1.permission.permissionResponse
import net.devh.boot.grpc.server.service.GrpcService
import org.springframework.data.domain.PageRequest

@GrpcService
class PermissionGrpcService(
    private val permissionService: PermissionService
) : PermissionGrpcKt.PermissionCoroutineImplBase() {
    override suspend fun getListPermission(request: GetPagingRequest): GetListPermissionResponse =
        permissionService.getListPermission(request)
}