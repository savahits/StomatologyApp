ALTER TABLE client RENAME TO clients;
ALTER TABLE doctor RENAME TO doctors;
ALTER TABLE specialization RENAME TO specializations;
ALTER TABLE appointment RENAME TO appointments;

ALTER SEQUENCE client_id_seq RENAME TO clients_id_seq;
ALTER SEQUENCE doctor_id_seq RENAME TO doctors_id_seq;
ALTER SEQUENCE specialization_id_seq RENAME TO specializations_id_seq;
ALTER SEQUENCE appointment_id_seq RENAME TO appointments_id_seq;