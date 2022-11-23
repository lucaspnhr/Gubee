package br.com.gubee.interview.core.application.in;

import br.com.gubee.interview.core.application.DTO.SaveHeroDTO;
import br.com.gubee.interview.core.interfaces.hero.request.CreateHeroRequest;
import br.com.gubee.interview.core.interfaces.hero.request.RetrieveHeroRequest;
import br.com.gubee.interview.core.interfaces.hero.request.UpdateHeroRequest;
import br.com.gubee.interview.domain.model.hero.enums.Race;
import br.com.gubee.interview.domain.model.request.CreateHeroRequest;
import br.com.gubee.interview.domain.model.request.RetrieveHeroRequest;
import br.com.gubee.interview.domain.model.request.UpdateHeroRequest;
import br.com.gubee.interview.domain.shared.SelfValidating;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public interface HeroService {
    UUID create(SaveHeroDTO saveHero);

    RetrieveHeroRequest retriveById(UUID id);

    List<RetrieveHeroRequest> retriveByName(String name);

    RetrieveHeroRequest update(UUID id, UpdateHeroRequest updateHeroRequest);

    void deleteById(UUID id);

    List<RetrieveHeroRequest> retriveHerosByIds(UUID firstHero, UUID secondHero);

    void deleteAll();

}
