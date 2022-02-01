package com.train2middle.springboot.controller;

import com.train2middle.springboot.model.Film;
import com.train2middle.springboot.service.FilmService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("film")
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;

    @GetMapping("/")
    public ResponseEntity<List<Film>> findAllFilms () {
        return ResponseEntity.ok(filmService.getAll());
    }
}
