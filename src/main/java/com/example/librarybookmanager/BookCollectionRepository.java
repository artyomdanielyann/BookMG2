package com.example.librarybookmanager;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BookCollectionRepository extends JpaRepository<BookCollection, Long> {
    Optional<BookCollection> findByNameAndUserId(String name, Long userId);
}