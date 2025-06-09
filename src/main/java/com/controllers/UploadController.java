package com.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.models.Flashcard;
import org.springframework.web.bind.annotation.GetMapping;
import com.services.FlashcardmanagamentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class UploadController {
    private final FlashcardmanagamentService flashcardService;
    public UploadController(FlashcardmanagamentService flashcardService) {
        this.flashcardService = flashcardService;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @PostMapping("/upload")
    public String uploadFile(
            @RequestParam("jsonData") String jsonData,
            Model model
    ) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            FlashcardmanagamentService.flashcards = mapper.readValue(jsonData, new TypeReference<List<Flashcard>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        model.addAttribute("question", flashcardService.getRandomFlashcard().getQuestion());
        flashcardService.removeFlashcard(FlashcardmanagamentService.currentFlashcard);
        return "question";
    }

    @GetMapping("/next")
    public String nextFlashcard(Model model) {
        if (FlashcardmanagamentService.flashcards == null || FlashcardmanagamentService.flashcards.isEmpty()) {
            model.addAttribute("message", "No flashcards left.");
            model.addAttribute("good", flashcardService.getRandomGoodJobMessage());
            return "noremaining";
        }else {
            FlashcardmanagamentService.currentFlashcard = flashcardService.getRandomFlashcard();
            model.addAttribute("question", FlashcardmanagamentService.currentFlashcard.getQuestion());
        }
        flashcardService.removeFlashcard(FlashcardmanagamentService.currentFlashcard);
        return "question";
    }

    @GetMapping("/showanswer")
    public String showAnswer(Model model) {
        if (FlashcardmanagamentService.currentFlashcard != null) {
            model.addAttribute("answer", FlashcardmanagamentService.currentFlashcard.getAnswer());
        } else if (FlashcardmanagamentService.flashcards.isEmpty()) {
            model.addAttribute("message", "No flashcards left.");
            return "noremaining";
        }
        return "answer";
    }
}
