CREATE TABLE public.customer (
  id BIGINT CONSTRAINT customer_pk_id PRIMARY KEY,
  email VARCHAR NOT NULL CONSTRAINT customer_unique_email UNIQUE
);

CREATE SEQUENCE customer_seq_id OWNED BY public.customer.id;

CREATE TABLE public.order (
  id BIGINT CONSTRAINT order_pk_id PRIMARY KEY,
  uuid VARCHAR NOT NULL CONSTRAINT order_unique_uuid UNIQUE,
  description VARCHAR NOT NULL,
  customer_id BIGINT NOT NULL,
  total_in_cents INT NOT NULL,
  created timestamp without time zone NOT NULL
);

CREATE SEQUENCE order_seq_id OWNED BY public.order.id;