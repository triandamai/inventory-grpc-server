package app.trian.grpclearn.module.auth

import app.trian.grpclearn.module.user.UserRepository
import app.trian.model.AuthenticationGrpc
import app.trian.model.RolesResponse
import app.trian.model.SignInRequest
import app.trian.model.SignInResponse
import app.trian.model.UserResponse
import io.grpc.stub.StreamObserver
import net.devh.boot.grpc.server.service.GrpcService
import org.springframework.transaction.annotation.Transactional

@GrpcService
class AuthService(
    private val userRepository: UserRepository
) : AuthenticationGrpc.AuthenticationImplBase() {
    @Transactional
    override fun signInBasic(
        request: SignInRequest,
        responseObserver: StreamObserver<SignInResponse>
    ) {
        val user = userRepository.findTopByEmail(request.email)
        if (user == null) {
            responseObserver.onNext(
                SignInResponse.newBuilder()
                    .setSuccess(false)
                    .setMessage("Cannot find user with email ${request.email}")
                    .build()
            )
            responseObserver.onCompleted()
            return
        }
        if (user.password != request.password) {
            responseObserver.onNext(
                SignInResponse.newBuilder()
                    .setSuccess(false)
                    .setMessage("Email or password didn't match to any account!")
                    .build()
            )
            responseObserver.onCompleted()
            return
        }
        responseObserver.onNext(
            SignInResponse.newBuilder()
                .setSuccess(true)
                .setMessage("Success!")
                .setUsers(
                    UserResponse.newBuilder()
                        .setId(user.id?.toLong() ?: 0)
                        .setName(user.name)
                        .setAuthProvider(user.auth_provider)
                        .setEmail(user.email)
                        .build()
                )
                .addAllRoles(user.roles.map {
                    RolesResponse.newBuilder()
                        .setId(it.id?.toLong() ?: 0)
                        .setName(it.name)
                        .setDescription(it.description)
                        .build()
                })
                .build()
        )
        responseObserver.onCompleted()

    }
}