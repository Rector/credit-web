CREATE TABLE clients (
    uuid                                VARCHAR(36) PRIMARY KEY,
    full_name                           VARCHAR(80) NOT NULL,
    phone_number                        VARCHAR(20) NOT NULL,
    email                               VARCHAR(80) NOT NULL,
    passport_number                     VARCHAR(15) UNIQUE
);

INSERT INTO clients (uuid, full_name, phone_number, email, passport_number)
VALUES
('751252dc-a432-4533-8516-c5f4cbb1c192', 'Rutov Vladimir Viktorovich', '+79271111111', 'vlad@yandex.ru', '100200'),
('cea845c5-ee61-4564-a2e1-f59d53893b50', 'Kapustin Fedor Ivanovich', '+79032222222', 'fedor@mail.ru', '300400');

CREATE TABLE credits (
    uuid                                VARCHAR(36) PRIMARY KEY,
    credit_limit                        NUMERIC(15, 2),
    interest_rate                       NUMERIC(6, 2)
);

CREATE UNIQUE INDEX idx_limit_interest_rate ON credits (credit_limit, interest_rate);

INSERT INTO credits (uuid, credit_limit, interest_rate)
VALUES
('d12b6803-d899-40a4-ae0c-3a604e7d2c2f', 150000, 10),
('427395c3-755f-4d87-a895-1dd9193121c2', 50000.5, 10.5);

CREATE TABLE banks (
    uuid                                VARCHAR(36) PRIMARY KEY
);

CREATE TABLE banks_clients (
    bank_uuid                           VARCHAR(36) REFERENCES banks (uuid),
    client_uuid                         VARCHAR(36) REFERENCES clients (uuid),
    PRIMARY KEY (bank_uuid, client_uuid)
);

CREATE TABLE banks_credits (
    bank_uuid                           VARCHAR(36) REFERENCES banks (uuid),
    credit_uuid                         VARCHAR(36) REFERENCES credits (uuid),
    PRIMARY KEY (bank_uuid, credit_uuid)
);

INSERT INTO banks (uuid)
VALUES
('59f4c890-4acd-49b3-b589-1560c92b28ce');

INSERT INTO banks_clients (bank_uuid, client_uuid)
VALUES
('59f4c890-4acd-49b3-b589-1560c92b28ce', '751252dc-a432-4533-8516-c5f4cbb1c192');

INSERT INTO banks_credits (bank_uuid, credit_uuid)
VALUES
('59f4c890-4acd-49b3-b589-1560c92b28ce', 'd12b6803-d899-40a4-ae0c-3a604e7d2c2f');

CREATE TABLE payment_schedules (
    uuid                                VARCHAR(36) PRIMARY KEY,
    payment_date                        DATE,
    payment_sum                         NUMERIC(15, 2),
    repayment_sum_credit_body           NUMERIC(15, 2),
    repayment_sum_interest              NUMERIC(15, 2)
);

CREATE TABLE credit_offers (
    uuid                                VARCHAR(36) PRIMARY KEY,
    client_uuid                         VARCHAR(36) REFERENCES clients (uuid),
    credit_uuid                         VARCHAR(36) REFERENCES credits (uuid),
    credit_sum                          NUMERIC(15, 2)
);

CREATE TABLE credit_offers_payment_schedules (
    credit_offer_uuid                   VARCHAR(36) REFERENCES credit_offers (uuid),
    payment_schedule_uuid               VARCHAR(36) REFERENCES payment_schedules (uuid),
    PRIMARY KEY (credit_offer_uuid, payment_schedule_uuid)
);

INSERT INTO credit_offers (uuid, client_uuid, credit_uuid, credit_sum)
VALUES
('681e7e6a-9493-4edb-8b62-22cc98dd44cf', 'cea845c5-ee61-4564-a2e1-f59d53893b50', '427395c3-755f-4d87-a895-1dd9193121c2', 50000.5);

INSERT INTO payment_schedules (uuid, payment_date, payment_sum, repayment_sum_credit_body, repayment_sum_interest)
VALUES
('df25f73b-6812-452b-ab0b-2c3eaa71f994', '2023-01-13', 4604.2, 4166.7, 437.5),
('4f1a3a87-03c5-4584-9172-a4af0e541019', '2023-02-13', 4567.75, 4166.7, 401.05),
('ee0fb9df-f87e-4686-9e3c-fc0f67baeafb', '2023-03-13', 4531.29, 4166.7, 364.59),
('6f551599-faab-4199-bdbd-6c48cdad3ee1', '2023-04-13', 4494.83, 4166.7, 328.13),
('aa7135a0-b048-48b4-a16a-6e872dab9a65', '2023-05-13', 4458.37, 4166.7, 291.67),
('c5ccd160-5644-457a-a888-995052d9b205', '2023-06-13', 4421.91, 4166.7, 255.21),
('437473a9-bf83-4ff4-959e-4cbb29e395f5', '2023-07-13', 4385.45, 4166.7, 218.75),
('a345019a-3df6-4808-b27d-96bd27611960', '2023-08-13', 4348.99, 4166.7, 182.29),
('91c8767e-f87b-4910-b533-ba30f285e91c', '2023-09-13', 4312.54, 4166.7, 145.84),
('04427af0-e4e8-420e-be4e-33214ab5e06c', '2023-10-13', 4276.08, 4166.7, 109.38),
('6f276e53-4c47-47f8-95c2-bf592b9333d6', '2023-11-13', 4239.62, 4166.7, 72.92),
('7ada33b6-eaa5-4636-b915-3808e96cac88', '2023-12-13', 4203.26, 4166.8, 36.46);


INSERT INTO credit_offers_payment_schedules (credit_offer_uuid, payment_schedule_uuid)
VALUES
('681e7e6a-9493-4edb-8b62-22cc98dd44cf', 'df25f73b-6812-452b-ab0b-2c3eaa71f994'),
('681e7e6a-9493-4edb-8b62-22cc98dd44cf', '4f1a3a87-03c5-4584-9172-a4af0e541019'),
('681e7e6a-9493-4edb-8b62-22cc98dd44cf', 'ee0fb9df-f87e-4686-9e3c-fc0f67baeafb'),
('681e7e6a-9493-4edb-8b62-22cc98dd44cf', '6f551599-faab-4199-bdbd-6c48cdad3ee1'),
('681e7e6a-9493-4edb-8b62-22cc98dd44cf', 'aa7135a0-b048-48b4-a16a-6e872dab9a65'),
('681e7e6a-9493-4edb-8b62-22cc98dd44cf', 'c5ccd160-5644-457a-a888-995052d9b205'),
('681e7e6a-9493-4edb-8b62-22cc98dd44cf', '437473a9-bf83-4ff4-959e-4cbb29e395f5'),
('681e7e6a-9493-4edb-8b62-22cc98dd44cf', 'a345019a-3df6-4808-b27d-96bd27611960'),
('681e7e6a-9493-4edb-8b62-22cc98dd44cf', '91c8767e-f87b-4910-b533-ba30f285e91c'),
('681e7e6a-9493-4edb-8b62-22cc98dd44cf', '04427af0-e4e8-420e-be4e-33214ab5e06c'),
('681e7e6a-9493-4edb-8b62-22cc98dd44cf', '6f276e53-4c47-47f8-95c2-bf592b9333d6'),
('681e7e6a-9493-4edb-8b62-22cc98dd44cf', '7ada33b6-eaa5-4636-b915-3808e96cac88');