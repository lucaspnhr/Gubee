package com.github.lucaspnhr.usecase;

import com.github.lucaspnhr.usecase.request.RegisterHeroRequest;

import java.util.UUID;

public interface RegisterHeroUseCase {

    public UUID registerHero(RegisterHeroRequest heroToRegister);
}
