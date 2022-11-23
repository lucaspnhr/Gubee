package com.github.lucaspnhr.outport;

import java.util.UUID;

public interface LoadHeroPort {
    RetrieveHeroRequest loadHeroByid(UUID id);
}
