package app.trian.grpclearn.module.recipe;

import org.springframework.data.jpa.repository.JpaRepository

interface RecipeRepository : JpaRepository<Recipe, Int> {
}