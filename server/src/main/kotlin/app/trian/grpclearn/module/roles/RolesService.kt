package app.trian.grpclearn.module.roles

import app.trian.model.RolesServiceGrpc
import net.devh.boot.grpc.server.service.GrpcService

@GrpcService
class RolesService(
    private val rolesRepository: RolesRepository
) :RolesServiceGrpc.RolesServiceImplBase(){
}