package com.github.lucaspnhr.usecase;

import com.github.lucaspnhr.usecase.request.CompareHeroRequest;

import java.util.UUID;

public interface CompareHeroUseCase {
    CompareHeroRequest compareTwoHeroes(UUID firstHeroId, UUID secondHeroId);
}
