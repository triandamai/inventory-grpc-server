package app.trian.grpclearn.module.recipe_ingredients;

import app.trian.grpclearn.module.recipe.Recipe
import org.springframework.data.jpa.repository.JpaRepository

interface RecipeIngredientsRepository : JpaRepository<RecipeIngredients, Int> {
    fun findByRecipe(recipe:Recipe):MutableList<RecipeIngredients>
}