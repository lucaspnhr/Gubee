package com.github.lucaspnhr.usecase;

import com.github.lucaspnhr.commom.SelfValidating;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class CompareHeroesCommand extends SelfValidating<CompareHeroesCommand> {
    @NotNull
    private UUID firstHeroId;
    @NotNull
    private UUID secondHeroId;

    public CompareHeroesCommand(UUID firstHeroId, UUID secondHeroId) {
        this.firstHeroId = firstHeroId;
        this.secondHeroId = secondHeroId;
        validateSelf();
    }
}
