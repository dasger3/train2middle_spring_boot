package com.train2middle.springboot.service;

import com.train2middle.springboot.model.Film;
import com.train2middle.springboot.repository.FilmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmRepository filmRepository;

    public List<Film> getAll() {
        return filmRepository.findAll();
    }
}
