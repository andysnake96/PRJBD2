-- Table: public.star

-- DROP TABLE public.star;

CREATE TABLE public.star
(
  id integer NOT NULL,
  name character varying(64),
  glon double precision NOT NULL,
  glat double precision NOT NULL,
  flux double precision,
  type character varying(64),
  satellite character varying(64),
  CONSTRAINT star_pkey PRIMARY KEY (id),
  CONSTRAINT star_satellite_fkey FOREIGN KEY (satellite)
      REFERENCES public.satellite (name) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.star
  OWNER TO postgres;
