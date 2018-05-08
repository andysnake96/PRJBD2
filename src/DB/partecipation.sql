-- Table: public.partecipation

-- DROP TABLE public.partecipation;

CREATE TABLE public.partecipation
(
  agency character varying(64) NOT NULL,
  satellite character varying(64),
  CONSTRAINT partecipation_pkey PRIMARY KEY (agency),
  CONSTRAINT partecipation_satellite_fkey FOREIGN KEY (satellite)
      REFERENCES public.satellite (name) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.partecipation
  OWNER TO postgres;
