--liquibase formatted sql

--changeset ivan.belyaev:1
ALTER TABLE users
ADD COLUMN image VARCHAR(64);

--changeset ivan.belyaev:2
ALTER TABLE users_aud
ADD COLUMN image VARCHAR(64);
