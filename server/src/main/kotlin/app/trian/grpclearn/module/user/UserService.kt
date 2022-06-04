package app.trian.grpclearn.module.user

import app.trian.grpclearn.module.error.DataNotFound
import app.trian.model.*
import com.google.protobuf.Empty
import io.grpc.stub.StreamObserver
import net.devh.boot.grpc.server.service.GrpcService
import org.springframework.data.repository.findByIdOrNull

@GrpcService
class UserService(
    private val userRepository: UserRepository
) :UserServiceGrpc.UserServiceImplBase(){
    override fun getListUser(request: Empty, responseObserver: StreamObserver<ListUserResponse>) {
        val user = userRepository.findAll()
        responseObserver.onNext(
            ListUserResponse.newBuilder()
                .addAllUsers(
                    user.map {
                        UserResponse.newBuilder()
                            .setId(it.id?.toLong() ?: 0)
                            .setEmail(it.email)
                            .setName(it.name)
                            .setAuthProvider(it.auth_provider)
                            .setUsername(it.username)
                        .build()
                    }
                ).build()
        )

        responseObserver.onCompleted()
    }

    override fun createUser(request: CreateUserRequest, responseObserver: StreamObserver<UserResponse>) {
        val user = User(
            id=null,
            name = request.name,
            email = request.email,
            password = request.password,
            username = request.username,
            auth_provider = "",
        )

        val saved = userRepository.save(user)

        responseObserver.onNext(
            UserResponse.newBuilder()
                .setName(saved.name)
                .setUsername(saved.username)
                .setEmail(saved.email)
                .setId(saved.id?.toLong() ?: 0)
                .setAuthProvider(saved.auth_provider)
                .build()
        )
        responseObserver.onCompleted()
    }

    override fun updateUser(request: UpdateUserRequest, responseObserver: StreamObserver<UserResponse>) {
        val user = userRepository.findByIdOrNull(request.id.toInt()) ?:
        throw DataNotFound("User with id ${request.id} doesn't exist!")

        val saved = user.copy(
            name = request.name,
            username = request.username,
        )
        val userSaved = userRepository.save(saved)
        responseObserver.onNext(
            UserResponse.newBuilder()
                .setEmail(userSaved.email)
                .setName(userSaved.name)
                .setAuthProvider(userSaved.auth_provider)
                .setUsername(userSaved.username)
                .build()
        )
        responseObserver.onCompleted()
    }

    override fun deleteUser(request: DeleteUserRequest, responseObserver: StreamObserver<UserResponse>) {
        val findUser = userRepository.findByIdOrNull(request.id.toInt()) ?:
        throw DataNotFound("User with id ${request.id} not found!")


        userRepository.deleteById(request.id.toInt())
        responseObserver.onNext(
            UserResponse.newBuilder()
                .setEmail(findUser.email)
                .setName(findUser.name)
                .setAuthProvider(findUser.auth_provider)
                .setUsername(findUser.username)
                .build()
        )
        responseObserver.onCompleted()
    }
}