create table business_scenario_run (
    run_id                                     BIGINT NOT NULL,
    business_scenario_id                        BIGINT NOT NULL,
    FOREIGN KEY (run_id)  REFERENCES runs(id),
    FOREIGN KEY (business_scenario_id)  REFERENCES business_scenario(id)
);