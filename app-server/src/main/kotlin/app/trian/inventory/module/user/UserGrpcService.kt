package app.trian.inventory.module.user


import app.trian.inventory.v1.user.*
import kotlinx.coroutines.flow.Flow
import net.devh.boot.grpc.server.service.GrpcService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable

@GrpcService
class UserGrpcService(
    private val userRepository: UserRepository
):UserGrpcKt.UserCoroutineImplBase(){

    override suspend fun getListUser(request: GetListUserRequest): GetListUserResponse {
        val dataUsers = userRepository.findAll(
            PageRequest.of(
                request.page.toInt(),
                50
            )
        )

        return getListUserResponse {
            totalItem = dataUsers.totalElements
            totalPage = dataUsers.totalPages.toLong()
            data += dataUsers.content.map {
                userResponse {
                    userId = it.id.orEmpty()
                    userFullName = it.userFullName
                    userEmail = it.userEmail
                    authProvider = it.authProvider
                    createdAt = it.createdAt.toString()
                    updatedAt = it.updatedAt.toString()

                }
            }
            currentPage = dataUsers.number.toLong()
        }
    }

    override suspend fun uploadImageUser(requests: Flow<UserImageUploadRequest>): UserImageUploadResponse {
        return super.uploadImageUser(requests)
    }
}