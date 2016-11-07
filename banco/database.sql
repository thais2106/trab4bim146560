--
-- PostgreSQL database dump
--

-- Dumped from database version 9.3.15
-- Dumped by pg_dump version 9.5.1

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: db_estudo_java; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE db_estudo_java WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'Portuguese_Brazil.1252' LC_CTYPE = 'Portuguese_Brazil.1252';


ALTER DATABASE db_estudo_java OWNER TO postgres;

\connect db_estudo_java

SET statement_timeout = 0;
SET lock_timeout = 0;
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
-- Name: tb_pessoa; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE tb_pessoa (
    id_pessoa integer NOT NULL,
    nm_pessoa character varying(70) NOT NULL,
    fl_sexo character(1) NOT NULL,
    dt_cadastro date NOT NULL,
    ds_email character varying(80) NOT NULL,
    ds_endereco character varying(200) NOT NULL,
    fl_origemcadastro character(1) NOT NULL,
    id_usuario_cadastro integer NOT NULL
);


ALTER TABLE tb_pessoa OWNER TO postgres;

--
-- Name: tb_pessoa_id_pessoa_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE tb_pessoa_id_pessoa_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE tb_pessoa_id_pessoa_seq OWNER TO postgres;

--
-- Name: tb_pessoa_id_pessoa_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE tb_pessoa_id_pessoa_seq OWNED BY tb_pessoa.id_pessoa;


--
-- Name: tb_usuario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE tb_usuario (
    id_usuario integer NOT NULL,
    ds_login character varying(30),
    ds_senha character varying(30)
);


ALTER TABLE tb_usuario OWNER TO postgres;

--
-- Name: tb_usuario_id_usuario_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE tb_usuario_id_usuario_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE tb_usuario_id_usuario_seq OWNER TO postgres;

--
-- Name: tb_usuario_id_usuario_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE tb_usuario_id_usuario_seq OWNED BY tb_usuario.id_usuario;


--
-- Name: id_pessoa; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tb_pessoa ALTER COLUMN id_pessoa SET DEFAULT nextval('tb_pessoa_id_pessoa_seq'::regclass);


--
-- Name: id_usuario; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tb_usuario ALTER COLUMN id_usuario SET DEFAULT nextval('tb_usuario_id_usuario_seq'::regclass);


--
-- Data for Name: tb_pessoa; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: tb_pessoa_id_pessoa_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('tb_pessoa_id_pessoa_seq', 1, false);


--
-- Data for Name: tb_usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO tb_usuario (id_usuario, ds_login, ds_senha) VALUES (1, 'admin', '123456');


--
-- Name: tb_usuario_id_usuario_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('tb_usuario_id_usuario_seq', 1, true);


--
-- Name: tb_pessoa_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tb_pessoa
    ADD CONSTRAINT tb_pessoa_pkey PRIMARY KEY (id_pessoa);


--
-- Name: tb_usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tb_usuario
    ADD CONSTRAINT tb_usuario_pkey PRIMARY KEY (id_usuario);


--
-- Name: fk_usuario; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tb_pessoa
    ADD CONSTRAINT fk_usuario FOREIGN KEY (id_usuario_cadastro) REFERENCES tb_usuario(id_usuario) MATCH FULL;


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

