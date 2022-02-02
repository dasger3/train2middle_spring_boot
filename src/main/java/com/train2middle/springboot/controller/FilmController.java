package com.train2middle.springboot.controller;

import com.train2middle.springboot.model.Film;
import com.train2middle.springboot.service.FilmService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("film")
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;

    @GetMapping("/")
    public ResponseEntity<List<Film>> findAllFilms() {
        return ResponseEntity.ok(filmService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Film> findFilmById(@PathVariable Long id) {
        return ResponseEntity.ok(filmService.getFilmById(id));
    }

    @PostMapping
    public ResponseEntity<Film> saveFilm(@RequestBody Film film) {
        return ResponseEntity.ok(filmService.saveFilm(film));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Film> updateFilm(@PathVariable Long id, @RequestBody Film film) {
        return ResponseEntity.ok(filmService.updateFilm(id, film));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFilmById(@PathVariable Long id) {
        filmService.deleteFilmById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
