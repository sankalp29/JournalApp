package com.thejournalapp.journalApp.controller;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thejournalapp.journalApp.entity.JournalEntry;
import com.thejournalapp.journalApp.entity.User;
import com.thejournalapp.journalApp.service.JournalEntryService;
import com.thejournalapp.journalApp.service.UserService;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;
    
    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        System.out.println("User by username : " + user);
        List<JournalEntry> allEntries = user.getJournalEntries();
        System.out.println("All Entries by user: " + allEntries);
        if (allEntries != null && !allEntries.isEmpty()) return new ResponseEntity<>(allEntries, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/id/{requestId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId requestId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        Optional<JournalEntry> journalEntry = user.getJournalEntries().stream().filter(entry -> entry.getId().equals(requestId)).findFirst();
        if (journalEntry.isPresent()) {
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry entry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        try {
            System.out.println("Saving a new entry");
            journalEntryService.saveEntry(entry, username);
            return new ResponseEntity<>(entry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/id/{requestId}")
    public ResponseEntity<?> deleteEntryById(@PathVariable ObjectId requestId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        boolean removed  = journalEntryService.deleteEntryById(username, requestId);
        if (removed) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/id/{requestId}")
    public ResponseEntity<JournalEntry> updateJournalById(@PathVariable ObjectId requestId, @RequestBody JournalEntry updatedEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        JournalEntry journalEntry = user.getJournalEntries().stream().filter(journal -> journal.getId().equals(requestId)).findFirst().orElse(null);
        if (journalEntry == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        journalEntry.setTitle(updatedEntry.getTitle() == null || updatedEntry.getTitle().isEmpty() ? journalEntry.getTitle() : updatedEntry.getTitle());
        journalEntry.setContent(updatedEntry.getContent() == null || updatedEntry.getContent().isEmpty() ? journalEntry.getContent() : updatedEntry.getContent());
        journalEntryService.saveEntry(journalEntry);
        return new ResponseEntity<>(journalEntry, HttpStatus.OK);
    }
}