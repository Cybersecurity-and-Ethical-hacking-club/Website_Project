package esc.first.demo.Model;

import esc.first.demo.Repositpries.RoleRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class RoleDataSeeder {
    private static final Logger logger = LoggerFactory.getLogger(RoleDataSeeder.class);

    @Autowired
    private RoleRepo roleRepository;

    @EventListener
    @Transactional
    public void loadRoles(ContextRefreshedEvent event) {
        logger.info("Starting role data seeding...");

        Arrays.stream(ERole.values())
                .forEach(erole -> {
                    Role existingRole = roleRepository.findByName(erole);
                    if (existingRole == null) {
                        logger.info("Role {} does not exist, creating...", erole);
                        roleRepository.save(new Role(erole));
                        logger.info("Role {} created successfully.", erole);
                    } else {
                        logger.info("Role {} already exists, skipping creation.", erole);
                    }
                });

        logger.info("Role data seeding completed.");
    }
}


