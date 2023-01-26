package app.trian.inventory.config

import app.trian.inventory.module.permission.Permission
import app.trian.inventory.module.permission.PermissionRepository
import app.trian.inventory.module.role.Role
import app.trian.inventory.module.role.RoleRepository
import app.trian.inventory.module.user.User
import app.trian.inventory.module.user.UserRepository
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.core.annotation.Order
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import java.util.Date
import java.util.UUID
import javax.transaction.Transactional

@Component
@Order(1)
class Seeder(
    private val permissionRepository: PermissionRepository,
    private val roleRepository: RoleRepository,
    private val userRepository: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder
) : ApplicationRunner {

    @Transactional
    override fun run(args: ApplicationArguments?) {
        val date = Date()

        val permissions = listOf(
            Permission(
                id = "f56917a2-279c-4ad3-8db2-8fc1e54e2be3",
                permissionName = "Manage User",
                permissionType = "WRITE",
                permissionGroup = "inventory.user",
                createdAt = date,
                updatedAt = date
            ),
            Permission(
                id = "da8bb576-6966-4fb1-88d7-11f0258389ab",
                permissionName = "Manage User",
                permissionType = "READ",
                permissionGroup = "inventory.user",
                createdAt = date,
                updatedAt = date
            ),
            Permission(
                id = "152e22bf-f2b7-4788-8f54-8b951341bd85",
                permissionName = "Manage Role",
                permissionType = "WRITE",
                permissionGroup = "inventory.role",
                createdAt = date,
                updatedAt = date
            ),
            Permission(
                id = "08f6e968-a60a-4072-9752-b78fcb9ca736",
                permissionName = "Manage Role",
                permissionType = "READ",
                permissionGroup = "inventory.role",
                createdAt = date,
                updatedAt = date
            ),
            Permission(
                id = "b46fe418-b71f-4e97-9975-ad1aedbcf845",
                permissionName = "Manage Supplier",
                permissionType = "WRITE",
                permissionGroup = "inventory.supplier",
                createdAt = date,
                updatedAt = date
            ),
            Permission(
                id = "0248a6c2-473e-4434-88b5-55853c66b448",
                permissionName = "Manage Supplier",
                permissionType = "READ",
                permissionGroup = "inventory.supplier",
                createdAt = date,
                updatedAt = date
            ),
            Permission(
                id = "f0d5c870-e0e3-4a3c-9f09-42235e3e2b83",
                permissionName = "Manage Customer",
                permissionType = "WRITE",
                permissionGroup = "inventory.customer",
                createdAt = date,
                updatedAt = date
            ),
            Permission(
                id = "3dba6309-bb5a-4cc5-ac0c-7a420a97c8f5",
                permissionName = "Manage Customer",
                permissionType = "READ",
                permissionGroup = "inventory.customer",
                createdAt = date,
                updatedAt = date
            ),
            Permission(
                id = "62673fe5-aaad-4acd-91ab-9bad5a7855d5",
                permissionName = "Manage Product",
                permissionType = "WRITE",
                permissionGroup = "inventory.product",
                createdAt = date,
                updatedAt = date
            ),
            Permission(
                id = "d93206c7-8581-4499-8374-ebafd01dfafd",
                permissionName = "Manage Product",
                permissionType = "READ",
                permissionGroup = "inventory.product",
                createdAt = date,
                updatedAt = date
            ),
            Permission(
                id = "85f177bb-668f-4bc8-ab67-a181eae0fd4f",
                permissionName = "Manage Category",
                permissionType = "WRITE",
                permissionGroup = "inventory.category",
                createdAt = date,
                updatedAt = date
            ),
            Permission(
                id = "e4a6b866-e5cf-4b34-b4fa-10323865b149",
                permissionName = "Manage Category",
                permissionType = "READ",
                permissionGroup = "inventory.category",
                createdAt = date,
                updatedAt = date
            ),
            Permission(
                id = "f94125c7-1a61-4602-8fdc-d99ac3c55e86",
                permissionName = "Manage Inbound",
                permissionType = "WRITE",
                permissionGroup = "inventory.inbound",
                createdAt = date,
                updatedAt = date
            ),
            Permission(
                id = "72783c79-02f3-469a-8bbc-a587b75d4e98",
                permissionName = "Manage Inbound",
                permissionType = "READ",
                permissionGroup = "inventory.inbound",
                createdAt = date,
                updatedAt = date
            ),
            Permission(
                id = "3d222694-1078-4080-a1f2-2696f0b1cc66",
                permissionName = "Manage Outbound",
                permissionType = "WRITE",
                permissionGroup = "inventory.outbound",
                createdAt = date,
                updatedAt = date
            ),
            Permission(
                id = "26ff6c62-a447-4e7f-941e-e3c866bd69be",
                permissionName = "Manage Outbound",
                permissionType = "READ",
                permissionGroup = "inventory.outbound",
                createdAt = date,
                updatedAt = date
            )
        )
        permissionRepository.saveAll(
            permissions
        )

        val permission = permissionRepository.findAll()

        val roleId = "26ff6c62-a447-4e7f-941e-e3c866bd69bf"
        val role = Role(
            id = roleId,
            roleName = "SUPER_ADMIN",
            roleDescription = "Khusus buat Super Admin",
            createdAt = date,
            updatedAt = date
        )
       val saved =  roleRepository.save(role)
        roleRepository.save(saved.copy(
            permissions  = permission.map { it },
        ))



        val user = User(
            id = "26ff6c62-a447-4e7f-941e-e3c866bd69bc",
            userFullName = "Trian Damai",
            userPassword = passwordEncoder.encode("admin123"),
            userEmail = "trian@gmail.com",
            authProvider = "EMAIL",
            createdAt = Date(),
            updatedAt = Date()
        )


        val savedUser = userRepository.save(user)
        userRepository.save(savedUser.copy(
            roles = listOf(saved)
        ))


    }
}