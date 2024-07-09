package esc.first.demo.Document.Repositories;


import esc.first.demo.Document.Model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepo extends JpaRepository<Document, Long> {

}
