package app.trian.inventory.module.permission

import org.springframework.data.repository.PagingAndSortingRepository

interface PermissionRepository:PagingAndSortingRepository<Permission,String> {
}