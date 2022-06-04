package app.trian.grpclearn.module.user

import app.trian.model.UserServiceGrpc
import net.devh.boot.grpc.server.service.GrpcService

@GrpcService
class UserService(
    private val userRepository: UserRepository
) :UserServiceGrpc.UserServiceImplBase(){
}