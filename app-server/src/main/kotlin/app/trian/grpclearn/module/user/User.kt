package app.trian.grpclearn.module.user

import app.trian.grpclearn.module.role.Role
import javax.persistence.*

@Entity
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id:Int?,
    var name:String,
    var username:String,
    @Column(
        unique = true
    )
    var email:String,
    var password:String,
    var auth_provider:String,
    @ManyToMany(
        mappedBy = "user",
        fetch = FetchType.LAZY,
        cascade = [CascadeType.REMOVE]
    )
    var roles:List<Role> = listOf(),
    var createdAt:String,
    var updatedAt:String
)