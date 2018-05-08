-- Table: public.outline

-- DROP TABLE public.outline;

CREATE TABLE public.outline
(
  idfil integer NOT NULL,
  namestr character varying(64) NOT NULL,
  glon integer NOT NULL,
  glat integer NOT NULL,
  CONSTRAINT outline_pkey PRIMARY KEY (glon, glat),
  CONSTRAINT outline_idfil_fkey FOREIGN KEY (idfil, namestr)
      REFERENCES public.filament (id, namestr) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.outline
  OWNER TO postgres;
