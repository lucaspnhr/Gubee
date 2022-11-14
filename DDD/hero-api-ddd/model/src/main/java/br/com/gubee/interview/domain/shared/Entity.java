package br.com.gubee.interview.domain.shared;


import lombok.Getter;

public abstract class Entity<ID extends ValueObject<ID>> {

    @Getter
    private ID id;

    public Entity(ID id) {
        this.id = id;
    }

    protected boolean sameIdentityAs(ID id){
        return this.id.sameValueAs(id);
    }

    public Entity() {
    }
}
