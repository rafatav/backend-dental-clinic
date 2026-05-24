INSERT INTO users (name, cpf, email, password, created_at, last_login, is_active) VALUES ('Rafael', '06161999999', 'rafael@teste.com', '123456', '2026-02-11 13:04:40', null, true);
INSERT INTO users (name, cpf, email, password, created_at, last_login, is_active) VALUES ('Alice', '06161888888', 'alice@teste.com', '123456', '2026-04-21 13:01:20', null, true);

INSERT INTO specialties (name) VALUES ('Emergência');
INSERT INTO specialties (name) VALUES ('Limpeza');
INSERT INTO specialties (name) VALUES ('Manutenção');

INSERT INTO dentists (name, cpf, email, cro, created_at, is_active) VALUES ('Alice', '06161888888', 'alice@teste.com', 'CRO-PR 00000', '2026-04-20 11:22:30', true);
INSERT INTO dentists (name, cpf, email, cro, created_at, is_active) VALUES ('Paulo', '06161777777', 'paulo@teste.com', 'CRO-PR 00001', '2026-03-15 10:06:10', true);
INSERT INTO dentists (name, cpf, email, cro, created_at, is_active) VALUES ('Lívia', '06161666666', 'livia@teste.com', 'CRO-PR 00002', '2026-03-14 09:23:23', true);

INSERT INTO patients (name, email, cpf, created_at, phone_number) VALUES ('Luan', 'luan@teste.com', '06161000000', '2026-05-15 15:44:40', '99099999');
INSERT INTO patients (name, email, cpf, created_at, phone_number) VALUES ('Maria', 'maria@teste.com', '06161111111', '2026-04-21 12:23:50', '99088888');
INSERT INTO patients (name, email, cpf, created_at, phone_number) VALUES ('Pedro', 'pedro@teste.com', '06161222222', '2026-03-15 13:06:00', '99077777');

INSERT INTO dentist_specialties (dentist_id, specialty_id) VALUES (1, 2);
INSERT INTO dentist_specialties (dentist_id, specialty_id) VALUES (2, 3);
INSERT INTO dentist_specialties (dentist_id, specialty_id) VALUES (3, 2);

INSERT INTO appointments (patient_id, dentist_id, user_id, description, cancellation_reason, start_time, end_time, booked_at, status) VALUES (2, 1, 2, 'Manutenção de canal', null, '2026-06-20 14:00:00', '2026-06-20 15:00:00', '2026-05-18 12:02:30', 'SCHEDULED');

