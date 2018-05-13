-- Table: public.filament

-- DROP TABLE public.filament;

CREATE TABLE public.filament
(
  name character varying(64) NOT NULL,
    id integer NOT NULL,
    fluxtot double precision,
    ellipticty double precision,
    contrast double precision,
    densavg double precision,
    tempavg double precision,
    namestr character varying(64) NOT NULL,
    nSeg integer,
    CONSTRAINT filament_pkey PRIMARY KEY (name),
    CONSTRAINT "foreign key" FOREIGN KEY (namestr)
        REFERENCES public.instrument (name) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT filament_id_namestr_key UNIQUE (id, namestr)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.filament
  OWNER TO postgres;
