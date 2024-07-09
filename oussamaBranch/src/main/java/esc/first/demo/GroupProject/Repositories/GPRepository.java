package esc.first.demo.GroupProject.Repositories;

import esc.first.demo.GroupProject.Models.GPModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GPRepository extends JpaRepository<GPModel, Long> {


    Optional<GPModel> findByTagsContaining(String tag);

    Optional<GPModel> findByState(String state);
}
