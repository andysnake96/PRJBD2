--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.6
-- Dumped by pg_dump version 9.6.6

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: filament; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE filament (
    name character varying(64) NOT NULL,
    id integer NOT NULL,
    fluxtot double precision,
    ellipticty double precision,
    contrast double precision,
    densavg double precision,
    tempavg double precision,
    nseg integer,
    namestr character varying(64) NOT NULL
);


ALTER TABLE filament OWNER TO postgres;

--
-- Name: instrument; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE instrument (
    name character varying(64) NOT NULL,
    satellite character varying(64),
    band double precision[]
);


ALTER TABLE instrument OWNER TO postgres;

--
-- Name: outline; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE outline (
    idfil integer NOT NULL,
    namestr character varying(64) NOT NULL,
    glon integer NOT NULL,
    glat integer NOT NULL
);


ALTER TABLE outline OWNER TO postgres;

--
-- Name: partecipation; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE partecipation (
    agency character varying(64) NOT NULL,
    satellite character varying(64)
);


ALTER TABLE partecipation OWNER TO postgres;

--
-- Name: satellite; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE satellite (
    name character varying(64) NOT NULL,
    startdate date NOT NULL,
    type character varying(32),
    enddate date,
    CONSTRAINT satellite_type_check CHECK ((((type)::text = 'ended'::text) OR ((type)::text = 'not ended'::text)))
);


ALTER TABLE satellite OWNER TO postgres;

--
-- Name: skeletonpoint; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE skeletonpoint (
    idseg integer NOT NULL,
    n integer NOT NULL,
    flux double precision,
    type character(1),
    glon integer NOT NULL,
    glat integer NOT NULL,
    idfil integer NOT NULL,
    namestr character varying(64) NOT NULL,
    CONSTRAINT skeletonpoint_type_check CHECK ((((type IS NOT NULL) AND (type = 'B'::bpchar)) OR (type = 'S'::bpchar)))
);


ALTER TABLE skeletonpoint OWNER TO postgres;

--
-- Name: star; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE star (
    id integer NOT NULL,
    name character varying(64),
    glon integer NOT NULL,
    glat integer NOT NULL,
    flux double precision,
    type character varying(64),
    satellite character varying(64)
);


ALTER TABLE star OWNER TO postgres;

--
-- Data for Name: filament; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY filament (name, id, fluxtot, ellipticty, contrast, densavg, tempavg, nseg, namestr) FROM stdin;
\.


--
-- Data for Name: instrument; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY instrument (name, satellite, band) FROM stdin;
Herschel-PACS	Herschel	{70,160}
Herschel-SPIRE	Herschel	{250,350,500}
Spitzer-IRAC	Spitzer	{3.60000000000000009,4.5,5.79999999999999982,8}
Spitzer-MIPS	Spitzer	{24}
\.


--
-- Data for Name: outline; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY outline (idfil, namestr, glon, glat) FROM stdin;
\.


--
-- Data for Name: partecipation; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY partecipation (agency, satellite) FROM stdin;
ESA	Herschel
NASA	Spitzer
\.


--
-- Data for Name: satellite; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY satellite (name, startdate, type, enddate) FROM stdin;
Herschel	2009-07-10	ended	2013-06-17
Spitzer	2003-12-18	ended	2009-05-15
\.


--
-- Data for Name: skeletonpoint; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY skeletonpoint (idseg, n, flux, type, glon, glat, idfil, namestr) FROM stdin;
\.


--
-- Data for Name: star; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY star (id, name, glon, glat, flux, type, satellite) FROM stdin;
\.


--
-- Name: filament filament_id_namestr_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY filament
    ADD CONSTRAINT filament_id_namestr_key UNIQUE (id, namestr);


--
-- Name: filament filament_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY filament
    ADD CONSTRAINT filament_pkey PRIMARY KEY (name);


--
-- Name: instrument instrument_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY instrument
    ADD CONSTRAINT instrument_pkey PRIMARY KEY (name);


--
-- Name: outline outline_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY outline
    ADD CONSTRAINT outline_pkey PRIMARY KEY (glon, glat);


--
-- Name: partecipation partecipation_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY partecipation
    ADD CONSTRAINT partecipation_pkey PRIMARY KEY (agency);


--
-- Name: satellite satellite_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY satellite
    ADD CONSTRAINT satellite_pkey PRIMARY KEY (name);


--
-- Name: skeletonpoint skeletonpoint_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY skeletonpoint
    ADD CONSTRAINT skeletonpoint_pkey PRIMARY KEY (idseg, n);


--
-- Name: star star_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY star
    ADD CONSTRAINT star_pkey PRIMARY KEY (id);


--
-- Name: instrument instrument_satellite_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY instrument
    ADD CONSTRAINT instrument_satellite_fkey FOREIGN KEY (satellite) REFERENCES satellite(name) ON DELETE CASCADE;


--
-- Name: outline outline_idfil_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY outline
    ADD CONSTRAINT outline_idfil_fkey FOREIGN KEY (idfil, namestr) REFERENCES filament(id, namestr);


--
-- Name: partecipation partecipation_satellite_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY partecipation
    ADD CONSTRAINT partecipation_satellite_fkey FOREIGN KEY (satellite) REFERENCES satellite(name) ON DELETE CASCADE;


--
-- Name: skeletonpoint skeletonpoint_idfil_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY skeletonpoint
    ADD CONSTRAINT skeletonpoint_idfil_fkey FOREIGN KEY (idfil, namestr) REFERENCES filament(id, namestr) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: star star_satellite_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY star
    ADD CONSTRAINT star_satellite_fkey FOREIGN KEY (satellite) REFERENCES satellite(name);


--
-- PostgreSQL database dump complete
--

