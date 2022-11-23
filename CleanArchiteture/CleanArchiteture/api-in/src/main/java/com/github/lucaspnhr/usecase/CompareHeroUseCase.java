package com.github.lucaspnhr.usecase;

import java.util.UUID;

public interface CompareHeroUseCase {
    CompareHeroRequest compareTwoHeroes(UUID firstHeroId, UUID secondHeroId);
}
