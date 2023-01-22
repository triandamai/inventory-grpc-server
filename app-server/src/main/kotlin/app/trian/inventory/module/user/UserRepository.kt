package app.trian.inventory.module.user

import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Int> {
    fun findTopByUserEmail(userEmail:String): User?
}