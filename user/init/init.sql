
CREATE TABLE customer (
  id BIGINT NOT NULL,
   username VARCHAR(255),
   email VARCHAR(255),
   password VARCHAR(255),
   CONSTRAINT pk_customer PRIMARY KEY (id)
);

CREATE TABLE

CREATE SEQUENCE "user_id_sequence"
START 0
INCREMENT 1
OWNED BY customer.id;

