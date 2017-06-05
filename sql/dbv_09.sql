--
-- PostgreSQL database dump
--

-- Dumped from database version 9.4.7
-- Dumped by pg_dump version 9.4.7
-- Started on 2017-06-02 16:49:22

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 1 (class 3079 OID 11855)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2109 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


--
-- TOC entry 3 (class 3079 OID 90352)
-- Name: pgcrypto; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS pgcrypto WITH SCHEMA public;


--
-- TOC entry 2110 (class 0 OID 0)
-- Dependencies: 3
-- Name: EXTENSION pgcrypto; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION pgcrypto IS 'cryptographic functions';


--
-- TOC entry 2 (class 3079 OID 90387)
-- Name: uuid-ossp; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS "uuid-ossp" WITH SCHEMA public;


--
-- TOC entry 2111 (class 0 OID 0)
-- Dependencies: 2
-- Name: EXTENSION "uuid-ossp"; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION "uuid-ossp" IS 'generate universally unique identifiers (UUIDs)';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 175 (class 1259 OID 90398)
-- Name: category_budgets; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE category_budgets (
    cb_id uuid DEFAULT uuid_generate_v4() NOT NULL,
    cb_category_id uuid NOT NULL,
    cb_amount double precision NOT NULL,
    cb_active boolean NOT NULL,
    cb_budget uuid NOT NULL
);


ALTER TABLE category_budgets OWNER TO postgres;

--
-- TOC entry 176 (class 1259 OID 90402)
-- Name: payment_categories; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE payment_categories (
    pcat_id uuid NOT NULL,
    pcat_name text NOT NULL,
    pcat_user uuid NOT NULL,
    pcat_active boolean NOT NULL,
    pcat_description text,
    pcat_type integer NOT NULL
);


ALTER TABLE payment_categories OWNER TO postgres;

--
-- TOC entry 177 (class 1259 OID 90408)
-- Name: payment_types; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE payment_types (
    ptype_name text NOT NULL,
    ptype_description text,
    ptype_active boolean NOT NULL,
    ptype_id integer NOT NULL
);


ALTER TABLE payment_types OWNER TO postgres;

--
-- TOC entry 184 (class 1259 OID 90498)
-- Name: payment_types_ptype_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE payment_types_ptype_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE payment_types_ptype_id_seq OWNER TO postgres;

--
-- TOC entry 2112 (class 0 OID 0)
-- Dependencies: 184
-- Name: payment_types_ptype_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE payment_types_ptype_id_seq OWNED BY payment_types.ptype_id;


--
-- TOC entry 178 (class 1259 OID 90415)
-- Name: payments; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE payments (
    p_id uuid NOT NULL,
    p_category uuid NOT NULL,
    p_amount double precision NOT NULL,
    p_date timestamp without time zone NOT NULL,
    p_description text,
    p_active boolean NOT NULL
);


ALTER TABLE payments OWNER TO postgres;

--
-- TOC entry 179 (class 1259 OID 90421)
-- Name: persistent_logins; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE persistent_logins (
    username character varying(64) NOT NULL,
    series character varying(64) NOT NULL,
    token character varying(64) NOT NULL,
    last_used timestamp without time zone NOT NULL
);


ALTER TABLE persistent_logins OWNER TO postgres;

--
-- TOC entry 180 (class 1259 OID 90424)
-- Name: user_budgets; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE user_budgets (
    ub_id uuid NOT NULL,
    ub_user uuid NOT NULL,
    ub_from date NOT NULL,
    ub_to date NOT NULL
);


ALTER TABLE user_budgets OWNER TO postgres;

--
-- TOC entry 181 (class 1259 OID 90427)
-- Name: user_role_gen_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE user_role_gen_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE user_role_gen_id OWNER TO postgres;

--
-- TOC entry 182 (class 1259 OID 90429)
-- Name: user_roles; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE user_roles (
    user_role_id integer DEFAULT nextval('user_role_gen_id'::regclass) NOT NULL,
    username text NOT NULL,
    user_role text NOT NULL
);


ALTER TABLE user_roles OWNER TO postgres;

--
-- TOC entry 183 (class 1259 OID 90436)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE users (
    user_userid uuid DEFAULT uuid_generate_v4() NOT NULL,
    user_email character varying(255),
    user_enabled boolean NOT NULL,
    user_firstname character varying(255),
    user_lastname character varying(255),
    user_middlename character varying(255),
    user_password character varying(255) NOT NULL,
    user_username character varying(255) NOT NULL
);


ALTER TABLE users OWNER TO postgres;

--
-- TOC entry 1963 (class 2604 OID 90500)
-- Name: ptype_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY payment_types ALTER COLUMN ptype_id SET DEFAULT nextval('payment_types_ptype_id_seq'::regclass);


--
-- TOC entry 1967 (class 2606 OID 90444)
-- Name: category_budget_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY category_budgets
    ADD CONSTRAINT category_budget_pkey PRIMARY KEY (cb_id);


--
-- TOC entry 1969 (class 2606 OID 90446)
-- Name: payment_category_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY payment_categories
    ADD CONSTRAINT payment_category_pkey PRIMARY KEY (pcat_id);


--
-- TOC entry 1971 (class 2606 OID 90508)
-- Name: payment_types_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY payment_types
    ADD CONSTRAINT payment_types_pkey PRIMARY KEY (ptype_id);


--
-- TOC entry 1973 (class 2606 OID 90450)
-- Name: payments_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY payments
    ADD CONSTRAINT payments_pkey PRIMARY KEY (p_id);


--
-- TOC entry 1975 (class 2606 OID 90452)
-- Name: persistent_logins_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY persistent_logins
    ADD CONSTRAINT persistent_logins_pkey PRIMARY KEY (series);


--
-- TOC entry 1979 (class 2606 OID 90454)
-- Name: uni_username_role; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY user_roles
    ADD CONSTRAINT uni_username_role UNIQUE (user_role, username);


--
-- TOC entry 1977 (class 2606 OID 90456)
-- Name: user_budgets_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY user_budgets
    ADD CONSTRAINT user_budgets_pkey PRIMARY KEY (ub_id);


--
-- TOC entry 1981 (class 2606 OID 90458)
-- Name: user_roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY user_roles
    ADD CONSTRAINT user_roles_pkey PRIMARY KEY (user_role_id);


--
-- TOC entry 1983 (class 2606 OID 90460)
-- Name: users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (user_userid);


--
-- TOC entry 1985 (class 2606 OID 90462)
-- Name: users_user_username_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_user_username_key UNIQUE (user_username);


--
-- TOC entry 1986 (class 2606 OID 90463)
-- Name: budgetfk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY category_budgets
    ADD CONSTRAINT budgetfk FOREIGN KEY (cb_budget) REFERENCES user_budgets(ub_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 1987 (class 2606 OID 90468)
-- Name: paymentCategoryFk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY category_budgets
    ADD CONSTRAINT "paymentCategoryFk" FOREIGN KEY (cb_category_id) REFERENCES payment_categories(pcat_id);


--
-- TOC entry 1989 (class 2606 OID 90509)
-- Name: payment_categories_pcat_type_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY payment_categories
    ADD CONSTRAINT payment_categories_pcat_type_fkey FOREIGN KEY (pcat_type) REFERENCES payment_types(ptype_id);


--
-- TOC entry 1990 (class 2606 OID 90473)
-- Name: payment_catfk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY payments
    ADD CONSTRAINT payment_catfk FOREIGN KEY (p_category) REFERENCES payment_categories(pcat_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 1988 (class 2606 OID 90483)
-- Name: pcat_userfk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY payment_categories
    ADD CONSTRAINT pcat_userfk FOREIGN KEY (pcat_user) REFERENCES users(user_userid) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 1991 (class 2606 OID 90488)
-- Name: ub_userfk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY user_budgets
    ADD CONSTRAINT ub_userfk FOREIGN KEY (ub_user) REFERENCES users(user_userid) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 1992 (class 2606 OID 90493)
-- Name: user_roles_username_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY user_roles
    ADD CONSTRAINT user_roles_username_fkey FOREIGN KEY (username) REFERENCES users(user_username);


--
-- TOC entry 2108 (class 0 OID 0)
-- Dependencies: 8
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


insert into payment_types (ptype_name, ptype_description, ptype_active)
VALUES ('Приходи', 'Приходи', true);
insert into payment_types (ptype_name, ptype_description, ptype_active)
VALUES ('Разходи', 'Разходи', true);


-- Completed on 2017-06-02 16:49:22

--
-- PostgreSQL database dump complete
--

