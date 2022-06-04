package app.trian.grpclearn.service


import app.trian.model.AuthenticationGrpc
import app.trian.model.SignInRequest
import app.trian.model.SignInResponse
import app.trian.model.User
import app.trian.model.UserResponse
import io.grpc.stub.StreamObserver
import net.devh.boot.grpc.server.service.GrpcService
import java.time.LocalDate

@GrpcService
class AuthService:AuthenticationGrpc.AuthenticationImplBase(){
    override fun signIn(request: SignInRequest, responseObserver: StreamObserver<SignInResponse>) {

        if(request.email.isBlank() || request.password.isBlank()){
            val base = SignInResponse.newBuilder()
                .setSuccess(false)
                .build()
            responseObserver.onNext(base)
            responseObserver.onCompleted()
            return
        }

        val user = UserResponse.newBuilder()
            .setName("Trian")
            .setEmail(request.email)
            .setPassword(request.password)
            .setUsername("Damai")
            .setCreatedAt(LocalDate.now().toString())
            .setUpdatedAt(LocalDate.now().toString())
            .build()

        val base = SignInResponse.newBuilder()
            .setSuccess(true)
           .setUsers(user)
            .build()
        responseObserver.onNext(base)
        responseObserver.onCompleted()
    }
}