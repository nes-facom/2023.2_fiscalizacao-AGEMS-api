--
-- PostgreSQL database dump
--

-- Dumped from database version 15.2
-- Dumped by pg_dump version 15.2

-- Started on 2023-06-12 16:57:29

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 225 (class 1259 OID 33242)
-- Name: formulario; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.formulario (
    id integer NOT NULL,
    data_criacao date,
    id_modelo integer,
    id_unidade integer,
    id_usuario integer,
    observacao character varying(255)
);


ALTER TABLE public.formulario OWNER TO root;

--
-- TOC entry 224 (class 1259 OID 33241)
-- Name: formulario_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE public.formulario_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.formulario_id_seq OWNER TO root;

--
-- TOC entry 3423 (class 0 OID 0)
-- Dependencies: 224
-- Name: formulario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE public.formulario_id_seq OWNED BY public.formulario.id;


--
-- TOC entry 227 (class 1259 OID 33249)
-- Name: imagem; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.imagem (
    id integer NOT NULL,
    data_criacao date,
    imagem character varying(255) NOT NULL,
    id_formulario integer NOT NULL,
    id_questao integer
);


ALTER TABLE public.imagem OWNER TO root;

--
-- TOC entry 226 (class 1259 OID 33248)
-- Name: imagem_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE public.imagem_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.imagem_id_seq OWNER TO root;

--
-- TOC entry 3424 (class 0 OID 0)
-- Dependencies: 226
-- Name: imagem_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE public.imagem_id_seq OWNED BY public.imagem.id;


--
-- TOC entry 216 (class 1259 OID 24698)
-- Name: modelo; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.modelo (
    id integer NOT NULL,
    modelo_nome character varying(255) NOT NULL
);


ALTER TABLE public.modelo OWNER TO root;

--
-- TOC entry 215 (class 1259 OID 24697)
-- Name: modelo_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE public.modelo_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.modelo_id_seq OWNER TO root;

--
-- TOC entry 3425 (class 0 OID 0)
-- Dependencies: 215
-- Name: modelo_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE public.modelo_id_seq OWNED BY public.modelo.id;


--
-- TOC entry 223 (class 1259 OID 33213)
-- Name: questao; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.questao (
    id integer NOT NULL,
    objetiva boolean NOT NULL,
    pergunta character varying(255) NOT NULL,
    portaria character varying(255),
    id_modelo integer NOT NULL
);


ALTER TABLE public.questao OWNER TO root;

--
-- TOC entry 222 (class 1259 OID 33212)
-- Name: questao_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE public.questao_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.questao_id_seq OWNER TO root;

--
-- TOC entry 3426 (class 0 OID 0)
-- Dependencies: 222
-- Name: questao_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE public.questao_id_seq OWNED BY public.questao.id;


--
-- TOC entry 228 (class 1259 OID 33255)
-- Name: resposta; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.resposta (
    obs character varying(255),
    resposta character varying(255),
    id_questao integer NOT NULL,
    id_formulario integer NOT NULL
);


ALTER TABLE public.resposta OWNER TO root;

--
-- TOC entry 230 (class 1259 OID 33263)
-- Name: tipo_resposta; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.tipo_resposta (
    id integer NOT NULL,
    resposta character varying(255) NOT NULL,
    id_questao integer NOT NULL
);


ALTER TABLE public.tipo_resposta OWNER TO root;

--
-- TOC entry 229 (class 1259 OID 33262)
-- Name: tipo_resposta_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE public.tipo_resposta_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tipo_resposta_id_seq OWNER TO root;

--
-- TOC entry 3427 (class 0 OID 0)
-- Dependencies: 229
-- Name: tipo_resposta_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE public.tipo_resposta_id_seq OWNED BY public.tipo_resposta.id;


--
-- TOC entry 217 (class 1259 OID 24728)
-- Name: token; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.token (
    id integer NOT NULL,
    expired boolean NOT NULL,
    revoked boolean NOT NULL,
    token character varying(255),
    token_type character varying(255),
    id_usuario integer
);


ALTER TABLE public.token OWNER TO root;

--
-- TOC entry 214 (class 1259 OID 24597)
-- Name: token_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE public.token_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.token_seq OWNER TO root;

--
-- TOC entry 221 (class 1259 OID 25019)
-- Name: unidade; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.unidade (
    id integer NOT NULL,
    endereco character varying(255) NOT NULL,
    id_unidade character varying(255) NOT NULL,
    tipo character varying(255)
);


ALTER TABLE public.unidade OWNER TO root;

--
-- TOC entry 220 (class 1259 OID 25018)
-- Name: unidade_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE public.unidade_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.unidade_id_seq OWNER TO root;

--
-- TOC entry 3428 (class 0 OID 0)
-- Dependencies: 220
-- Name: unidade_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE public.unidade_id_seq OWNED BY public.unidade.id;


--
-- TOC entry 219 (class 1259 OID 24745)
-- Name: usuario; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.usuario (
    id integer NOT NULL,
    cargo character varying(255) NOT NULL,
    data_criacao date NOT NULL,
    email character varying(255) NOT NULL,
    funcao character varying(255),
    nome character varying(255) NOT NULL,
    senha character varying(255) NOT NULL
);


ALTER TABLE public.usuario OWNER TO root;

--
-- TOC entry 218 (class 1259 OID 24744)
-- Name: usuario_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE public.usuario_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.usuario_id_seq OWNER TO root;

--
-- TOC entry 3429 (class 0 OID 0)
-- Dependencies: 218
-- Name: usuario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE public.usuario_id_seq OWNED BY public.usuario.id;


--
-- TOC entry 3239 (class 2604 OID 33245)
-- Name: formulario id; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.formulario ALTER COLUMN id SET DEFAULT nextval('public.formulario_id_seq'::regclass);


--
-- TOC entry 3240 (class 2604 OID 33252)
-- Name: imagem id; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.imagem ALTER COLUMN id SET DEFAULT nextval('public.imagem_id_seq'::regclass);


--
-- TOC entry 3235 (class 2604 OID 24701)
-- Name: modelo id; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.modelo ALTER COLUMN id SET DEFAULT nextval('public.modelo_id_seq'::regclass);


--
-- TOC entry 3238 (class 2604 OID 33216)
-- Name: questao id; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.questao ALTER COLUMN id SET DEFAULT nextval('public.questao_id_seq'::regclass);


--
-- TOC entry 3241 (class 2604 OID 33266)
-- Name: tipo_resposta id; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.tipo_resposta ALTER COLUMN id SET DEFAULT nextval('public.tipo_resposta_id_seq'::regclass);


--
-- TOC entry 3237 (class 2604 OID 25022)
-- Name: unidade id; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.unidade ALTER COLUMN id SET DEFAULT nextval('public.unidade_id_seq'::regclass);


--
-- TOC entry 3236 (class 2604 OID 24748)
-- Name: usuario id; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.usuario ALTER COLUMN id SET DEFAULT nextval('public.usuario_id_seq'::regclass);


--
-- TOC entry 3259 (class 2606 OID 33247)
-- Name: formulario formulario_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.formulario
    ADD CONSTRAINT formulario_pkey PRIMARY KEY (id);


--
-- TOC entry 3261 (class 2606 OID 33254)
-- Name: imagem imagem_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.imagem
    ADD CONSTRAINT imagem_pkey PRIMARY KEY (id);


--
-- TOC entry 3243 (class 2606 OID 24703)
-- Name: modelo modelo_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.modelo
    ADD CONSTRAINT modelo_pkey PRIMARY KEY (id);


--
-- TOC entry 3257 (class 2606 OID 33220)
-- Name: questao questao_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.questao
    ADD CONSTRAINT questao_pkey PRIMARY KEY (id);


--
-- TOC entry 3263 (class 2606 OID 33261)
-- Name: resposta resposta_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.resposta
    ADD CONSTRAINT resposta_pkey PRIMARY KEY (id_formulario, id_questao);


--
-- TOC entry 3265 (class 2606 OID 33268)
-- Name: tipo_resposta tipo_resposta_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.tipo_resposta
    ADD CONSTRAINT tipo_resposta_pkey PRIMARY KEY (id);


--
-- TOC entry 3245 (class 2606 OID 24734)
-- Name: token token_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.token
    ADD CONSTRAINT token_pkey PRIMARY KEY (id);


--
-- TOC entry 3249 (class 2606 OID 24756)
-- Name: usuario uk_5171l57faosmj8myawaucatdw; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT uk_5171l57faosmj8myawaucatdw UNIQUE (email);


--
-- TOC entry 3247 (class 2606 OID 24754)
-- Name: token uk_pddrhgwxnms2aceeku9s2ewy5; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.token
    ADD CONSTRAINT uk_pddrhgwxnms2aceeku9s2ewy5 UNIQUE (token);


--
-- TOC entry 3253 (class 2606 OID 25028)
-- Name: unidade uk_rb1osrtpfqxpm2rsc56op4es9; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.unidade
    ADD CONSTRAINT uk_rb1osrtpfqxpm2rsc56op4es9 UNIQUE (id_unidade);


--
-- TOC entry 3255 (class 2606 OID 25026)
-- Name: unidade unidade_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.unidade
    ADD CONSTRAINT unidade_pkey PRIMARY KEY (id);


--
-- TOC entry 3251 (class 2606 OID 24752)
-- Name: usuario usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id);


--
-- TOC entry 3268 (class 2606 OID 33269)
-- Name: formulario fk2an0sgdrv4rh1xdbsu8mb0xfn; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.formulario
    ADD CONSTRAINT fk2an0sgdrv4rh1xdbsu8mb0xfn FOREIGN KEY (id_modelo) REFERENCES public.modelo(id);


--
-- TOC entry 3275 (class 2606 OID 33304)
-- Name: tipo_resposta fk3x9kp0la4yjdfwhggud01n80b; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.tipo_resposta
    ADD CONSTRAINT fk3x9kp0la4yjdfwhggud01n80b FOREIGN KEY (id_questao) REFERENCES public.questao(id);


--
-- TOC entry 3266 (class 2606 OID 24797)
-- Name: token fk7bvb07qywqbdurc9u0v9q65x1; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.token
    ADD CONSTRAINT fk7bvb07qywqbdurc9u0v9q65x1 FOREIGN KEY (id_usuario) REFERENCES public.usuario(id);


--
-- TOC entry 3269 (class 2606 OID 33279)
-- Name: formulario fka4yunkcoe7ej0l2pqckhe37f1; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.formulario
    ADD CONSTRAINT fka4yunkcoe7ej0l2pqckhe37f1 FOREIGN KEY (id_usuario) REFERENCES public.usuario(id);


--
-- TOC entry 3273 (class 2606 OID 33299)
-- Name: resposta fkakco6yqrkfqrttdl6ty3qc09h; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.resposta
    ADD CONSTRAINT fkakco6yqrkfqrttdl6ty3qc09h FOREIGN KEY (id_formulario) REFERENCES public.formulario(id);


--
-- TOC entry 3271 (class 2606 OID 33284)
-- Name: imagem fkjwwwbarj6vhp02tgkb2lvs61i; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.imagem
    ADD CONSTRAINT fkjwwwbarj6vhp02tgkb2lvs61i FOREIGN KEY (id_formulario) REFERENCES public.formulario(id);


--
-- TOC entry 3272 (class 2606 OID 33289)
-- Name: imagem fkpeenkglpopb4w7e5elx8afnmm; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.imagem
    ADD CONSTRAINT fkpeenkglpopb4w7e5elx8afnmm FOREIGN KEY (id_questao) REFERENCES public.questao(id);


--
-- TOC entry 3270 (class 2606 OID 33274)
-- Name: formulario fkrv5ywmddfov2x1dfu7w4166ox; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.formulario
    ADD CONSTRAINT fkrv5ywmddfov2x1dfu7w4166ox FOREIGN KEY (id_unidade) REFERENCES public.unidade(id);


--
-- TOC entry 3267 (class 2606 OID 33226)
-- Name: questao fktcdrwnaifgnvl4wxco8xqcrhq; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.questao
    ADD CONSTRAINT fktcdrwnaifgnvl4wxco8xqcrhq FOREIGN KEY (id_modelo) REFERENCES public.modelo(id);


--
-- TOC entry 3274 (class 2606 OID 33294)
-- Name: resposta fkth8ax805foi01vdqtop9g3nnm; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.resposta
    ADD CONSTRAINT fkth8ax805foi01vdqtop9g3nnm FOREIGN KEY (id_questao) REFERENCES public.questao(id);


-- Completed on 2023-06-12 16:57:29

--
-- PostgreSQL database dump complete
--

