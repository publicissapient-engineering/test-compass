CREATE TABLE global_object (
    id                  SERIAL              PRIMARY KEY,
    name                VARCHAR (512)       NOT NULL,
    content             TEXT         NOT NULL,
    created_at          TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP,
    application_id      BIGINT              NOT NULL,
    FOREIGN KEY (application_id)    REFERENCES application(id)
);