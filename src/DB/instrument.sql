-- Table: public.instrument

-- DROP TABLE public.instrument;

CREATE TABLE public.instrument
(
  name character varying(64) NOT NULL,
  satellite character varying(64),
  band double precision[],
  CONSTRAINT instrument_pkey PRIMARY KEY (name),
  CONSTRAINT instrument_satellite_fkey FOREIGN KEY (satellite)
      REFERENCES public.satellite (name) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.instrument
  OWNER TO postgres;
