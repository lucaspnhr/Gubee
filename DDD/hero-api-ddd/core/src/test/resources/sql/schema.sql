CREATE schema  interview_service_test;


CREATE TABLE power_stats
(
    id           UUID PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4() ,
    strength_    SMALLINT         NOT NULL,
    agility      SMALLINT         NOT NULL,
    dexterity    SMALLINT         NOT NULL,
    intelligence SMALLINT         NOT NULL,
    created_at   TIMESTAMP      NOT NULL DEFAULT now(),
    updated_at   TIMESTAMP      NOT NULL DEFAULT now()
);

CREATE TABLE hero
(
    id           UUID PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4() ,
    name           VARCHAR(255)     NOT NULL UNIQUE,
    race           VARCHAR(255)     NOT NULL,
    power_stats_id UUID             NOT NULL,
    enabled        BOOLEAN          NOT NULL DEFAULT TRUE,
    created_at     TIMESTAMP      NOT NULL DEFAULT now(),
    updated_at     TIMESTAMP      NOT NULL DEFAULT now(),
    CHECK ( race IN ('HUMAN', 'ALIEN', 'DIVINE', 'CYBORG')),
    CONSTRAINT FK_power_stats FOREIGN KEY (power_stats_id) REFERENCES power_stats(id) ON DELETE CASCADE
);
