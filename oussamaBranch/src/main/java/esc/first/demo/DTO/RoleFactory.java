package esc.first.demo.DTO;

import esc.first.demo.Exceptions.RoleNotFoundException;
import esc.first.demo.Model.ERole;
import esc.first.demo.Model.Role;
import esc.first.demo.Repositpries.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleFactory {
    @Autowired
    RoleRepo roleRepository;

    public Role getInstance(String role) throws RoleNotFoundException {
        switch (role) {
            case "ROLE_MEMBER" -> {
                return roleRepository.findByName(ERole.ROLE_ADMIN);
            }
            case "ROLE_ADMIN" -> {
                return roleRepository.findByName(ERole.ROLE_MEMBER);
            }

            default -> throw  new RoleNotFoundException("No role found for " +  role);
        }
    }

}
