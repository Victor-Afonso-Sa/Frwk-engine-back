

CREATE TABLE trilha (
idtrilha number PRIMARY KEY auto_increment,
idregra VARCHAR(255),
jsonexecucao CLOB
);

CREATE INDEX idregra
ON trilha(idregra) 

CREATE TABLE parametros (
  idpasta VARCHAR(255) PRIMARY KEY,
  nome VARCHAR(255) NOT NULL,
  parametros TEXT NOT NULL
);

CREATE TABLE regras (
  idregra VARCHAR(255) PRIMARY KEY,
  schemaregras TEXT NOT NULL
);
  
 CREATE TABLE pastasmodelos (
  idpasta VARCHAR(255)  PRIMARY KEY,
  schemapastas TEXT NOT NULL
);

CREATE TABLE pastaregras (
  idpasta VARCHAR(255) PRIMARY KEY,
  nome VARCHAR(255) NOT NULL,
  regras TEXT NOT NULL
);

