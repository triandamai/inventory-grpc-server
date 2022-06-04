package app.trian.grpclearn.module.roles;

import org.springframework.data.jpa.repository.JpaRepository

interface RolesRepository : JpaRepository<Roles, Int> {
}