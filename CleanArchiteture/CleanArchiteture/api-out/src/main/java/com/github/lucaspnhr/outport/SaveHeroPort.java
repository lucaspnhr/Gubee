package com.github.lucaspnhr.outport;

import java.util.UUID;

public interface SaveHeroPort {

    boolean saveHero(SaveHeroCommand saveHeroCommand);
}
