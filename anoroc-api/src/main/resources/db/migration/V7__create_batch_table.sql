CREATE TABLE batch (
    id         SERIAL        PRIMARY KEY,
    name       VARCHAR (512) NOT NULL,
    description varchar(1024) DEFAULT NULL,
    created_at TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP

)