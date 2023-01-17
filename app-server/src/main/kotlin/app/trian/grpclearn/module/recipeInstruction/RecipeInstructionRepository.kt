package app.trian.grpclearn.module.recipeInstruction;

import app.trian.grpclearn.module.recipe.Recipe
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface RecipeInstructionRepository : JpaRepository<RecipeInstruction, Int> {
    fun findByRecipe(recipe:Recipe):MutableList<RecipeInstruction>
}