package app.trian.inventory.module.user


import app.trian.inventory.v1.GetById
import app.trian.inventory.v1.GetPagingRequest
import app.trian.inventory.v1.user.AssignRoleRequest
import app.trian.inventory.v1.user.CreateUserByAdminRequest
import app.trian.inventory.v1.user.CreateUserRequest
import app.trian.inventory.v1.user.DeleteUserRequest
import app.trian.inventory.v1.user.GetListUserResponse
import app.trian.inventory.v1.user.UpdateUserRequest
import app.trian.inventory.v1.user.UserGrpcKt
import app.trian.inventory.v1.user.UserImageUploadRequest
import app.trian.inventory.v1.user.UserImageUploadResponse
import app.trian.inventory.v1.user.UserResponse
import kotlinx.coroutines.flow.Flow
import net.devh.boot.grpc.server.service.GrpcService

@GrpcService
class UserGrpcService(
    private val userService: UserService
):UserGrpcKt.UserCoroutineImplBase(){


    override suspend fun getListUser(request: GetPagingRequest): GetListUserResponse = userService.getListUser(request)

    override suspend fun getUserBydId(request: GetById): UserResponse =
        userService.getUserBydId(request)
    override suspend fun uploadImageUser(requests: Flow<UserImageUploadRequest>): UserImageUploadResponse {
        return super.uploadImageUser(requests)
    }

    override suspend fun addUserByAdmin(request: CreateUserByAdminRequest): UserResponse = userService.addUserByAdmin(request)

    override suspend fun assignRoleUser(request: AssignRoleRequest): UserResponse = userService.assignRoleUser(request)

    override suspend fun updateUser(request: UpdateUserRequest): UserResponse = userService.updateUser(request)

    override suspend fun deleteUser(request: DeleteUserRequest): UserResponse = userService.deleteUser(request)



}