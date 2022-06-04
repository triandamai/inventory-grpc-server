package app.trian.grpclearn.module.recipe

import app.trian.grpclearn.module.recipe_ingredients.RecipeIngredients
import app.trian.grpclearn.module.user.User
import javax.persistence.*

@Entity
data class Recipe(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id:Int?,
    var title:String,
    var description:String,
    var cover:String,
    var duration:String,
    @ManyToOne(
        cascade = [CascadeType.REMOVE],
        fetch = FetchType.LAZY
    )
    @JoinColumn(
        name = "recipes"
    )
    var user:User?,
    @OneToMany(
        mappedBy = "id",
        fetch = FetchType.LAZY
    )
    var ingredients:List<RecipeIngredients> = listOf()
)