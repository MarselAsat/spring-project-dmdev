--liquibase formatted sql

--changeset amarsel:1
ALTER TABLE users
ADD COLUMN image VARCHAR(128);

--changeset amarsel:2
ALTER TABLE users_aud
ADD COLUMN image VARCHAR(128);