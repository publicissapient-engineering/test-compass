CREATE TABLE business_scenario(
    id          SERIAL               PRIMARY KEY,
    name        VARCHAR (512)        NOT NULL,
    description VARCHAR (1024)      NOT NULL,
    created_at  TIMESTAMP            NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP            NOT NULL DEFAULT CURRENT_TIMESTAMP
);