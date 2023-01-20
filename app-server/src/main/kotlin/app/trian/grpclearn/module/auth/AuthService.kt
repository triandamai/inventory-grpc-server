package app.trian.grpclearn.module.auth

import app.trian.grpclearn.module.error.UnAuthorized
import app.trian.grpclearn.module.user.UserRepository
import app.trian.model.*
import io.grpc.stub.StreamObserver
import net.devh.boot.grpc.server.service.GrpcService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.transaction.annotation.Transactional

@GrpcService
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder:BCryptPasswordEncoder
) : AuthenticationGrpcKt.AuthenticationCoroutineImplBase() {
    override suspend fun signInBasic(request: SignInRequest): SignInResponse {
        val user = userRepository.findTopByEmail(request.email) ?:
        throw UnAuthorized("Cannot find user with email ${request.email}")


        val match = passwordEncoder.matches(request.password,user.password)
        if (!match) throw UnAuthorized("Email or password didn't match to any account!")

        return signInResponse {
            success = true
            message = ""
            users = userResponse {
                id = user?.id?.toLong() ?: 0
                name = user.name
            }
            roles  += rolesResponse {

            }
        }

    }

}