package app.trian.inventory.module.user


import app.trian.inventory.module.error.DataNotFound
import app.trian.inventory.v1.user.*
import kotlinx.coroutines.flow.Flow
import net.devh.boot.grpc.server.service.GrpcService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import java.util.Date

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


    override suspend fun deleteUser(request: DeleteUserRequest): UserResponse {
        val findUser = userRepository.findByIdOrNull(request.userId)?:
        throw DataNotFound("cannot find user ${request.userId}")

        findUser.id.let {
            userRepository.deleteById(it.orEmpty())
        }

        return userResponse {
            userId = findUser.id.orEmpty()
            userFullName = findUser.userFullName
            userEmail = findUser.userEmail
            authProvider = findUser.authProvider
            createdAt = findUser.createdAt.toString()
            updatedAt = findUser.updatedAt.toString()
        }
    }

    override suspend fun updateUser(request: UpdateUserRequest): UserResponse {
        val findUser =  userRepository.findByIdOrNull(request.userId)?:
        throw DataNotFound("cannot find ${request.userId}")

        val updateUser = with(request){
            findUser.copy(
                userFullName = if (userFullName.isNullOrEmpty()) findUser.userFullName else userFullName,
                userEmail = if (userEmail.isNullOrEmpty()) findUser.userEmail else userEmail,
                updatedAt = Date()
            )
        }

        val saveUpdate = userRepository.save(updateUser)

        return userResponse {
            userId = saveUpdate.id.orEmpty()
            userFullName = saveUpdate.userFullName
            userEmail = saveUpdate.userEmail
            authProvider = saveUpdate.authProvider
            createdAt = saveUpdate.createdAt.toString()
            updatedAt = saveUpdate.updatedAt.toString()
        }
    }

}