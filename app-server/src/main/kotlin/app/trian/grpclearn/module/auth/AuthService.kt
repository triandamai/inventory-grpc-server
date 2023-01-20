package app.trian.grpclearn.module.auth

import app.trian.grpclearn.module.error.UnAuthorized
import app.trian.grpclearn.module.user.UserRepository
import app.trian.inventory.stub.*
import net.devh.boot.grpc.server.service.GrpcService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@GrpcService
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder:BCryptPasswordEncoder
) : AuthenticationGrpcKt.AuthenticationCoroutineImplBase() {

    override suspend fun signInWithEmailAndPassword(request: SignInRequest): SignInResponse {
        val userData = userRepository.findTopByEmail(request.email) ?:
        throw UnAuthorized("Cannot find user with email ${request.email}")


        val match = passwordEncoder.matches(request.password,userData.password)
        if (!match) throw UnAuthorized("Email or password didn't match to any account!")

        return signInResponse {
            success = true
            message = ""
            user = userResponse {

            }
            roles  += roleResponse {

            }
        }
    }

}