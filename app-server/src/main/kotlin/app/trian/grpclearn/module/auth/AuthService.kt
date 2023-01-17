package app.trian.grpclearn.module.auth

import app.trian.grpclearn.module.error.UnAuthorized
import app.trian.grpclearn.module.user.UserRepository
import app.trian.model.AuthenticationGrpc
import app.trian.model.RolesResponse
import app.trian.model.SignInRequest
import app.trian.model.SignInResponse
import app.trian.model.UserResponse
import io.grpc.stub.StreamObserver
import net.devh.boot.grpc.server.service.GrpcService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.transaction.annotation.Transactional

@GrpcService
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder:BCryptPasswordEncoder
) : AuthenticationGrpc.AuthenticationImplBase() {
    @Transactional
    override fun signInBasic(
        request: SignInRequest,
        responseObserver: StreamObserver<SignInResponse>
    ) {
        val user = userRepository.findTopByEmail(request.email) ?:
        throw UnAuthorized("Cannot find user with email ${request.email}")


        val match = passwordEncoder.matches(request.password,user.password)
        if (!match) throw UnAuthorized("Email or password didn't match to any account!")

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
                        .setUsername(user.username)
                        .setCreatedAt(user.createdAt)
                        .setUpdatedAt(user.updatedAt)
                        .build()
                )
                .addAllRoles(user.roles.map {
                    RolesResponse.newBuilder()
                        .setId(it.id?.toLong() ?: 0)
                        .setName(it.name)
                        .setDescription(it.description)
                        .setCreatedAt(it.createdAt)
                        .setUpdatedAt(it.updatedAt)
                        .build()
                })
                .build()
        )
        responseObserver.onCompleted()

    }
}