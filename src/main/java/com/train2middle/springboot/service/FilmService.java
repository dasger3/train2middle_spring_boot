package com.train2middle.springboot.service;

import com.train2middle.springboot.model.Film;
import com.train2middle.springboot.repository.FilmRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmRepository filmRepository;

    public List<Film> getAll() {
        return filmRepository.findAll();
    }

    public Film getFilmById(Long id) {
        Optional<Film> film = filmRepository.findById(id);
        if (film.isEmpty()) throw new ObjectNotFoundException(id,"Film");
        return film.get();
    }

    public Film saveFilm(Film film) {
        return filmRepository.save(film);
    }

    public Film updateFilm(Long id, Film film) {
        Film tmp = getFilmById(id);
        film.setId(tmp.getId());
        return filmRepository.save(film);
    }

    public void deleteFilmById(Long id) {
        filmRepository.deleteById(id);
    }
}
