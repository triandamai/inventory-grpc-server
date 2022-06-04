package app.trian.grpclearn.module.ingredients;

import org.springframework.data.jpa.repository.JpaRepository

interface IngredientsRepository : JpaRepository<Ingredients, Int> {
}