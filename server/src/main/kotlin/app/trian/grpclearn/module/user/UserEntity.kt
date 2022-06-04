package app.trian.grpclearn.module.user

import app.trian.grpclearn.module.recipe.Recipe
import app.trian.grpclearn.module.roles.Roles
import org.springframework.context.annotation.Primary
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id:Int?,
    var name:String,
    var username:String,
    var email:String,
    var password:String,
    var auth_provider:String,
    @OneToMany(
        mappedBy = "id"
    )
    var roles:List<Roles> = listOf(),
    @OneToMany(
        mappedBy = "id"
    )
    var recipes:List<Recipe> = listOf()
)
