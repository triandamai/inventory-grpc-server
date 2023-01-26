package app.trian.inventory.module.permission

import org.hibernate.annotations.GenericGenerator
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Temporal
import javax.persistence.TemporalType

@Entity
data class Permission(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    var id:String? = null,
    @Column(nullable = false)
    var permissionName:String? = null,
    @Column(nullable = false)
    var permissionType:String? = null,
    @Column
    var permissionGroup:String?=null,
    @Temporal(TemporalType.TIMESTAMP)
    var createdAt: Date?=null,
    @Temporal(TemporalType.TIMESTAMP)
    var updatedAt: Date?= null
)