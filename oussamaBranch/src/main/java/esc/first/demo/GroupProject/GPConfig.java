package esc.first.demo.GroupProject;

import esc.first.demo.GroupProject.Models.GPModel;
import esc.first.demo.GroupProject.Repositories.GPRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class GPConfig {
    @Bean
    CommandLineRunner commandLineRunner(GPRepository gpRepository) {
        return args -> {

                    GPModel gp1 = new GPModel(
                            1L,
                            "Testname1",
                            "testdescription1",
                            LocalDateTime.now(),
                            LocalDateTime.now()
                    );

            GPModel gp2 = new GPModel(
                    2L,
                    "Testname2",
                    "testdescription2",
                    LocalDateTime.now(),
                    LocalDateTime.now()
            );

            GPModel gp3 = new GPModel(
                    3L,
                    "Testname3",
                    "testdescription3",
                    LocalDateTime.now(),
                    LocalDateTime.now()
            );

            gpRepository.saveAll(List.of(gp1,gp2,gp3));

        };
    }
}
