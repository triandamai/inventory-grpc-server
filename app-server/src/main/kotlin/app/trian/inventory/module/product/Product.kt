package app.trian.inventory.module.product


import app.trian.inventory.module.category.Category
import app.trian.inventory.module.user.User
import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.*
import javax.persistence.*

@Entity
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id:Int?,
    var productName:String,
    var productDescription:String,
    var productImage:String,
    var productUnit:String,
    var productOutboundPrice:Int,
    var productInboundPrice:Int,
    var productStock:Int,
    @ManyToMany(
        fetch = FetchType.LAZY,
        cascade = [CascadeType.REFRESH],
    )
    var category: List<Category> = listOf(),
    @Temporal(TemporalType.TIMESTAMP)
    var createdAt: Date,
    @Temporal(TemporalType.TIMESTAMP)
    var updatedAt: Date

)