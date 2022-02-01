package com.train2middle.springboot.service.implementation;

import com.train2middle.springboot.model.Film;
import com.train2middle.springboot.repository.FilmRepository;
import com.train2middle.springboot.service.FilmService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {

    private final FilmRepository filmRepository;

    @Override
    public List<Film> getAll() {
        return filmRepository.findAll();
    }
}
