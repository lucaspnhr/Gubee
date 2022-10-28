package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.model.request.BattleHeroRequest;
import br.com.gubee.interview.model.request.CreateHeroRequest;
import br.com.gubee.interview.model.request.RetrieveHeroRequest;
import br.com.gubee.interview.model.request.UpdateHeroRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import static java.lang.String.format;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/heroes", produces = APPLICATION_JSON_VALUE)
public class HeroController {

    private final HeroService heroServiceImpl;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> create(@Validated
                                       @RequestBody CreateHeroRequest createHeroRequest) {
        final UUID id = heroServiceImpl.create(createHeroRequest);
        return created(URI.create(format("/api/v1/heroes/%s", id))).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RetrieveHeroRequest> findHeroById(@PathVariable(required = false) UUID id){
        final RetrieveHeroRequest retrieveHeroRequest = heroServiceImpl.retriveById(id);
        return ok(retrieveHeroRequest);
    }
    @GetMapping("/filter")
    public ResponseEntity<List<RetrieveHeroRequest>> findHeroByName(@RequestParam(required = false) String name){
        final List<RetrieveHeroRequest> retrieveHeroRequest = heroServiceImpl.retriveByName(name);
        return retrieveHeroRequest.size() > 0 ? ok(retrieveHeroRequest) : ok().build();
    }

    @GetMapping("/battle")
    public ResponseEntity<BattleHeroRequest> compareToHeroes(@RequestParam(required = false) UUID firstHero,
                                                             @RequestParam(required = false) UUID secondHero){
        List<RetrieveHeroRequest> herosToCompare =  heroServiceImpl.retriveHerosByIds((firstHero), (secondHero));
        BattleHeroRequest battleHeroRequest = new BattleHeroRequest(herosToCompare.get(0), herosToCompare.get(1));
        return ok(battleHeroRequest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RetrieveHeroRequest> updateHero(@PathVariable UUID id, @RequestBody UpdateHeroRequest updateHeroRequest){
        RetrieveHeroRequest updatedHero = heroServiceImpl.update(id, updateHeroRequest);
        return ok(updatedHero);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHeroById(@PathVariable UUID id){
        heroServiceImpl.deleteById(id);
        return noContent().build();
    }


}
