-- Table: public.skeletonpoint

-- DROP TABLE public.skeletonpoint;

CREATE TABLE public.skeletonpoint
(
  idseg integer NOT NULL,
  n integer NOT NULL,
  flux double precision,
  type character(1),
  glon double precision NOT NULL,
  glat double precision NOT NULL,
  idfil integer NOT NULL,
  namestr character varying(64) NOT NULL,
  CONSTRAINT skeletonpoint_pkey PRIMARY KEY (idseg, n, namestr),
  CONSTRAINT skeletonpoint_idfil_fkey FOREIGN KEY (idfil, namestr)
      REFERENCES public.filament (id, namestr) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT skeletonpoint_type_check CHECK (type IS NOT NULL AND type = 'B'::bpchar OR type = 'S'::bpchar)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.skeletonpoint
  OWNER TO postgres;
