package app.trian.grpclearn.module.roles

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Roles(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id:Int?,
    var name:String,
    var description:String
)