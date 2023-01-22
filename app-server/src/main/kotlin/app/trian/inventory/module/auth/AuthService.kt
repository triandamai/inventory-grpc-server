package app.trian.inventory.module.auth

import app.trian.inventory.module.error.UnAuthorized
import app.trian.inventory.module.user.UserRepository
import app.trian.inventory.stub.*
import net.devh.boot.grpc.server.service.GrpcService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@GrpcService
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder:BCryptPasswordEncoder
) : AuthenticationGrpcKt.AuthenticationCoroutineImplBase() {

    override suspend fun signInWithEmailAndPassword(request: SignInRequest): SignInResponse {
        val userData = userRepository.findTopByUserEmail(request.email) ?:
        throw UnAuthorized("Cannot find user with email ${request.email}")


        val match = passwordEncoder.matches(request.password,userData.userPassword)
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