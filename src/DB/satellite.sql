-- Table: public.satellite

-- DROP TABLE public.satellite;

CREATE TABLE public.satellite
(
  name character varying(64) NOT NULL,
  startdate date NOT NULL,
  type character varying(32),
  enddate date,
  CONSTRAINT satellite_pkey PRIMARY KEY (name),
  CONSTRAINT satellite_type_check CHECK (type::text = 'ended'::text OR type::text = 'not ended'::text)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.satellite
  OWNER TO postgres;
