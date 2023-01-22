package app.trian.inventory.module.user


import app.trian.inventory.v1.user.UserGrpcKt
import net.devh.boot.grpc.server.service.GrpcService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable

@GrpcService
class UserGrpcService(
    private val userRepository: UserRepository
):UserGrpcKt.UserCoroutineImplBase(){

}