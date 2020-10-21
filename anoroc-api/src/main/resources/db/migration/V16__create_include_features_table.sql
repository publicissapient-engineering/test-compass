CREATE TABLE include_features (
    include_feature_id      BIGINT NOT NULL,
    feature_id              BIGINT NOT NULL,
    FOREIGN KEY (feature_id)  REFERENCES features(id)
);