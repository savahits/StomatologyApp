CREATE SEQUENCE IF NOT EXISTS roles_id_seq;
CREATE TABLE IF NOT EXISTS roles (
                                     id      BIGINT PRIMARY KEY DEFAULT nextval('roles_id_seq'),
    name    VARCHAR(255) NOT NULL UNIQUE
    );

CREATE SEQUENCE IF NOT EXISTS specialization_id_seq;
CREATE TABLE IF NOT EXISTS specialization (
                                              id   BIGINT PRIMARY KEY DEFAULT nextval('specialization_id_seq'),
    name VARCHAR(255) NOT NULL UNIQUE
    );

CREATE SEQUENCE IF NOT EXISTS users_id_seq;
CREATE TABLE IF NOT EXISTS users (
                                     id       BIGINT PRIMARY KEY DEFAULT nextval('users_id_seq'),
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role_id  BIGINT NOT NULL,
    CONSTRAINT fk_user_role FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE RESTRICT
    );

CREATE SEQUENCE IF NOT EXISTS client_id_seq;
CREATE TABLE IF NOT EXISTS client (
                                      id         BIGINT PRIMARY KEY DEFAULT nextval('client_id_seq'),
    surname    VARCHAR(100) NOT NULL,
    name       VARCHAR(100) NOT NULL,
    patronymic VARCHAR(100),
    phone      VARCHAR(20) NOT NULL,
    CONSTRAINT uk_client_phone UNIQUE (phone)
    );

CREATE SEQUENCE IF NOT EXISTS doctor_id_seq;
CREATE TABLE IF NOT EXISTS doctor (
                                      id                BIGINT PRIMARY KEY DEFAULT nextval('doctor_id_seq'),
    surname           VARCHAR(100) NOT NULL,
    name              VARCHAR(100) NOT NULL,
    patronymic        VARCHAR(100),
    phone             VARCHAR(255) NOT NULL,
    specialization_id BIGINT NOT NULL,
    user_id           BIGINT NOT NULL UNIQUE,
    CONSTRAINT fk_doctor_specialization FOREIGN KEY (specialization_id) REFERENCES specialization(id) ON DELETE RESTRICT,
    CONSTRAINT fk_doctor_user           FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
    );

CREATE SEQUENCE IF NOT EXISTS appointment_id_seq;
CREATE TABLE IF NOT EXISTS appointment (
                                           id               BIGINT PRIMARY KEY DEFAULT nextval('appointment_id_seq'),
    client_id        BIGINT NOT NULL,
    doctor_id        BIGINT NOT NULL,
    appointment_time TIMESTAMP NOT NULL,
    created_by       BIGINT NOT NULL,
    status           VARCHAR(255) NOT NULL,
    description      VARCHAR(255) NOT NULL DEFAULT '',
    been_before      BOOLEAN NOT NULL DEFAULT false,
    price            INTEGER NOT NULL,
    CONSTRAINT unique_doctor_time      UNIQUE (doctor_id, appointment_time),
    CONSTRAINT fk_appointment_client   FOREIGN KEY (client_id)  REFERENCES client(id)  ON DELETE RESTRICT,
    CONSTRAINT fk_appointment_doctor   FOREIGN KEY (doctor_id)  REFERENCES doctor(id)  ON DELETE RESTRICT,
    CONSTRAINT fk_appointment_user     FOREIGN KEY (created_by) REFERENCES users(id)   ON DELETE RESTRICT
    );