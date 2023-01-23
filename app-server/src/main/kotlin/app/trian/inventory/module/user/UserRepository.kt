package app.trian.inventory.module.user

import org.springframework.data.repository.PagingAndSortingRepository

interface UserRepository : PagingAndSortingRepository<User, String> {
    fun findTopByUserEmail(userEmail:String): User?
}