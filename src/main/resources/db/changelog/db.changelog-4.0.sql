--liquibase formatted sql

--changeset amarsel:1
ALTER TABLE users
ADD COLUMN password VARCHAR(128) default '{noop}123';

--changeset amarsel:2
ALTER TABLE users_aud
ADD COLUMN password VARCHAR(128);