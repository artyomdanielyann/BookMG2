package com.example.librarybookmanager;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("user-collections")
    private List<BookCollection> collections = new ArrayList<>();

    public User() {
    }

    public User(String username) {
        this.username = username;
    }

    public void addCollection(BookCollection collection) {
        collections.add(collection);
        collection.setUser(this);
    }


    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<BookCollection> getCollections() {
        return collections;
    }

    public void setCollections(List<BookCollection> collections) {
        this.collections = collections;
    }
}