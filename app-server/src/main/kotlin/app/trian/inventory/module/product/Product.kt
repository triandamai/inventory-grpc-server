package app.trian.inventory.module.product


import app.trian.inventory.module.category.Category
import app.trian.inventory.module.user.User
import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.GenericGenerator
import java.util.*
import javax.persistence.*

@Entity
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    var id:String? = null,
    @Column(nullable = false)
    var productName:String? = null,
    var productDescription:String? = null,
    var productImage:String? = null,
    @Column(nullable = false)
    var productUnit:String? = null,
    @Column(nullable = false)
    var productOutboundPrice:Int = 0,
    @Column(nullable = false)
    var productInboundPrice:Int = 0,
    var productStock:Int = 0,
    @ManyToMany(
        fetch = FetchType.LAZY,
        cascade = [CascadeType.REFRESH],
    )
    var category: List<Category> = listOf(),
    @Temporal(TemporalType.TIMESTAMP)
    var createdAt: Date? = null,
    @Temporal(TemporalType.TIMESTAMP)
    var updatedAt: Date? = null

)