package com.example.librarybookmanager;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")

    public ResponseEntity<User> createUser(@RequestBody Map<String, String> userData) {
        String username = userData.get("username");
        User user = new User(username);
        return ResponseEntity.ok(userService.createUser(user));
    }

    @PostMapping("/collections")
    public ResponseEntity<BookCollection> createCollection(@RequestBody Map<String, String> collectionData) {
        String username = collectionData.get("username");
        String name = collectionData.get("name");
        return ResponseEntity.ok(userService.createCollectionForUser(username, name));
    }

    @PostMapping("/books")
    public ResponseEntity<Book> addBookToCollection(@RequestBody Map<String, String> bookData) {
        String username = bookData.get("username");
        String collectionName = bookData.get("collectionName");
        String title = bookData.get("title");
        String author = bookData.get("author");

        return ResponseEntity.ok(userService.addBookToCollection(username, collectionName, title, author));
    }

    @PostMapping("/search")
    public ResponseEntity<User> getUserByUsernameJson(@RequestBody Map<String, String> userData) {
        String username = userData.get("username");
        User foundUser = userService.getUserByUsername(username);
        return ResponseEntity.ok(foundUser);
    }

    @PostMapping("/books/delete")
    public ResponseEntity<String> removeBookFromCollection(@RequestBody Map<String, String> payload) {
        String username = payload.get("username");
        String collectionName = payload.get("collectionName");
        String title = payload.get("title");

        userService.removeBookFromCollection(username, collectionName, title);
        return ResponseEntity.ok("The book has been deleted");
    }

}
