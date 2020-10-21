CREATE TABLE features (
    id         SERIAL        PRIMARY KEY,
    name       VARCHAR (512) NOT NULL,
    content    TEXT   NOT NULL,
    xpath      TEXT   NOT NULL,
    created_at TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP
);
