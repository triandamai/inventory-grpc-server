package app.trian.inventory.module.detail_outbound

import app.trian.inventory.module.inbound.Inbound
import app.trian.inventory.module.outbound.Outbound
import app.trian.inventory.module.product.Product
import java.util.*
import javax.persistence.*

@Entity
data class DetailOutbound(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id:Int?,
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
    var outbound: Outbound?=null,
    @Temporal(TemporalType.TIMESTAMP)
    var createdAt: Date,
    @Temporal(TemporalType.TIMESTAMP)
    var updatedAt: Date
)