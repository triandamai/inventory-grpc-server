package app.trian.inventory.module.role

import app.trian.inventory.v1.role.RoleGrpcKt
import net.devh.boot.grpc.server.service.GrpcService

@GrpcService
class RoleGrpcService(
    private val roleRepository: RoleRepository
):RoleGrpcKt.RoleCoroutineImplBase() {
}