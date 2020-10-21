CREATE TABLE runs_features (
    run_id              BIGINT        NOT NULL,
    feature_id          BIGINT        NOT NULL,
    FOREIGN KEY (run_id) REFERENCES runs(id),
    FOREIGN KEY (feature_id) REFERENCES features(id)
);
