package br.com.gubee.interview.core.application.in.impl;

import br.com.gubee.interview.core.application.in.HeroService;
import br.com.gubee.interview.core.application.util.exception.HeroAlredyExistsException;
import br.com.gubee.interview.core.application.util.exception.NotFoundHeroException;
import br.com.gubee.interview.core.application.in.PowerStatsService;
import br.com.gubee.interview.domain.model.hero.Hero;
import br.com.gubee.interview.core.application.out.HeroRepository;
import br.com.gubee.interview.domain.model.powerstats.PowerStats;
import br.com.gubee.interview.domain.model.request.CreateHeroRequest;
import br.com.gubee.interview.domain.model.request.RetrieveHeroRequest;
import br.com.gubee.interview.domain.model.request.UpdateHeroRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class HeroServiceImpl implements HeroService {

    private final HeroRepository heroRepository;
    private final PowerStatsService powerStatsService;

    @Override
    @Transactional
    public UUID create(CreateHeroRequest createHeroRequest) {
        boolean exist = heroRepository.alreadyExists(createHeroRequest.getName());
        if(exist){
            throw new HeroAlredyExistsException(createHeroRequest.getName());
        }
        UUID powerStatsId = powerStatsService.create(new PowerStats(createHeroRequest));
        Hero heroToAdd = new Hero(createHeroRequest, powerStatsId);
        return heroRepository.create(heroToAdd);
    }

    @Override
    @Transactional
    public RetrieveHeroRequest retriveById(UUID id){
        Hero retrivedHero = (heroRepository.retriveById(id))
                .orElseThrow(()-> new NotFoundHeroException(id));
        return new RetrieveHeroRequest(retrivedHero, retrieveItsPowerStats(retrivedHero));
    }
    @Override
    @Transactional
    public List<RetrieveHeroRequest> retriveByName(String name){
        if( name == null || name.isEmpty()){
            return Collections.emptyList();
        }
        List<Hero> retrivedHeros = heroRepository.retriveByName(name);
        return retrivedHeros.stream()
                .map((hero) -> new RetrieveHeroRequest(hero, retrieveItsPowerStats(hero)))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public RetrieveHeroRequest update(UUID id, UpdateHeroRequest updateHeroRequest){
        Hero oldHero = heroRepository.retriveById(id)
                .orElseThrow(() -> new NotFoundHeroException(id));
        powerStatsService.update(updateHeroRequest, oldHero.getPowerStatsId());
        oldHero.update(updateHeroRequest);
        int updateReturn = heroRepository.update(oldHero);
        //TODO maybe throw a custom exception here
        return updateReturn > 0 ?
                new RetrieveHeroRequest(oldHero, retrieveItsPowerStats(oldHero)) :
                new RetrieveHeroRequest();

    }

    @Override
    @Transactional
    public void deleteById(UUID id){
        Hero hero = heroRepository.retriveById(id).orElseThrow(() -> new NotFoundHeroException(id));
        powerStatsService.deleteById(hero.getPowerStatsId());
        heroRepository.delete(hero.getId());
    }

    @Override
    @Transactional
    public List<RetrieveHeroRequest> retriveHerosByIds(UUID firstHero, UUID secondHero) {
        Hero firstRetrivedHero = (heroRepository.retriveById(firstHero)).orElseThrow(()-> new NotFoundHeroException(firstHero));
        Hero secondRetrivedHero = (heroRepository.retriveById(secondHero)).orElseThrow(()-> new NotFoundHeroException(secondHero));
        return List.of(new RetrieveHeroRequest(firstRetrivedHero, retrieveItsPowerStats(firstRetrivedHero)),
                new RetrieveHeroRequest(secondRetrivedHero, retrieveItsPowerStats(secondRetrivedHero)));
    }

    @Override
    @Transactional
    public void deleteAll() {
        powerStatsService.deleteAll();
        heroRepository.deleteAll();
    }

    private PowerStats retrieveItsPowerStats(Hero hero) {
        return powerStatsService.retriveById(hero.getPowerStatsId());
    }
}
