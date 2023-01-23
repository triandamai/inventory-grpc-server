package app.trian.inventory.module.category


import app.trian.inventory.module.user.User
import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.GenericGenerator
import java.util.*
import javax.persistence.*

@Entity
data class Category(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    var id:String? = null,
    var categoryName:String,
    var categoryDescription:String,
    @Temporal(TemporalType.TIMESTAMP)
    var createdAt: Date,
    @Temporal(TemporalType.TIMESTAMP)
    var updatedAt: Date
)