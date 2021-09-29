CREATE TABLE public.customer (
  id BIGINT CONSTRAINT customer_pk_id PRIMARY KEY,
  email VARCHAR NOT NULL CONSTRAINT customer_unique_email UNIQUE,
  first_name VARCHAR(64) NOT NULL,
  last_name VARCHAR(64) NOT NULL
);

CREATE SEQUENCE customer_seq_id OWNED BY public.customer.id;

CREATE TABLE public.order (
  id BIGINT CONSTRAINT order_pk_id PRIMARY KEY,
  uuid VARCHAR NOT NULL CONSTRAINT order_unique_uuid UNIQUE,
  customer_id BIGINT,
  total_in_cents INT,
  created timestamp without time zone NOT NULL
);

CREATE SEQUENCE order_seq_id OWNED BY public.order.id;