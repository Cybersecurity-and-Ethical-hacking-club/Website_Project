package esc.first.demo.Repositpries;

import esc.first.demo.Model.Role;
import esc.first.demo.Model.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role,Long> {
    Role findByName(ERole name);
}
