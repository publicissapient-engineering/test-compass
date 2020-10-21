 CREATE TABLE business_scenario_features(
    business_scenario_id    BIGINT        NOT NULL,
    features_id             BIGINT        NOT NULL,
    FOREIGN KEY (business_scenario_id) REFERENCES business_scenario(id),
    FOREIGN KEY (features_id) REFERENCES features(id)
)