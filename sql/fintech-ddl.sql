-- USUARIO

CREATE TABLE T_FINTECH_USUARIO
(
	cd_usuario NUMERIC(3) NOT NULL,
	nm_usuario VARCHAR(40) NOT NULL,
	ds_email VARCHAR(150) NOT NULL,
	cd_senha VARCHAR(50) NOT NULL,
	dt_nasc DATE NOT NULL,
	tp_genero CHAR(1) NOT NULL,
    cd_salt VARCHAR(50) NOT NULL
);

-- PK USUARIO

ALTER TABLE T_FINTECH_USUARIO
	ADD CONSTRAINT PK_FINTECH_USUARIO PRIMARY KEY (cd_usuario);

-- REGISTRO

CREATE TABLE T_FINTECH_REGISTRO
(
	cd_registro NUMERIC(6) NOT NULL,
	cd_usuario NUMERIC(3) NOT NULL,
	vl_registro NUMERIC(9,4) NOT NULL,
	dt_registro DATE NOT NULL,
	cd_tipo NUMERIC(6) NOT NULL,
	ds_categoria VARCHAR(20) NULL,
	ds_registro VARCHAR(60) NULL
);

-- PK REGISTRO
ALTER TABLE T_FINTECH_REGISTRO
	ADD CONSTRAINT PK_FINTECH_REGISTRO PRIMARY KEY (cd_registro, cd_usuario);

-- INVESTIMENTO

CREATE TABLE T_FINTECH_INVESTIMENTO
(
	cd_investimento NUMERIC(2) NOT NULL,
	cd_usuario NUMERIC(3) NOT NULL,
	vl_investimento NUMERIC(13,4) NOT NULL,
	vl_taxa NUMERIC(5,4) NOT NULL,
	nm_inst_financ VARCHAR(30) NOT NULL,
	cd_tipo NUMERIC (6) NOT NULL,
	dt_inicio_investimento DATE NOT NULL,
	dt_final_investimento DATE NULL,
	ds_investimento VARCHAR(100) NULL
);

-- PK INVESTIMENTO

ALTER TABLE T_FINTECH_INVESTIMENTO
	ADD CONSTRAINT PK_FINTECH_INVESTIMENTO PRIMARY KEY (cd_investimento, cd_usuario);

-- TIPO

CREATE TABLE T_FINTECH_TIPO
(
	cd_tipo NUMERIC(6) NOT NULL,
	nm_tipo VARCHAR(20) NOT NULL
);

-- PK TIPO
ALTER TABLE T_FINTECH_TIPO
	ADD CONSTRAINT PK_T_FINTECH_TIPO PRIMARY KEY (cd_tipo);

ALTER TABLE T_FINTECH_TIPO
	ADD CONSTRAINT UN_FINTECH_TIPO_DS UNIQUE (nm_tipo);



-- FKS REGISTRO

ALTER TABLE T_FINTECH_REGISTRO
	ADD CONSTRAINT FK_FINTECH_REGISTRO_USUARIO
	FOREIGN KEY (cd_usuario) REFERENCES T_FINTECH_USUARIO (cd_usuario);

ALTER TABLE T_FINTECH_REGISTRO
	ADD CONSTRAINT FK_FINTECH_REGISTRO_TIPO
	FOREIGN KEY (cd_tipo) REFERENCES T_FINTECH_TIPO (cd_tipo);

-- FKS INVESTIMENTO

ALTER TABLE T_FINTECH_INVESTIMENTO
	ADD CONSTRAINT FK_FINTECH_INVESTIMENTO_USUARIO
	FOREIGN KEY (cd_usuario) REFERENCES T_FINTECH_USUARIO (cd_usuario);

ALTER TABLE T_FINTECH_INVESTIMENTO
	ADD CONSTRAINT FK_FINTECH_INVESTIMENTO_TIPO
	FOREIGN KEY (cd_tipo) REFERENCES T_FINTECH_TIPO (cd_tipo);



-- SEQUENCES

CREATE SEQUENCE SQ_USUARIO
INCREMENT BY 1
START WITH 1
MAXVALUE 999
NOCACHE
NOCYCLE;

CREATE SEQUENCE SQ_REGISTRO
INCREMENT BY 1
START WITH 1
MAXVALUE 999999
NOCACHE
NOCYCLE;

CREATE SEQUENCE SQ_INVESTIMENTO
INCREMENT BY 1
START WITH 1
MAXVALUE 99
NOCACHE
NOCYCLE;

CREATE SEQUENCE SQ_TIPO
INCREMENT BY 1
START WITH 1
MAXVALUE 999
NOCACHE
NOCYCLE;

-- TIPOS

INSERT INTO T_FINTECH_TIPO (cd_tipo, nm_tipo)
VALUES (1, 'Receita');

INSERT INTO T_FINTECH_TIPO (cd_tipo, nm_tipo)
VALUES (2, 'Gasto');

INSERT INTO T_FINTECH_TIPO (cd_tipo, nm_tipo)
VALUES (3, 'Renda Fixa');

INSERT INTO T_FINTECH_TIPO (cd_tipo, nm_tipo)
VALUES (1, 'Renda Variável');