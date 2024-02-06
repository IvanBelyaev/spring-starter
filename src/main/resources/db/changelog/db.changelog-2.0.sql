--liquibase formatted sql

--changeset ivan.belyaev:1
ALTER TABLE users
    ADD COLUMN created_at TIMESTAMP,
    ADD COLUMN modified_at TIMESTAMP;

ALTER TABLE users
    ADD COLUMN created_by VARCHAR(32),
    ADD COLUMN modified_by VARCHAR(32);