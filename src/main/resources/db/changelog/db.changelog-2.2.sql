--liquibase formatted sql

--changeset ivan.belyaev:1
ALTER TABLE users_aud
DROP CONSTRAINT users_aud_username_key;

--changeset ivan.belyaev:2
ALTER TABLE users_aud
ALTER COLUMN username DROP NOT NULL;
