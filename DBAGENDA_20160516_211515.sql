------------- SQLite3 Dump File -------------

-- ------------------------------------------
-- Dump of "COMPROMISSO"
-- ------------------------------------------

CREATE TABLE "COMPROMISSO"(
	"ID" Integer NOT NULL PRIMARY KEY AUTOINCREMENT,
	"DATA" Date,
	"HORA_INI" DateTime,
	"HORA_FIM" DateTime,
	"LOCAL" Text,
	"DESCRICAO" Text,
	"ID_TIPO" Integer,
	CONSTRAINT "lnk_COMPROMISSO_TIPO_EVENTO" FOREIGN KEY ( "ID_TIPO" ) REFERENCES "TIPO_EVENTO"( "ID" )
,
CONSTRAINT "unique_ID" UNIQUE ( "ID_TIPO" ) );


-- ------------------------------------------
-- Dump of "COMPROMISSO_PARTICIPANTE"
-- ------------------------------------------

CREATE TABLE "COMPROMISSO_PARTICIPANTE"(
	"ID" Integer,
	"ID_PARTICIPANTE" Integer,
	CONSTRAINT "lnk_COMPROMISSO_PARTICIPANTE_PARTICIPANTE" FOREIGN KEY ( "ID_PARTICIPANTE" ) REFERENCES "PARTICIPANTE"( "ID" )
,
CONSTRAINT "unique_ID" UNIQUE ( "ID_PARTICIPANTE" ) );


-- ------------------------------------------
-- Dump of "COMPROMISSO_REPLICA"
-- ------------------------------------------

CREATE TABLE "COMPROMISSO_REPLICA"(
	"ID" Integer NOT NULL PRIMARY KEY AUTOINCREMENT,
	"ID_COMPROMISSO" Integer NOT NULL,
	CONSTRAINT "lnk_COMPROMISSO_REPLICA_COMPROMISSO" FOREIGN KEY ( "ID_COMPROMISSO" ) REFERENCES "COMPROMISSO"( "ID" )
,
CONSTRAINT "unique_ID_COMPROMISSO" UNIQUE ( "ID_COMPROMISSO" ) );


-- ------------------------------------------
-- Dump of "PARTICIPANTE"
-- ------------------------------------------

CREATE TABLE "PARTICIPANTE"(
	"ID" Integer NOT NULL PRIMARY KEY AUTOINCREMENT,
	"NOME" Text,
CONSTRAINT "unique_ID" UNIQUE ( "ID" ) );


INSERT INTO "PARTICIPANTE" ("ID","NOME") VALUES ( 1,'Rangel' );



-- ------------------------------------------
-- Dump of "TIPO_EVENTO"
-- ------------------------------------------

CREATE TABLE "TIPO_EVENTO"(
	"ID" Integer NOT NULL PRIMARY KEY AUTOINCREMENT,
	"DESCRICAO" Text,
CONSTRAINT "unique_ID" UNIQUE ( "ID" ) );


