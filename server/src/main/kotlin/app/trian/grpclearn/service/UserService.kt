package app.trian.grpclearn.service

import app.trian.model.ListUser
import app.trian.model.User
import app.trian.model.UserResponse
import app.trian.model.UserServiceGrpc
import com.google.protobuf.Empty
import io.grpc.stub.StreamObserver
import net.devh.boot.grpc.server.service.GrpcService
import java.time.LocalDate

@GrpcService
class UserService:UserServiceGrpc.UserServiceImplBase() {
    override fun createNewUser(request: User, responseObserver: StreamObserver<UserResponse>) {
        responseObserver.onNext(
            UserResponse.newBuilder()
                .setCreatedAt(LocalDate.now().toString())
                .setUpdatedAt(LocalDate.now().toString())
                .setEmail(request.email)
                .setUsername(request.username)
                .setPassword(request.password)
                .setName(request.name)
                .build()
        )
        responseObserver.onCompleted()
    }

    override fun getListUser(request: Empty, responseObserver: StreamObserver<ListUser>) {
        val resp = ListUser.newBuilder()


        (0.. 10).forEach { i ->
            resp.addUsers(UserResponse.newBuilder()
                .setCreatedAt(LocalDate.now().toString())
                .setUpdatedAt(LocalDate.now().toString())
                .setEmail("Trian $i")
                .setUsername("Username")
                .setPassword("Password")
                .setName("hehe")
                .build())
        }


        responseObserver.onNext(resp.build())
        responseObserver.onCompleted()
    }
}