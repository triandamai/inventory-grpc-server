package app.trian.inventory.module.auth

import app.trian.inventory.module.error.UnAuthorized
import app.trian.inventory.module.user.UserRepository
import app.trian.inventory.v1.authentication.SignInRequest
import app.trian.inventory.v1.authentication.SignInResponse
import app.trian.inventory.v1.authentication.signInResponse
import app.trian.inventory.v1.role.roleResponse
import app.trian.inventory.v1.user.userResponse
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthenticationService(
    private val userRepository: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder
) {
    suspend fun signInWithEmailAndPassword(request: SignInRequest): SignInResponse {
        val userData = userRepository.findTopByUserEmail(request.email)
            ?: throw UnAuthorized("Cannot find user with email ${request.email}")

        if(userData.authProvider == "GOOGLE") throw UnAuthorized("Email sudah tertaut dengan akun lain!")

        val match = passwordEncoder.matches(request.password,userData.userPassword)
        if (!match) throw UnAuthorized("Email or password didn't match to any account!")

        return signInResponse {
            success = true
            message= "Login success"
            user = userResponse {
                userId = userData.id.orEmpty()
                userFullName = userData.userFullName.orEmpty()
                userEmail = userData.userEmail.orEmpty()
                createdAt = userData.createdAt.toString()
                updatedAt = userData.updatedAt.toString()
                roles += userData.roles.map {
                    roleResponse {
                        roleId = it.id.orEmpty()
                        roleName = it.roleName.orEmpty()
                        roleDescription = it.roleDescription.orEmpty()
                        createdAt = it.createdAt.toString()
                        updatedAt = it.updatedAt.toString()
                    }
                }
            }
        }
    }

    suspend fun signInWithGoogle(request: SignInRequest): SignInResponse {
        //
        return signInResponse {

        }
    }
}