package com.github.lucaspnhr.outport;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class RetrieveHeroRequest {
    @NotNull
    private UUID heroId;
    @NotBlank
    private String name;
    @Min(0)
    @Max(10)
    private int strength;
    @Min(0)
    @Max(10)
    private int agility;
    @Min(0)
    @Max(10)
    private int intelligence;
    @Min(0)
    @Max(10)
    private int dexterity;
}
