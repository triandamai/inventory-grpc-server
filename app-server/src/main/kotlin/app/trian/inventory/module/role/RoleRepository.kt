package app.trian.inventory.module.role

import org.springframework.data.repository.PagingAndSortingRepository

interface RoleRepository:PagingAndSortingRepository<Role,Int> {
}