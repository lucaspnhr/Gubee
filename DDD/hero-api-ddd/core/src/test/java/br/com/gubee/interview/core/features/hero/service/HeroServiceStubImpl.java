package br.com.gubee.interview.core.features.hero.service;

import br.com.gubee.interview.core.application.HeroService;
import br.com.gubee.interview.core.application.util.exception.NotFoundHeroException;
import br.com.gubee.interview.core.features.hero.repository.HeroRepositoryStubImpl;
import br.com.gubee.interview.domain.model.hero.Hero;
import br.com.gubee.interview.domain.model.request.CreateHeroRequest;
import br.com.gubee.interview.domain.model.request.RetrieveHeroRequest;
import br.com.gubee.interview.domain.model.request.UpdateHeroRequest;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class HeroServiceStubImpl implements HeroService {

    private final HeroRepository heroRepository = new HeroRepositoryStubImpl();

    @Override
    public UUID create(CreateHeroRequest createHeroRequest) {
        Hero heroToCreate = new Hero(createHeroRequest, UUID.randomUUID());
        return heroRepository.create(heroToCreate);
    }

    @Override
    public RetrieveHeroRequest retriveById(UUID id) {
        Hero hero = heroRepository.retriveById(id).orElseThrow(() -> new NotFoundHeroException(id));
        return getRetrieveHeroRequest(hero);
    }

    @Override
    public List<RetrieveHeroRequest> retriveByName(String name) {
        List<Hero> heroes = heroRepository.retriveByName(name);
        if (heroes.isEmpty()){return Collections.emptyList();}
        return heroes.stream().map(HeroServiceStubImpl::getRetrieveHeroRequest).collect(Collectors.toList());

    }

    @Override
    public RetrieveHeroRequest update(UUID id, UpdateHeroRequest updateHeroRequest) {
        Hero heroToUpdate = heroRepository.retriveById(id).orElseThrow(() -> new NotFoundHeroException(id));;
        heroToUpdate.setName(updateHeroRequest.getName());
        heroToUpdate.setRace(updateHeroRequest.getRace());
        heroRepository.update(heroToUpdate);
        return getRetrieveHeroRequest(heroToUpdate);
    }

    @Override
    public void deleteById(UUID id) {
        heroRepository.delete(id);
    }

    @Override
    public List<RetrieveHeroRequest> retriveHerosByIds(UUID firstHeroId, UUID secondHeroId) {
        Hero firstHero = heroRepository.retriveById(firstHeroId).orElseThrow(() -> new NotFoundHeroException(firstHeroId));;
        Hero secondHero = heroRepository.retriveById(secondHeroId).orElseThrow(() -> new NotFoundHeroException(secondHeroId));;

        return List.of(getRetrieveHeroRequest(firstHero),getRetrieveHeroRequest(secondHero));
    }

    @Override
    public void deleteAll() {
        heroRepository.deleteAll();
    }

    private static RetrieveHeroRequest getRetrieveHeroRequest(Hero hero) {
        return RetrieveHeroRequest.builder()
                .id(hero.getId())
                .name(hero.getName())
                .race(hero.getRace())
                .dexterity(5)
                .intelligence(6)
                .strength(8)
                .build();
    }
}
