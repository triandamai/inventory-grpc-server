package app.trian.inventory.module.detail_inbound

import app.trian.inventory.module.inbound.Inbound
import app.trian.inventory.module.product.Product
import app.trian.inventory.module.supplier.Supplier
import app.trian.inventory.module.user.User
import org.hibernate.annotations.GenericGenerator
import java.util.*
import javax.persistence.*

@Entity
data class DetailInbound(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    var id:String? = null,
    var status:String,
    var totalAmount:Int,
    @ManyToOne(
        fetch = FetchType.LAZY,
        cascade = [CascadeType.REFRESH]
    )
    var product: Product?=null,
    @ManyToOne(
        fetch = FetchType.LAZY,
        cascade = [CascadeType.REFRESH]
    )
    var inbound: Inbound?=null,
    @Temporal(TemporalType.TIMESTAMP)
    var createdAt: Date,
    @Temporal(TemporalType.TIMESTAMP)
    var updatedAt: Date
)