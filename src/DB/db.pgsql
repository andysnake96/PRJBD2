PGDMP     :                    v        	   DBProject    9.6.6    9.6.6 '    �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                       false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                       false            �           1262    24959 	   DBProject    DATABASE     }   CREATE DATABASE "DBProject" WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'it_IT.UTF-8' LC_CTYPE = 'it_IT.UTF-8';
    DROP DATABASE "DBProject";
             postgres    false                        2615    2200    public    SCHEMA        CREATE SCHEMA public;
    DROP SCHEMA public;
             postgres    false            �           0    0    SCHEMA public    COMMENT     6   COMMENT ON SCHEMA public IS 'standard public schema';
                  postgres    false    3                        3079    12431    plpgsql 	   EXTENSION     ?   CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;
    DROP EXTENSION plpgsql;
                  false            �           0    0    EXTENSION plpgsql    COMMENT     @   COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';
                       false    1            �            1259    24999    filament    TABLE     4  CREATE TABLE filament (
    name character varying(64) NOT NULL,
    id integer NOT NULL,
    fluxtot double precision,
    ellipticty double precision,
    contrast double precision,
    densavg double precision,
    tempavg double precision,
    namestr character varying(64) NOT NULL,
    nseg integer
);
    DROP TABLE public.filament;
       public         postgres    false    3            �            1259    25006 
   instrument    TABLE     �   CREATE TABLE instrument (
    name character varying(64) NOT NULL,
    satellite character varying(64),
    band double precision[]
);
    DROP TABLE public.instrument;
       public         postgres    false    3            �            1259    25066    outline    TABLE     �   CREATE TABLE outline (
    idfil integer NOT NULL,
    namestr character varying(64) NOT NULL,
    glon double precision NOT NULL,
    glat double precision NOT NULL
);
    DROP TABLE public.outline;
       public         postgres    false    3            �            1259    25019    partecipation    TABLE     o   CREATE TABLE partecipation (
    agency character varying(64) NOT NULL,
    satellite character varying(64)
);
 !   DROP TABLE public.partecipation;
       public         postgres    false    3            �            1259    24960 	   satellite    TABLE       CREATE TABLE satellite (
    name character varying(64) NOT NULL,
    startdate date NOT NULL,
    type character varying(32),
    enddate date,
    CONSTRAINT satellite_type_check CHECK ((((type)::text = 'ended'::text) OR ((type)::text = 'not ended'::text)))
);
    DROP TABLE public.satellite;
       public         postgres    false    3            �            1259    25040    skeletonpoint    TABLE     �  CREATE TABLE skeletonpoint (
    idseg integer NOT NULL,
    n integer NOT NULL,
    flux double precision,
    type character(1),
    glon double precision NOT NULL,
    glat double precision NOT NULL,
    idfil integer NOT NULL,
    namestr character varying(64) NOT NULL,
    CONSTRAINT skeletonpoint_type_check CHECK ((((type IS NOT NULL) AND (type = 'B'::bpchar)) OR (type = 'S'::bpchar)))
);
 !   DROP TABLE public.skeletonpoint;
       public         postgres    false    3            �            1259    24976    star    TABLE     �   CREATE TABLE star (
    id integer NOT NULL,
    name character varying(64),
    glon double precision NOT NULL,
    glat double precision NOT NULL,
    flux double precision,
    type character varying(64),
    satellite character varying(64)
);
    DROP TABLE public.star;
       public         postgres    false    3            �            1259    25118 
   usersistem    TABLE     �  CREATE TABLE usersistem (
    name character varying(32) NOT NULL,
    surname character varying(32) NOT NULL,
    username character varying(32) NOT NULL,
    password character varying(32) NOT NULL,
    email character varying(32),
    type character varying(16) NOT NULL,
    CONSTRAINT user_type_check CHECK ((((type)::text = 'Administrator'::text) OR ((type)::text = 'User'::text)))
);
    DROP TABLE public.usersistem;
       public         postgres    false    3            �          0    24999    filament 
   TABLE DATA               e   COPY filament (name, id, fluxtot, ellipticty, contrast, densavg, tempavg, namestr, nseg) FROM stdin;
    public       postgres    false    187   6.       �          0    25006 
   instrument 
   TABLE DATA               4   COPY instrument (name, satellite, band) FROM stdin;
    public       postgres    false    188   S.       �          0    25066    outline 
   TABLE DATA               6   COPY outline (idfil, namestr, glon, glat) FROM stdin;
    public       postgres    false    191   �.       �          0    25019    partecipation 
   TABLE DATA               3   COPY partecipation (agency, satellite) FROM stdin;
    public       postgres    false    189   �.       �          0    24960 	   satellite 
   TABLE DATA               <   COPY satellite (name, startdate, type, enddate) FROM stdin;
    public       postgres    false    185   4/       �          0    25040    skeletonpoint 
   TABLE DATA               R   COPY skeletonpoint (idseg, n, flux, type, glon, glat, idfil, namestr) FROM stdin;
    public       postgres    false    190   �/       �          0    24976    star 
   TABLE DATA               D   COPY star (id, name, glon, glat, flux, type, satellite) FROM stdin;
    public       postgres    false    186   �/       �          0    25118 
   usersistem 
   TABLE DATA               M   COPY usersistem (name, surname, username, password, email, type) FROM stdin;
    public       postgres    false    192   �/                  2606    25005     filament filament_id_namestr_key 
   CONSTRAINT     [   ALTER TABLE ONLY filament
    ADD CONSTRAINT filament_id_namestr_key UNIQUE (id, namestr);
 J   ALTER TABLE ONLY public.filament DROP CONSTRAINT filament_id_namestr_key;
       public         postgres    false    187    187    187                        2606    25003    filament filament_pkey 
   CONSTRAINT     O   ALTER TABLE ONLY filament
    ADD CONSTRAINT filament_pkey PRIMARY KEY (name);
 @   ALTER TABLE ONLY public.filament DROP CONSTRAINT filament_pkey;
       public         postgres    false    187    187            #           2606    25013    instrument instrument_pkey 
   CONSTRAINT     S   ALTER TABLE ONLY instrument
    ADD CONSTRAINT instrument_pkey PRIMARY KEY (name);
 D   ALTER TABLE ONLY public.instrument DROP CONSTRAINT instrument_pkey;
       public         postgres    false    188    188            )           2606    25159    outline outline_pkey 
   CONSTRAINT     c   ALTER TABLE ONLY outline
    ADD CONSTRAINT outline_pkey PRIMARY KEY (namestr, idfil, glon, glat);
 >   ALTER TABLE ONLY public.outline DROP CONSTRAINT outline_pkey;
       public         postgres    false    191    191    191    191    191            %           2606    25023     partecipation partecipation_pkey 
   CONSTRAINT     [   ALTER TABLE ONLY partecipation
    ADD CONSTRAINT partecipation_pkey PRIMARY KEY (agency);
 J   ALTER TABLE ONLY public.partecipation DROP CONSTRAINT partecipation_pkey;
       public         postgres    false    189    189                       2606    24965    satellite satellite_pkey 
   CONSTRAINT     Q   ALTER TABLE ONLY satellite
    ADD CONSTRAINT satellite_pkey PRIMARY KEY (name);
 B   ALTER TABLE ONLY public.satellite DROP CONSTRAINT satellite_pkey;
       public         postgres    false    185    185            '           2606    25161     skeletonpoint skeletonpoint_pkey 
   CONSTRAINT     f   ALTER TABLE ONLY skeletonpoint
    ADD CONSTRAINT skeletonpoint_pkey PRIMARY KEY (idseg, n, namestr);
 J   ALTER TABLE ONLY public.skeletonpoint DROP CONSTRAINT skeletonpoint_pkey;
       public         postgres    false    190    190    190    190                       2606    24980    star star_pkey 
   CONSTRAINT     E   ALTER TABLE ONLY star
    ADD CONSTRAINT star_pkey PRIMARY KEY (id);
 8   ALTER TABLE ONLY public.star DROP CONSTRAINT star_pkey;
       public         postgres    false    186    186            +           2606    25123    usersistem user_pkey 
   CONSTRAINT     Q   ALTER TABLE ONLY usersistem
    ADD CONSTRAINT user_pkey PRIMARY KEY (username);
 >   ALTER TABLE ONLY public.usersistem DROP CONSTRAINT user_pkey;
       public         postgres    false    192    192            !           1259    25129    fki_foreign key    INDEX     B   CREATE INDEX "fki_foreign key" ON filament USING btree (namestr);
 %   DROP INDEX public."fki_foreign key";
       public         postgres    false    187            -           2606    25124    filament foreign key    FK CONSTRAINT     n   ALTER TABLE ONLY filament
    ADD CONSTRAINT "foreign key" FOREIGN KEY (namestr) REFERENCES instrument(name);
 @   ALTER TABLE ONLY public.filament DROP CONSTRAINT "foreign key";
       public       postgres    false    2083    187    188            .           2606    25014 $   instrument instrument_satellite_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY instrument
    ADD CONSTRAINT instrument_satellite_fkey FOREIGN KEY (satellite) REFERENCES satellite(name) ON DELETE CASCADE;
 N   ALTER TABLE ONLY public.instrument DROP CONSTRAINT instrument_satellite_fkey;
       public       postgres    false    188    185    2074            1           2606    25076    outline outline_idfil_fkey    FK CONSTRAINT     ~   ALTER TABLE ONLY outline
    ADD CONSTRAINT outline_idfil_fkey FOREIGN KEY (idfil, namestr) REFERENCES filament(id, namestr);
 D   ALTER TABLE ONLY public.outline DROP CONSTRAINT outline_idfil_fkey;
       public       postgres    false    187    191    191    187    2078            /           2606    25024 *   partecipation partecipation_satellite_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY partecipation
    ADD CONSTRAINT partecipation_satellite_fkey FOREIGN KEY (satellite) REFERENCES satellite(name) ON DELETE CASCADE;
 T   ALTER TABLE ONLY public.partecipation DROP CONSTRAINT partecipation_satellite_fkey;
       public       postgres    false    189    185    2074            0           2606    25051 &   skeletonpoint skeletonpoint_idfil_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY skeletonpoint
    ADD CONSTRAINT skeletonpoint_idfil_fkey FOREIGN KEY (idfil, namestr) REFERENCES filament(id, namestr) ON UPDATE CASCADE ON DELETE CASCADE;
 P   ALTER TABLE ONLY public.skeletonpoint DROP CONSTRAINT skeletonpoint_idfil_fkey;
       public       postgres    false    190    2078    187    187    190            ,           2606    24981    star star_satellite_fkey    FK CONSTRAINT     q   ALTER TABLE ONLY star
    ADD CONSTRAINT star_satellite_fkey FOREIGN KEY (satellite) REFERENCES satellite(name);
 B   ALTER TABLE ONLY public.star DROP CONSTRAINT star_satellite_fkey;
       public       postgres    false    186    185    2074            �      x������ � �      �      x�pt��H-*N�H��67�143��
�rE725�1bS��g��3gpAfIUjg����*��1�3�1�3�DF:�\����F&�\)i�C=CT]�f:�z�h6������r��qqq �!4Q      �      x������ � �      �   %   x�sv��H-*N�H���s�2K�R��b���� ���      �   [   x��H-*N�H��420��50�54�L�KIM
����sd�T�����Z�Հ4���re��T�4Y���@,�b���� M��      �      x������ � �      �      x������ � �      �   S   x���,���,�,JD'Sr3�2�K�K�r��83Rsr�s�9�3�"��Z����"9�.=-�*�4�4'�0F��� X+�     