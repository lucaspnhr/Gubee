package com.github.lucaspnhr.restadapter.controller;

import com.github.lucaspnhr.usecase.request.CompareHeroRequest;
import com.github.lucaspnhr.usecase.CompareHeroUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.github.lucaspnhr.restadapter.util.constants.Mapping.BASE_URL;

@RestController
@RequestMapping(BASE_URL+"/compare")
@RequiredArgsConstructor
public class CompareHeroesController {

    private final CompareHeroUseCase compareHeroUseCase;

    @GetMapping()
    public ResponseEntity<CompareHeroRequest> compareHeroes(@RequestParam UUID firstHeroId, @RequestParam UUID secondHeroId){
        CompareHeroRequest compareHeroRequest = compareHeroUseCase.compareTwoHeroes(firstHeroId, secondHeroId);
        return ResponseEntity.ok(compareHeroRequest);
    }
}
