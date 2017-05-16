package io.karmanov.watermark.repository;

import io.karmanov.watermark.domain.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    Optional<Document> findById(Long id);
}
