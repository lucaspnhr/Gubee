package com.github.lucaspnhr.outport;

import java.util.UUID;

public interface LoadHeroPort {
    RetrievedHero loadHeroByid(UUID id);
}
