package com.services;

import com.models.Flashcard;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.ArrayList;
import java.util.List;

@Service
@ApplicationScope
public class FlashcardmanagamentService {
    public static List<Flashcard> flashcards = new ArrayList<>();
    public static Flashcard currentFlashcard;
    public static List<String> goodJob = List.of(
            "Good job with learning today!",
            "Well done!",
            "Excellent!",
            "Keep it up!",
            "Great work!"
    );

    public List<Flashcard> getFlashcards() {
        return flashcards;
    }

    public Flashcard getRandomFlashcard() {
        if (flashcards.isEmpty()) {
            return null; // or throw an exception
        }
        int randomIndex = (int) (Math.random() * flashcards.size());
        currentFlashcard = flashcards.get(randomIndex);
        return currentFlashcard;
    }

    public String getRandomGoodJobMessage() {
        int randomIndex = (int) (Math.random() * goodJob.size());
        return goodJob.get(randomIndex);
    }

    public void removeFlashcard(Flashcard flashcard) {
        flashcards.remove(flashcard);
    }
}
