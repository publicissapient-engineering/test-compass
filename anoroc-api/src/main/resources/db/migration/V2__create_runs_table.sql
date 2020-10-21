CREATE TABLE runs (
    id         SERIAL         PRIMARY KEY,
    details    VARCHAR(512)   DEFAULT NULL,
    report_url VARCHAR(512)   DEFAULT NULL,
    status     INTEGER,
    run_type   INTEGER,
    created_at TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP
);
