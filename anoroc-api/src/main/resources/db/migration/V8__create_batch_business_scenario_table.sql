 CREATE TABLE batch_business_scenario(
    batch_id              BIGINT        NOT NULL,
    business_scenario_id  BIGINT        NOT NULL,
    FOREIGN KEY (batch_id) REFERENCES batch(id),
    FOREIGN KEY (business_scenario_id) REFERENCES business_scenario(id)
)