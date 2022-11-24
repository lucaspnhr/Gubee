package com.github.lucaspnhr.webadapter.controller;

import com.github.lucaspnhr.usecase.CompareHeroUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/hero")
@RequiredArgsConstructor
public class CompareHeroesController {

    private final CompareHeroUseCase compareHeroUseCase;

    @GetMapping("/compare")
    public ResponseEntity<String> compareHeroes(){
        return null;
    }
}
