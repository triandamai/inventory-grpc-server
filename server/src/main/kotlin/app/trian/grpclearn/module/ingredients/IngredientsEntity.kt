package app.trian.grpclearn.module.ingredients

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Ingredients(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id:Int?,
    var name:String,
    var picture:String,
    var description:String
)