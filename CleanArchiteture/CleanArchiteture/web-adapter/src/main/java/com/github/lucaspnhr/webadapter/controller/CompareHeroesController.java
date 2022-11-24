package com.github.lucaspnhr.webadapter.controller;

import com.github.lucaspnhr.usecase.CompareHeroRequest;
import com.github.lucaspnhr.usecase.CompareHeroUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/hero/compare")
@RequiredArgsConstructor
public class CompareHeroesController {

    private final CompareHeroUseCase compareHeroUseCase;

    @GetMapping()
    public ResponseEntity<CompareHeroRequest> compareHeroes(@RequestParam UUID firstHeroId, @RequestParam UUID secondHeroId){
        CompareHeroRequest compareHeroRequest = compareHeroUseCase.compareTwoHeroes(firstHeroId, secondHeroId);
        return ResponseEntity.ok(compareHeroRequest);
    }
}
