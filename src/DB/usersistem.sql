-- Table: public.usersistem

-- DROP TABLE public.usersistem;

CREATE TABLE public.usersistem
(
  name character varying(32) NOT NULL,
  surname character varying(32) NOT NULL,
  username character varying(32) NOT NULL,
  password character varying(32) NOT NULL,
  email character varying(32),
  type character varying(16) NOT NULL,
  CONSTRAINT user_pkey PRIMARY KEY (username),
  CONSTRAINT user_type_check CHECK (type::text = 'Administrator'::text OR type::text = 'User'::text)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.usersistem
  OWNER TO postgres;