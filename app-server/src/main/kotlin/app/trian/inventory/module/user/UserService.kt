package app.trian.inventory.module.user

import app.trian.inventory.module.error.DataExist
import app.trian.inventory.module.error.DataNotFound
import app.trian.inventory.module.role.RoleRepository
import app.trian.inventory.v1.role.roleResponse
import app.trian.inventory.v1.user.AssignRoleRequest
import app.trian.inventory.v1.user.CreateUserRequest
import app.trian.inventory.v1.user.DeleteUserRequest
import app.trian.inventory.v1.user.GetListUserRequest
import app.trian.inventory.v1.user.GetListUserResponse
import app.trian.inventory.v1.user.UpdateUserRequest
import app.trian.inventory.v1.user.UserImageUploadRequest
import app.trian.inventory.v1.user.UserImageUploadResponse
import app.trian.inventory.v1.user.UserResponse
import app.trian.inventory.v1.user.getListUserResponse
import app.trian.inventory.v1.user.userImageUploadResponse
import app.trian.inventory.v1.user.userResponse
import kotlinx.coroutines.flow.Flow
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val passwordEncoder: BCryptPasswordEncoder
) {
     fun getListUser(request: GetListUserRequest): GetListUserResponse {
        val dataUsers = userRepository.findAll(
            PageRequest.of(
                request.page.toInt(),
                50
            )
        )
         if(dataUsers.isEmpty) throw DataNotFound("Tidak ditemukan satupun data")

        return getListUserResponse {
            totalItem = 0// dataUsers.totalElements
            totalPage = dataUsers.totalPages.toLong()
            data += dataUsers.content.map {
                userResponse {
                    userId = it.id.orEmpty()
                    userFullName = it.userFullName.orEmpty()
                    userEmail = it.userEmail.orEmpty()
                    authProvider = it.authProvider.orEmpty()
                    roles += it.roles.map {
                        roleResponse {
                            roleId = it.id.orEmpty()
                            roleName = it.roleName.orEmpty()
                            roleDescription = it.roleDescription.orEmpty()
                            createdAt = it.createdAt.toString()
                            updatedAt = it.updatedAt.toString()
                        }
                    }
                    createdAt = it.createdAt.toString()
                    updatedAt = it.updatedAt.toString()

                }
            }
            currentPage = dataUsers.number.toLong()
        }
    }

     fun uploadImageUser(requests: Flow<UserImageUploadRequest>): UserImageUploadResponse {
        return userImageUploadResponse {  }
    }

      fun addUserByAdmin(request: CreateUserRequest): UserResponse {
        val findUserByEmail = userRepository.findTopByUserEmail(request.email)

        if(findUserByEmail != null){
            throw DataExist("Email ${request.email} sudah terdaftar silahkan gunakan email lain!")
        }

        val dataPayload = User()
        val savedData = userRepository.save(
            dataPayload.copy(
                id = null,
                userEmail = request.email,
                userPassword = passwordEncoder.encode(request.password),
                userFullName = request.fullName,
                createdAt = Date(),
                updatedAt = Date()
            )
        )
        return userResponse {
            userId = savedData.id.orEmpty()
            userFullName = savedData.userFullName.orEmpty()
            userEmail = savedData.userEmail.orEmpty()
            createdAt = savedData.createdAt.toString()
            updatedAt = savedData.updatedAt.toString()
            roles += savedData.roles.map {
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

      fun assignRoleUser(request: AssignRoleRequest): UserResponse {
        val findUser = userRepository.findByIdOrNull(request.userId) ?:
        throw DataNotFound("Tidak dapat menemukan user yang di assign")

        val finRoles = roleRepository.findAllById(
            request.rolesList
        )

        val updateData = findUser.copy(
            roles = finRoles.toList()
        )

        val savedData = userRepository.save(updateData)

        return userResponse {
            userId=savedData.id.orEmpty()
            userFullName = savedData.userFullName.orEmpty()
            userEmail = savedData.userEmail.orEmpty()
            roles += savedData.roles.map {
                roleResponse {
                    roleId = it.id.orEmpty()
                    roleName = it.roleName.orEmpty()
                    roleDescription = it.roleDescription.orEmpty()
                    createdAt = it.createdAt.toString()
                    updatedAt = it.updatedAt.toString()
                }
            }
            createdAt = savedData.createdAt.toString()
            updatedAt = savedData.updatedAt.toString()
        }
    }

    fun deleteUser(request: DeleteUserRequest): UserResponse {
        val findUser = userRepository.findByIdOrNull(request.userId)?:
        throw DataNotFound("cannot find user ${request.userId}")

        findUser.id.let {
            userRepository.deleteById(it.orEmpty())
        }

        return userResponse {
            userId = findUser.id.orEmpty()
            userFullName = findUser.userFullName.orEmpty()
            userEmail = findUser.userEmail.orEmpty()
            authProvider = findUser.authProvider.orEmpty()
            roles += findUser.roles.map {
                roleResponse {
                    roleId = it.id.orEmpty()
                    roleName = it.roleName.orEmpty()
                    roleDescription = it.roleDescription.orEmpty()
                    createdAt = it.createdAt.toString()
                    updatedAt = it.updatedAt.toString()
                }
            }
            createdAt = findUser.createdAt.toString()
            updatedAt = findUser.updatedAt.toString()
        }
    }

    fun updateUser(request: UpdateUserRequest): UserResponse {
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
            userFullName = saveUpdate.userFullName.orEmpty()
            userEmail = saveUpdate.userEmail.orEmpty()
            authProvider = saveUpdate.authProvider.orEmpty()
            roles += findUser.roles.map {
                roleResponse {
                    roleId = it.id.orEmpty()
                    roleName = it.roleName.orEmpty()
                    roleDescription = it.roleDescription.orEmpty()
                    createdAt = it.createdAt.toString()
                    updatedAt = it.updatedAt.toString()
                }
            }
            createdAt = saveUpdate.createdAt.toString()
            updatedAt = saveUpdate.updatedAt.toString()
        }
    }
}