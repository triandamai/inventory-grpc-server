package app.trian.grpclearn.module.recipe;


import app.trian.grpclearn.module.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface RecipeRepository : JpaRepository<Recipe, Int> {
    fun findByUser(user: User):MutableList<Recipe>
}