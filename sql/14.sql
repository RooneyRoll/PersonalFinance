--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.10
-- Dumped by pg_dump version 9.5.10

-- Started on 2018-01-24 11:23:20

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 1 (class 3079 OID 12355)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2237 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


--
-- TOC entry 3 (class 3079 OID 16394)
-- Name: pgcrypto; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS pgcrypto WITH SCHEMA public;


--
-- TOC entry 2238 (class 0 OID 0)
-- Dependencies: 3
-- Name: EXTENSION pgcrypto; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION pgcrypto IS 'cryptographic functions';


--
-- TOC entry 2 (class 3079 OID 16431)
-- Name: uuid-ossp; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS "uuid-ossp" WITH SCHEMA public;


--
-- TOC entry 2239 (class 0 OID 0)
-- Dependencies: 2
-- Name: EXTENSION "uuid-ossp"; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION "uuid-ossp" IS 'generate universally unique identifiers (UUIDs)';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 183 (class 1259 OID 16442)
-- Name: category_budgets; Type: TABLE; Schema: public; Owner: postgres
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
-- TOC entry 184 (class 1259 OID 16446)
-- Name: payment_categories; Type: TABLE; Schema: public; Owner: postgres
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
-- TOC entry 185 (class 1259 OID 16452)
-- Name: payment_types; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE payment_types (
    ptype_name text NOT NULL,
    ptype_description text,
    ptype_active boolean NOT NULL,
    ptype_id integer NOT NULL
);


ALTER TABLE payment_types OWNER TO postgres;

--
-- TOC entry 186 (class 1259 OID 16458)
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
-- TOC entry 2240 (class 0 OID 0)
-- Dependencies: 186
-- Name: payment_types_ptype_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE payment_types_ptype_id_seq OWNED BY payment_types.ptype_id;


--
-- TOC entry 187 (class 1259 OID 16460)
-- Name: payments; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE payments (
    p_id uuid NOT NULL,
    p_category uuid NOT NULL,
    p_amount double precision NOT NULL,
    p_date timestamp without time zone NOT NULL,
    p_description text,
    p_active boolean NOT NULL,
    p_recurring_budget_payment uuid,
    p_covered_recurring_periods integer,
    p_confirmed boolean NOT NULL
);


ALTER TABLE payments OWNER TO postgres;

--
-- TOC entry 188 (class 1259 OID 16466)
-- Name: persistent_logins; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE persistent_logins (
    username character varying(64) NOT NULL,
    series character varying(64) NOT NULL,
    token character varying(64) NOT NULL,
    last_used timestamp without time zone NOT NULL
);


ALTER TABLE persistent_logins OWNER TO postgres;

--
-- TOC entry 189 (class 1259 OID 16469)
-- Name: recurring_budget_payments; Type: TABLE; Schema: public; Owner: postgres
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
    rbp_finished boolean NOT NULL,
    rbp_date_finish time without time zone NOT NULL,
    rbp_miss_per_periods integer NOT NULL,
    rbp_final_amount double precision NOT NULL,
    rbp_initial_amount double precision NOT NULL
);


ALTER TABLE recurring_budget_payments OWNER TO postgres;

--
-- TOC entry 190 (class 1259 OID 16475)
-- Name: recurring_types; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE recurring_types (
    rt_name text NOT NULL,
    rt_id integer NOT NULL
);


ALTER TABLE recurring_types OWNER TO postgres;

--
-- TOC entry 191 (class 1259 OID 16481)
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
-- TOC entry 2241 (class 0 OID 0)
-- Dependencies: 191
-- Name: recurring_types_rt_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE recurring_types_rt_id_seq OWNED BY recurring_types.rt_id;


--
-- TOC entry 192 (class 1259 OID 16483)
-- Name: user_budgets; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE user_budgets (
    ub_id uuid NOT NULL,
    ub_user uuid NOT NULL,
    ub_from date NOT NULL,
    ub_to date NOT NULL
);


ALTER TABLE user_budgets OWNER TO postgres;

--
-- TOC entry 193 (class 1259 OID 16486)
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
-- TOC entry 194 (class 1259 OID 16488)
-- Name: user_roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE user_roles (
    user_role_id integer DEFAULT nextval('user_role_gen_id'::regclass) NOT NULL,
    username text NOT NULL,
    user_role text NOT NULL
);


ALTER TABLE user_roles OWNER TO postgres;

--
-- TOC entry 195 (class 1259 OID 16495)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
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
-- TOC entry 2077 (class 2604 OID 16502)
-- Name: ptype_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY payment_types ALTER COLUMN ptype_id SET DEFAULT nextval('payment_types_ptype_id_seq'::regclass);


--
-- TOC entry 2078 (class 2604 OID 16503)
-- Name: rt_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY recurring_types ALTER COLUMN rt_id SET DEFAULT nextval('recurring_types_rt_id_seq'::regclass);


--
-- TOC entry 2082 (class 2606 OID 16505)
-- Name: category_budget_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY category_budgets
    ADD CONSTRAINT category_budget_pkey PRIMARY KEY (cb_id);


--
-- TOC entry 2084 (class 2606 OID 16507)
-- Name: payment_category_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY payment_categories
    ADD CONSTRAINT payment_category_pkey PRIMARY KEY (pcat_id);


--
-- TOC entry 2086 (class 2606 OID 16509)
-- Name: payment_types_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY payment_types
    ADD CONSTRAINT payment_types_pkey PRIMARY KEY (ptype_id);


--
-- TOC entry 2088 (class 2606 OID 16511)
-- Name: payments_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY payments
    ADD CONSTRAINT payments_pkey PRIMARY KEY (p_id);


--
-- TOC entry 2090 (class 2606 OID 16513)
-- Name: persistent_logins_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY persistent_logins
    ADD CONSTRAINT persistent_logins_pkey PRIMARY KEY (series);


--
-- TOC entry 2092 (class 2606 OID 16515)
-- Name: recurring_payments_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY recurring_budget_payments
    ADD CONSTRAINT recurring_payments_pkey PRIMARY KEY (rbp_id);


--
-- TOC entry 2094 (class 2606 OID 16517)
-- Name: recurring_types_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY recurring_types
    ADD CONSTRAINT recurring_types_pkey PRIMARY KEY (rt_id);


--
-- TOC entry 2098 (class 2606 OID 16519)
-- Name: uni_username_role; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY user_roles
    ADD CONSTRAINT uni_username_role UNIQUE (user_role, username);


--
-- TOC entry 2096 (class 2606 OID 16521)
-- Name: user_budgets_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY user_budgets
    ADD CONSTRAINT user_budgets_pkey PRIMARY KEY (ub_id);


--
-- TOC entry 2100 (class 2606 OID 16523)
-- Name: user_roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY user_roles
    ADD CONSTRAINT user_roles_pkey PRIMARY KEY (user_role_id);


--
-- TOC entry 2102 (class 2606 OID 16525)
-- Name: users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (user_userid);


--
-- TOC entry 2104 (class 2606 OID 16527)
-- Name: users_user_username_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_user_username_key UNIQUE (user_username);


--
-- TOC entry 2105 (class 2606 OID 16528)
-- Name: budgetfk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY category_budgets
    ADD CONSTRAINT budgetfk FOREIGN KEY (cb_budget) REFERENCES user_budgets(ub_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2106 (class 2606 OID 16533)
-- Name: paymentCategoryFk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY category_budgets
    ADD CONSTRAINT "paymentCategoryFk" FOREIGN KEY (cb_category_id) REFERENCES payment_categories(pcat_id);


--
-- TOC entry 2107 (class 2606 OID 16538)
-- Name: payment_categories_pcat_type_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY payment_categories
    ADD CONSTRAINT payment_categories_pcat_type_fkey FOREIGN KEY (pcat_type) REFERENCES payment_types(ptype_id);


--
-- TOC entry 2109 (class 2606 OID 16543)
-- Name: payment_catfk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY payments
    ADD CONSTRAINT payment_catfk FOREIGN KEY (p_category) REFERENCES payment_categories(pcat_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2110 (class 2606 OID 16548)
-- Name: payments_p_recurring_budget_payment_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY payments
    ADD CONSTRAINT payments_p_recurring_budget_payment_fkey FOREIGN KEY (p_recurring_budget_payment) REFERENCES recurring_budget_payments(rbp_id);


--
-- TOC entry 2108 (class 2606 OID 16553)
-- Name: pcat_userfk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY payment_categories
    ADD CONSTRAINT pcat_userfk FOREIGN KEY (pcat_user) REFERENCES users(user_userid) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2111 (class 2606 OID 16558)
-- Name: recurring_budget_payments_rbp_category_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY recurring_budget_payments
    ADD CONSTRAINT recurring_budget_payments_rbp_category_fkey FOREIGN KEY (rbp_category) REFERENCES payment_categories(pcat_id);


--
-- TOC entry 2112 (class 2606 OID 16563)
-- Name: recurring_budget_payments_rbp_rec_type_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY recurring_budget_payments
    ADD CONSTRAINT recurring_budget_payments_rbp_rec_type_fkey FOREIGN KEY (rbp_rec_type) REFERENCES recurring_types(rt_id);


--
-- TOC entry 2113 (class 2606 OID 16568)
-- Name: recurring_budget_payments_rbp_user_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY recurring_budget_payments
    ADD CONSTRAINT recurring_budget_payments_rbp_user_fkey FOREIGN KEY (rbp_user) REFERENCES users(user_userid);


--
-- TOC entry 2114 (class 2606 OID 16573)
-- Name: ub_userfk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY user_budgets
    ADD CONSTRAINT ub_userfk FOREIGN KEY (ub_user) REFERENCES users(user_userid) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2115 (class 2606 OID 16578)
-- Name: user_roles_username_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY user_roles
    ADD CONSTRAINT user_roles_username_fkey FOREIGN KEY (username) REFERENCES users(user_username);


--
-- TOC entry 2236 (class 0 OID 0)
-- Dependencies: 9
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2018-01-24 11:23:21

--
-- PostgreSQL database dump complete
--

