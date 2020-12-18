

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
  regras text NOT NULL
);

