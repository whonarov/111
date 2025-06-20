
package com.project.threatalerts.repo;

import com.project.threatalerts.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;



public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}

