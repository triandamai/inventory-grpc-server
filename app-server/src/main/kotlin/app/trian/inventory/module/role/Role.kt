package app.trian.inventory.module.role


import app.trian.inventory.module.permission.Permission
import app.trian.inventory.module.user.User
import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.*

@Entity
data class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    var id:String? = null,
    @Column(nullable = false)
    var roleName:String? = null,
    @Column(nullable = false)
    var roleDescription:String? = null,
    @ManyToMany(
        fetch = FetchType.LAZY,
        cascade = [CascadeType.REFRESH]
    )
    var permissions:List<Permission> = listOf(),
    @Temporal(TemporalType.TIMESTAMP)
    var createdAt: Date?=null,
    @Temporal(TemporalType.TIMESTAMP)
    var updatedAt: Date?= null
)