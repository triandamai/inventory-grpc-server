package app.trian.inventory.module.auth


import app.trian.inventory.module.error.UnAuthorized
import app.trian.inventory.module.user.UserRepository
import app.trian.inventory.v1.authentication.AuthenticationGrpcKt
import app.trian.inventory.v1.authentication.SignInRequest
import app.trian.inventory.v1.authentication.SignInResponse
import app.trian.inventory.v1.authentication.signInResponse
import app.trian.inventory.v1.user.userResponse
import net.devh.boot.grpc.server.service.GrpcService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@GrpcService
class AuthenticationGrpcService(
    private val authenticationService: AuthenticationService
):AuthenticationGrpcKt.AuthenticationCoroutineImplBase(){

    override suspend fun signInWithEmailAndPassword(request: SignInRequest): SignInResponse = authenticationService.signInWithEmailAndPassword(request)

    override suspend fun signInWithGoogle(request: SignInRequest): SignInResponse =authenticationService.signInWithGoogle(request)

}