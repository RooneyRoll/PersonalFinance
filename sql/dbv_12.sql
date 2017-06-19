--
-- PostgreSQL database dump
--

-- Dumped from database version 9.4.7
-- Dumped by pg_dump version 9.4.7
-- Started on 2017-06-19 17:56:56

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
-- TOC entry 2130 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


--
-- TOC entry 3 (class 3079 OID 90756)
-- Name: pgcrypto; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS pgcrypto WITH SCHEMA public;


--
-- TOC entry 2131 (class 0 OID 0)
-- Dependencies: 3
-- Name: EXTENSION pgcrypto; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION pgcrypto IS 'cryptographic functions';


--
-- TOC entry 2 (class 3079 OID 90791)
-- Name: uuid-ossp; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS "uuid-ossp" WITH SCHEMA public;


--
-- TOC entry 2132 (class 0 OID 0)
-- Dependencies: 2
-- Name: EXTENSION "uuid-ossp"; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION "uuid-ossp" IS 'generate universally unique identifiers (UUIDs)';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 175 (class 1259 OID 90802)
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
-- TOC entry 176 (class 1259 OID 90806)
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
-- TOC entry 177 (class 1259 OID 90812)
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
-- TOC entry 178 (class 1259 OID 90818)
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
-- TOC entry 2133 (class 0 OID 0)
-- Dependencies: 178
-- Name: payment_types_ptype_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE payment_types_ptype_id_seq OWNED BY payment_types.ptype_id;


--
-- TOC entry 179 (class 1259 OID 90820)
-- Name: payments; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE payments (
    p_id uuid NOT NULL,
    p_category uuid NOT NULL,
    p_amount double precision NOT NULL,
    p_date timestamp without time zone NOT NULL,
    p_description text,
    p_active boolean NOT NULL,
    p_recurring_budget_payment uuid,
    p_covered_recurring_periods integer
);


ALTER TABLE payments OWNER TO postgres;

--
-- TOC entry 180 (class 1259 OID 90826)
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
-- TOC entry 181 (class 1259 OID 90829)
-- Name: recurring_budget_payments; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE recurring_budget_payments (
    rbp_id uuid NOT NULL,
    rbp_rec_type integer NOT NULL,
    rbp_periods integer NOT NULL,
    rbp_active boolean NOT NULL,
    rbp_date_start timestamp without time zone NOT NULL,
    rbp_amount double precision NOT NULL,
    rbp_user uuid NOT NULL,
    rbp_category uuid NOT NULL,
    rbp_title text NOT NULL,
    rbp_description text,
    rbv_finished boolean NOT NULL,
    rbp_finished_date time without time zone,
    rbp_miss_per_periods integer NOT NULL
);


ALTER TABLE recurring_budget_payments OWNER TO postgres;

--
-- TOC entry 182 (class 1259 OID 90835)
-- Name: recurring_types; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE recurring_types (
    rt_name text NOT NULL,
    rt_id integer NOT NULL
);


ALTER TABLE recurring_types OWNER TO postgres;

--
-- TOC entry 183 (class 1259 OID 90841)
-- Name: recurring_types_rt_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE recurring_types_rt_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE recurring_types_rt_id_seq OWNER TO postgres;

--
-- TOC entry 2134 (class 0 OID 0)
-- Dependencies: 183
-- Name: recurring_types_rt_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE recurring_types_rt_id_seq OWNED BY recurring_types.rt_id;


--
-- TOC entry 184 (class 1259 OID 90843)
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
-- TOC entry 185 (class 1259 OID 90846)
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
-- TOC entry 186 (class 1259 OID 90848)
-- Name: user_roles; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE user_roles (
    user_role_id integer DEFAULT nextval('user_role_gen_id'::regclass) NOT NULL,
    username text NOT NULL,
    user_role text NOT NULL
);


ALTER TABLE user_roles OWNER TO postgres;

--
-- TOC entry 187 (class 1259 OID 90855)
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
-- TOC entry 1975 (class 2604 OID 90862)
-- Name: ptype_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY payment_types ALTER COLUMN ptype_id SET DEFAULT nextval('payment_types_ptype_id_seq'::regclass);


--
-- TOC entry 1976 (class 2604 OID 90863)
-- Name: rt_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY recurring_types ALTER COLUMN rt_id SET DEFAULT nextval('recurring_types_rt_id_seq'::regclass);


--
-- TOC entry 1980 (class 2606 OID 90865)
-- Name: category_budget_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY category_budgets
    ADD CONSTRAINT category_budget_pkey PRIMARY KEY (cb_id);


--
-- TOC entry 1982 (class 2606 OID 90867)
-- Name: payment_category_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY payment_categories
    ADD CONSTRAINT payment_category_pkey PRIMARY KEY (pcat_id);


--
-- TOC entry 1984 (class 2606 OID 90869)
-- Name: payment_types_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY payment_types
    ADD CONSTRAINT payment_types_pkey PRIMARY KEY (ptype_id);


--
-- TOC entry 1986 (class 2606 OID 90871)
-- Name: payments_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY payments
    ADD CONSTRAINT payments_pkey PRIMARY KEY (p_id);


--
-- TOC entry 1988 (class 2606 OID 90873)
-- Name: persistent_logins_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY persistent_logins
    ADD CONSTRAINT persistent_logins_pkey PRIMARY KEY (series);


--
-- TOC entry 1990 (class 2606 OID 90875)
-- Name: recurring_payments_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY recurring_budget_payments
    ADD CONSTRAINT recurring_payments_pkey PRIMARY KEY (rbp_id);


--
-- TOC entry 1992 (class 2606 OID 90877)
-- Name: recurring_types_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY recurring_types
    ADD CONSTRAINT recurring_types_pkey PRIMARY KEY (rt_id);


--
-- TOC entry 1996 (class 2606 OID 90879)
-- Name: uni_username_role; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY user_roles
    ADD CONSTRAINT uni_username_role UNIQUE (user_role, username);


--
-- TOC entry 1994 (class 2606 OID 90881)
-- Name: user_budgets_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY user_budgets
    ADD CONSTRAINT user_budgets_pkey PRIMARY KEY (ub_id);


--
-- TOC entry 1998 (class 2606 OID 90883)
-- Name: user_roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY user_roles
    ADD CONSTRAINT user_roles_pkey PRIMARY KEY (user_role_id);


--
-- TOC entry 2000 (class 2606 OID 90885)
-- Name: users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (user_userid);


--
-- TOC entry 2002 (class 2606 OID 90887)
-- Name: users_user_username_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_user_username_key UNIQUE (user_username);


--
-- TOC entry 2003 (class 2606 OID 90888)
-- Name: budgetfk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY category_budgets
    ADD CONSTRAINT budgetfk FOREIGN KEY (cb_budget) REFERENCES user_budgets(ub_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2004 (class 2606 OID 90893)
-- Name: paymentCategoryFk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY category_budgets
    ADD CONSTRAINT "paymentCategoryFk" FOREIGN KEY (cb_category_id) REFERENCES payment_categories(pcat_id);


--
-- TOC entry 2005 (class 2606 OID 90898)
-- Name: payment_categories_pcat_type_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY payment_categories
    ADD CONSTRAINT payment_categories_pcat_type_fkey FOREIGN KEY (pcat_type) REFERENCES payment_types(ptype_id);


--
-- TOC entry 2007 (class 2606 OID 90903)
-- Name: payment_catfk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY payments
    ADD CONSTRAINT payment_catfk FOREIGN KEY (p_category) REFERENCES payment_categories(pcat_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2008 (class 2606 OID 90908)
-- Name: payments_p_recurring_budget_payment_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY payments
    ADD CONSTRAINT payments_p_recurring_budget_payment_fkey FOREIGN KEY (p_recurring_budget_payment) REFERENCES recurring_budget_payments(rbp_id);


--
-- TOC entry 2006 (class 2606 OID 90913)
-- Name: pcat_userfk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY payment_categories
    ADD CONSTRAINT pcat_userfk FOREIGN KEY (pcat_user) REFERENCES users(user_userid) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2009 (class 2606 OID 90918)
-- Name: recurring_budget_payments_rbp_category_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY recurring_budget_payments
    ADD CONSTRAINT recurring_budget_payments_rbp_category_fkey FOREIGN KEY (rbp_category) REFERENCES payment_categories(pcat_id);


--
-- TOC entry 2010 (class 2606 OID 90923)
-- Name: recurring_budget_payments_rbp_rec_type_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY recurring_budget_payments
    ADD CONSTRAINT recurring_budget_payments_rbp_rec_type_fkey FOREIGN KEY (rbp_rec_type) REFERENCES recurring_types(rt_id);


--
-- TOC entry 2011 (class 2606 OID 90928)
-- Name: recurring_budget_payments_rbp_user_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY recurring_budget_payments
    ADD CONSTRAINT recurring_budget_payments_rbp_user_fkey FOREIGN KEY (rbp_user) REFERENCES users(user_userid);


--
-- TOC entry 2012 (class 2606 OID 90933)
-- Name: ub_userfk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY user_budgets
    ADD CONSTRAINT ub_userfk FOREIGN KEY (ub_user) REFERENCES users(user_userid) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2013 (class 2606 OID 90938)
-- Name: user_roles_username_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY user_roles
    ADD CONSTRAINT user_roles_username_fkey FOREIGN KEY (username) REFERENCES users(user_username);


--
-- TOC entry 2129 (class 0 OID 0)
-- Dependencies: 9
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2017-06-19 17:56:56

--
-- PostgreSQL database dump complete
--

