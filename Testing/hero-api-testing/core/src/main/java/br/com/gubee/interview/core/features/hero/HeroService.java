package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.core.exception.customException.HeroAlredyExistsException;
import br.com.gubee.interview.core.exception.customException.NotFoundHeroException;
import br.com.gubee.interview.core.features.powerstats.PowerStatsService;
import br.com.gubee.interview.model.Hero;
import br.com.gubee.interview.model.PowerStats;
import br.com.gubee.interview.model.request.CreateHeroRequest;
import br.com.gubee.interview.model.request.RetrieveHeroRequest;
import br.com.gubee.interview.model.request.UpdateHeroRequest;
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
public class HeroService {

    private final HeroRepository heroRepository;
    private final PowerStatsService powerStatsService;

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

    @Transactional
    public RetrieveHeroRequest retriveById(UUID id){
        Hero retrivedHero = (heroRepository.retriveById(id))
                .orElseThrow(()-> new NotFoundHeroException(id));
        return createRetriveHero(retrivedHero);
    }
    @Transactional
    public List<RetrieveHeroRequest> retriveByName(String name){
        if( name == null || name.isEmpty()){
            return Collections.emptyList();
        }
        List<Hero> retrivedHeros = heroRepository.retriveByName(name);
        return retrivedHeros.stream()
                .map(this::createRetriveHero)
                .collect(Collectors.toList());
    }

    @Transactional
    public RetrieveHeroRequest update(UUID id, UpdateHeroRequest updateHeroRequest){
        Hero oldHero = heroRepository.retriveById(id)
                .orElseThrow(() -> new NotFoundHeroException(id));
        powerStatsService.update(updateHeroRequest, oldHero.getPowerStatsId());
        Hero heroUpdated = updateHeroRequestToHero(updateHeroRequest, oldHero);
        int updateReturn = heroRepository.updateHero(heroUpdated);
        return updateReturn > 0 ?
                createRetriveHero(heroUpdated)
                : createRetriveHero(oldHero);
    }

    @Transactional
    public void deleteById(UUID id){
        Hero hero = heroRepository.retriveById(id).orElseThrow(() -> new NotFoundHeroException(id));
        powerStatsService.deleteById(hero.getPowerStatsId());
        heroRepository.delete(hero.getId());
    }

    @Transactional
    public List<RetrieveHeroRequest> retriveHerosByIds(UUID firstHero, UUID secondHero) {
        Hero firstRetrivedHero = (heroRepository.retriveById(firstHero)).orElseThrow(()-> new NotFoundHeroException(firstHero));
        Hero secondRetrivedHero = (heroRepository.retriveById(secondHero)).orElseThrow(()-> new NotFoundHeroException(secondHero));
        return List.of(createRetriveHero(firstRetrivedHero),createRetriveHero(secondRetrivedHero));
    }
    private RetrieveHeroRequest createRetriveHero(Hero hero) {
        PowerStats powerStats = powerStatsService.retriveById(hero.getPowerStatsId());
        return RetrieveHeroRequest.builder()
                .id(hero.getId())
                .name(hero.getName())
                .race(hero.getRace())
                .strength(powerStats.getStrength())
                .agility(powerStats.getAgility())
                .dexterity(powerStats.getDexterity())
                .intelligence(powerStats.getIntelligence()).build();
    }

    private Hero updateHeroRequestToHero(UpdateHeroRequest updateHeroRequest, Hero oldHero) {
        if(updateHeroRequest.getName().isEmpty() || updateHeroRequest.getName() == null){
            updateHeroRequest.setName(oldHero.getName());
        }else if(updateHeroRequest.getRace() == null){
            updateHeroRequest.setRace(oldHero.getRace());
        }
        return Hero.builder()
                .id(oldHero.getId())
                .name(updateHeroRequest.getName())
                .race(updateHeroRequest.getRace())
                .powerStatsId(oldHero.getPowerStatsId())
                .build();
    }



}
