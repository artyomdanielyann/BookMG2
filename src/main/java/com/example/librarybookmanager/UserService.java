package com.example.librarybookmanager;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BookCollectionRepository collectionRepository;
    private final BookRepository bookRepository;

    public UserService(UserRepository userRepository, BookCollectionRepository collectionRepository, BookRepository bookRepository) {
        this.userRepository = userRepository;
        this.collectionRepository = collectionRepository;
        this.bookRepository = bookRepository;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found" + username));
    }

    @Transactional
    public BookCollection createCollectionForUser(String username, String collectionName) {
        User user = getUserByUsername(username);
        BookCollection collection = new BookCollection(collectionName);
        user.addCollection(collection);
        return collectionRepository.save(collection);
    }

    @Transactional
    public Book addBookToCollection(String username, String collectionName, String title, String author) {
        User user = getUserByUsername(username);

        BookCollection collection = user.getCollections().stream().filter(c -> c.getName().trim().equalsIgnoreCase(collectionName.trim())).findFirst().orElseThrow(() -> new RuntimeException("Collection not found: " + collectionName));

        Book book = new Book(title, author);
        collection.addBook(book);

        return bookRepository.save(book);
    }

    @Transactional
    public void removeBookFromCollection(String username, String collectionName, String title) {
        User user = getUserByUsername(username);

        BookCollection foundCollection = null;
        for (BookCollection collection : user.getCollections()) {
            if (collection.getName().trim().equals(collectionName.trim())) {
                foundCollection = collection;
                break;
            }
        }

        if (foundCollection== null) {
            throw new RuntimeException("Collection not found" + collectionName);
        }

        Book bookToRemove = null;
        for (Book book : foundCollection.getBooks()) {
            if (book.getTitle().trim().equals(title.trim())) {
                bookToRemove = book;
                break;
            }
        }

        if (bookToRemove == null) {
            throw new RuntimeException("Book not found" + title);
        }

        foundCollection.removeBook(bookToRemove);
        bookRepository.delete(bookToRemove);
    }
}