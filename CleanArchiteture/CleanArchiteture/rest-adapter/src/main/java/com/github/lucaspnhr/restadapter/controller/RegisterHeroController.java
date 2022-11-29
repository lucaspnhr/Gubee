package com.github.lucaspnhr.restadapter.controller;

import com.github.lucaspnhr.usecase.RegisterHeroUseCase;
import com.github.lucaspnhr.usecase.request.RegisterHeroRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

import static com.github.lucaspnhr.restadapter.util.constants.Mapping.BASE_URL;
import static java.lang.String.format;

@RestController
@RequestMapping(BASE_URL+"/register")
@RequiredArgsConstructor
public class RegisterHeroController {

    private final RegisterHeroUseCase registerHeroUseCase;

    @PostMapping
    public ResponseEntity<String> registerHero(@RequestBody RegisterHeroRequest registerHero){
        UUID id = registerHeroUseCase.registerHero(registerHero);
        return ResponseEntity.created(URI.create(format("%s/%s",BASE_URL, id))).build();
    }
}
