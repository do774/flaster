--
-- PostgreSQL database dump
--

-- Dumped from database version 16.8 (Ubuntu 16.8-0ubuntu0.24.04.1)
-- Dumped by pg_dump version 16.8 (Ubuntu 16.8-0ubuntu0.24.04.1)

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
-- Name: comments; Type: TABLE; Schema: public; Owner: bloguser
--

CREATE TABLE public.comments (
    id bigint NOT NULL,
    content text,
    created_at timestamp(6) without time zone,
    post_id bigint,
    user_id bigint
);


ALTER TABLE public.comments OWNER TO bloguser;

--
-- Name: comments_id_seq; Type: SEQUENCE; Schema: public; Owner: bloguser
--

ALTER TABLE public.comments ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.comments_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: likes; Type: TABLE; Schema: public; Owner: bloguser
--

CREATE TABLE public.likes (
    id bigint NOT NULL,
    post_id bigint,
    user_id bigint
);


ALTER TABLE public.likes OWNER TO bloguser;

--
-- Name: likes_id_seq; Type: SEQUENCE; Schema: public; Owner: bloguser
--

ALTER TABLE public.likes ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.likes_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: posts; Type: TABLE; Schema: public; Owner: bloguser
--

CREATE TABLE public.posts (
    id bigint NOT NULL,
    content text,
    created_at timestamp(6) without time zone,
    title character varying(255),
    updated_at timestamp(6) without time zone,
    author_id bigint
);


ALTER TABLE public.posts OWNER TO bloguser;

--
-- Name: posts_id_seq; Type: SEQUENCE; Schema: public; Owner: bloguser
--

ALTER TABLE public.posts ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.posts_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: users; Type: TABLE; Schema: public; Owner: bloguser
--

CREATE TABLE public.users (
    id bigint NOT NULL,
    age integer,
    country character varying(255),
    email character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    role character varying(255),
    username character varying(255) NOT NULL
);


ALTER TABLE public.users OWNER TO bloguser;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: bloguser
--

ALTER TABLE public.users ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Data for Name: comments; Type: TABLE DATA; Schema: public; Owner: bloguser
--

COPY public.comments (id, content, created_at, post_id, user_id) FROM stdin;
5	What about slow charging?	2025-03-23 10:22:13.500496	15	6
6	That is so true , music is so OP!	2025-03-23 10:22:57.727535	11	6
7	Slow charging is not the main problem, what about battery lifespan?	2025-03-23 10:26:00.122973	15	8
8	I prefer gas cars, sorry guys :)	2025-03-23 10:27:58.000125	15	9
9	I want to go there, New York is the best!	2025-03-23 10:28:52.977596	12	9
10	We dont need cars, we have bikes!	2025-03-23 10:31:35.858242	15	10
11	I would like to go there on vacation!	2025-03-23 11:21:54.784172	14	11
\.


--
-- Data for Name: likes; Type: TABLE DATA; Schema: public; Owner: bloguser
--

COPY public.likes (id, post_id, user_id) FROM stdin;
170	15	6
171	10	6
172	11	6
173	15	8
175	15	9
176	10	9
177	12	9
178	15	10
179	14	11
180	15	11
181	11	11
183	15	1
190	11	1
193	11	8
194	10	8
\.


--
-- Data for Name: posts; Type: TABLE DATA; Schema: public; Owner: bloguser
--

COPY public.posts (id, content, created_at, title, updated_at, author_id) FROM stdin;
14	Life in Honduras offers a mix of natural beauty, cultural richness, and everyday simplicity. From the Caribbean beaches of the Bay Islands to the lush mountains and colonial towns, the country is full of charm and character.\n\nMany Hondurans live with a strong sense of community and tradition. While challenges like economic inequality and safety exist, people often find joy in family, music, and celebration.\n\nWith stunning nature, delicious food, and a warm, resilient spirit, life in Honduras is both complex and beautiful — a reflection of its vibrant people.\n\n	2025-03-23 10:17:28.917556	Life in Honduras – Vibrant, Warm, and Full of Contrast	2025-03-23 10:17:28.91756	5
15	Electric cars are transforming the way we move, offering a cleaner and more sustainable alternative to traditional gas-powered vehicles. With zero tailpipe emissions and lower operating costs, they’re becoming an increasingly popular choice worldwide.\n\nAdvancements in battery technology, charging infrastructure, and design have made EVs more efficient, stylish, and accessible than ever before. From compact city cars to high-performance models, the electric revolution is in full swing.\n\nSwitching to electric isn’t just about driving — it’s about choosing a better future for the planet.	2025-03-23 10:19:10.870313	Electric Cars – Driving Toward a Greener Future	2025-03-23 10:19:10.870318	5
9	Flaster is a bold and innovative company redefining outdoor advertising. Based in Europe, they specialize in turning vehicles and urban spaces into moving billboards, offering brands a fresh and eye-catching way to reach their audience.\n\nWhat sets Flaster apart is their ability to combine mobility, visibility, and creativity, ensuring campaigns are not only seen but remembered. With a strong focus on data, tracking, and impact, Flaster delivers measurable results while keeping things fun and engaging.\n\nFor brands looking to break through the noise, Flaster is a game-changer.	2025-03-23 10:07:44.177827	Flaster – Creative Advertising That Stands Out	2025-03-23 17:56:16.996202	3
11	Music has the unique power to connect people across cultures, languages, and generations. Whether it's a soothing melody, a powerful beat, or meaningful lyrics, music speaks to our emotions in ways words often can’t.\n\nFrom classical symphonies to modern electronic sounds, music continues to evolve while staying deeply personal and universal. It inspires, heals, energizes, and brings people together.\n\nNo matter the genre or style, music is a constant companion — a soundtrack to our lives.	2025-03-23 10:12:11.247649	Music – The Universal Language	2025-03-23 10:12:11.247653	4
12	New York City is more than just a place — it’s an experience. Known for its iconic skyline, buzzing streets, and cultural diversity, NYC is a global symbol of ambition, creativity, and nonstop energy.\n\nFrom Times Square and Central Park to hidden gems in Brooklyn, the city offers endless inspiration. It’s a hub for finance, fashion, art, food, and innovation — all packed into one unforgettable metropolis.\n\nWhether you’re visiting or living there, New York has a way of making you feel like anything is possible.	2025-03-23 10:16:03.840386	New York – The City That Never Sleeps	2025-03-23 10:16:03.840391	5
10	Robots are no longer just science fiction — they’re becoming a real part of our everyday lives. From warehouse automation and smart homes to surgical assistants and self-driving cars, robotics is transforming industries across the globe.\n\nWhat makes robots so powerful is their ability to work with precision, speed, and endurance, often in environments that are too dangerous or repetitive for humans. As AI continues to evolve, robots are becoming smarter and more adaptable.\n\nThe future of robotics isn’t just coming — it’s already here, and it’s changing the way we live and work.	2025-03-23 10:09:04.346876	Robots – The Future Is Already Here	2025-03-23 13:46:15.859927	3
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: bloguser
--

COPY public.users (id, age, country, email, password, role, username) FROM stdin;
2	55	Slovenia	reader@gmail.com	$2a$10$Qul3PdUtKnq.NND93dPSDOwnHiaxmZh5HCvO2Bk80yy7mYk8zBd16	READER	JustAnotherReader
1	33	Croatia	domagoj.jugovic74@gmail.com	$2a$10$SxPsNnZqBu1uVopeUjt/seTtfyOOU.lhlWf1UNT.SFqgpx2Qv0aJ.	ADMIN	domagoj74
3	37	Italia	author@gmail.com	$2a$10$rJFdZrUKQYGwpcEUrsmz5ezOxqynnD/eMmNwxt3.eLe.ujFPEK4Fy	AUTHOR	Author55
4	27	Hawaii	janice@gmail.com	$2a$10$I3HVt7RkN/1twRYZgFH3sOfRjZ1ZiquvbiTIiDlmuyaFbVRnKIA4a	AUTHOR	JanisJoplin
5	55	Honduras	newyorktimes@yahoo.com	$2a$10$nlrpLKLmK2RyFEhidt.iOOq0qC.PM2AMWoZ7/8jTIn2TzgrDzjyNu	AUTHOR	NewYorkTimes
6	17	France	justreader@gmail.com	$2a$10$lVsuAlxaq4lgDszaKzMjOuAJuew8hjIjtTnDAhM6pjcpMAF7MIh5i	READER	JustReader
7	22		anthony@gmail.com	$2a$10$QqQJU.ajUNHaEOvabrFxFuuCV9zwD5xIu8vJVw437rzoV.zZy9kU2	READER	Anthony22
8	27	Hungary	robi@gmail.com	$2a$10$i/UwEVAxbiW4iZFUXz01UeTF3K4Um5JfPazCTjIr.vPmhjqD9eJHy	READER	robi
10	32	Mahwah	johnny@gmail.com	$2a$10$Yz.NCZS7J0XzNkqpkqWIZONZWOyF2F4xCIQRpqyceJTOVntkxGhAu	AUTHOR	johnny99
11	25	Croatia	marina13@gmail.com	$2a$10$gjBFPC5jmY7va6JJDPT4u.lpBw9oKVkMtjUXebViQ7qBs./pxnRty	AUTHOR	marina
9	33	Portugal	mary33@gmail.com	$2a$10$kD5W8HVLgrFZv8UmfP3Gfe1ZZdxJ0h/0xbjKibS77wbI3oSzjP.ue	READER	mary33
\.


--
-- Name: comments_id_seq; Type: SEQUENCE SET; Schema: public; Owner: bloguser
--

SELECT pg_catalog.setval('public.comments_id_seq', 25, true);


--
-- Name: likes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: bloguser
--

SELECT pg_catalog.setval('public.likes_id_seq', 194, true);


--
-- Name: posts_id_seq; Type: SEQUENCE SET; Schema: public; Owner: bloguser
--

SELECT pg_catalog.setval('public.posts_id_seq', 23, true);


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: bloguser
--

SELECT pg_catalog.setval('public.users_id_seq', 11, true);


--
-- Name: comments comments_pkey; Type: CONSTRAINT; Schema: public; Owner: bloguser
--

ALTER TABLE ONLY public.comments
    ADD CONSTRAINT comments_pkey PRIMARY KEY (id);


--
-- Name: likes likes_pkey; Type: CONSTRAINT; Schema: public; Owner: bloguser
--

ALTER TABLE ONLY public.likes
    ADD CONSTRAINT likes_pkey PRIMARY KEY (id);


--
-- Name: posts posts_pkey; Type: CONSTRAINT; Schema: public; Owner: bloguser
--

ALTER TABLE ONLY public.posts
    ADD CONSTRAINT posts_pkey PRIMARY KEY (id);


--
-- Name: users ukr43af9ap4edm43mmtq01oddj6; Type: CONSTRAINT; Schema: public; Owner: bloguser
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT ukr43af9ap4edm43mmtq01oddj6 UNIQUE (username);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: bloguser
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: posts fk6xvn0811tkyo3nfjk2xvqx6ns; Type: FK CONSTRAINT; Schema: public; Owner: bloguser
--

ALTER TABLE ONLY public.posts
    ADD CONSTRAINT fk6xvn0811tkyo3nfjk2xvqx6ns FOREIGN KEY (author_id) REFERENCES public.users(id);


--
-- Name: comments fk8omq0tc18jd43bu5tjh6jvraq; Type: FK CONSTRAINT; Schema: public; Owner: bloguser
--

ALTER TABLE ONLY public.comments
    ADD CONSTRAINT fk8omq0tc18jd43bu5tjh6jvraq FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: comments fkh4c7lvsc298whoyd4w9ta25cr; Type: FK CONSTRAINT; Schema: public; Owner: bloguser
--

ALTER TABLE ONLY public.comments
    ADD CONSTRAINT fkh4c7lvsc298whoyd4w9ta25cr FOREIGN KEY (post_id) REFERENCES public.posts(id);


--
-- Name: likes fknvx9seeqqyy71bij291pwiwrg; Type: FK CONSTRAINT; Schema: public; Owner: bloguser
--

ALTER TABLE ONLY public.likes
    ADD CONSTRAINT fknvx9seeqqyy71bij291pwiwrg FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: likes fkry8tnr4x2vwemv2bb0h5hyl0x; Type: FK CONSTRAINT; Schema: public; Owner: bloguser
--

ALTER TABLE ONLY public.likes
    ADD CONSTRAINT fkry8tnr4x2vwemv2bb0h5hyl0x FOREIGN KEY (post_id) REFERENCES public.posts(id);


--
-- PostgreSQL database dump complete
--

