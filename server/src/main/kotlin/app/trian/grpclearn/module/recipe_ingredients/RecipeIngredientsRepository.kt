package app.trian.grpclearn.module.recipe_ingredients;

import org.springframework.data.jpa.repository.JpaRepository

interface RecipeIngredientsRepository : JpaRepository<RecipeIngredients, Int> {
}