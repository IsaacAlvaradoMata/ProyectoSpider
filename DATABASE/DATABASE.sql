-- SEQUENCES -------------------------------------------------------------

CREATE SEQUENCE SEQ_JUGADOR START WITH 1 INCREMENT BY 1 NOMAXVALUE NOMINVALUE NOCYCLE CACHE 20;
CREATE SEQUENCE SEQ_PARTIDA START WITH 1 INCREMENT BY 1 NOMAXVALUE NOMINVALUE NOCYCLE CACHE 20;
CREATE SEQUENCE SEQ_CARTASPARTIDA START WITH 1 INCREMENT BY 1 NOMAXVALUE NOMINVALUE NOCYCLE CACHE 20;

-- TABLE JUGADOR ---------------------------------------------------------

CREATE TABLE Jugador (
                         Id_Jugador NUMBER CONSTRAINT PK_JUGADOR PRIMARY KEY,
                         Nombre_Usuario VARCHAR2(50) CONSTRAINT UK_JUGADOR_USUARIO UNIQUE NOT NULL,
                         Partidas_Jugadas NUMBER,
                         Partidas_Ganadas NUMBER,
                         Puntos_Acumulados NUMBER,
                         Estilo_Cartas NUMBER,
                         Imagen_Fondo BLOB,
                         Version NUMBER DEFAULT 0
);

COMMENT ON COLUMN Jugador.Id_Jugador IS 'Id asignado al jugador';
COMMENT ON COLUMN Jugador.Nombre_Usuario IS 'Nombre del usuario';
COMMENT ON COLUMN Jugador.Partidas_Jugadas IS 'Total de partidas jugadas';
COMMENT ON COLUMN Jugador.Partidas_Ganadas IS 'Total de partidas ganadas';
COMMENT ON COLUMN Jugador.Puntos_Acumulados IS 'Puntos totales acumulados en la partida';
COMMENT ON COLUMN Jugador.Estilo_Cartas IS 'Estilo de cartas elegido por el jugador';
COMMENT ON COLUMN Jugador.Imagen_Fondo IS 'Imagen de fondo elegida por el jugador';
COMMENT ON COLUMN Jugador.Version IS 'Número de versión para control de concurrencia optimista';

-- TABLE PARTIDA ---------------------------------------------------------

CREATE TABLE Partida (
                         Id_Partida NUMBER CONSTRAINT PK_PARTIDA PRIMARY KEY,
                         Id_Jugador NUMBER NOT NULL,
                         Fecha_Inicio TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP,
                         Fecha_Fin TIMESTAMP(6),
                         Version NUMBER DEFAULT 0,
                         Puntos NUMBER CONSTRAINT CK_PARTIDA_PUNTOS CHECK (Puntos >= 0),
                         Tiempo_Jugado NUMBER CONSTRAINT CK_PARTIDA_TIEMPO_JUGADO CHECK (Tiempo_Jugado >= 0),
                         Estado VARCHAR2(20) NOT NULL CONSTRAINT CK_PARTIDA_ESTADO CHECK (Estado IN ('EN_JUEGO', 'PAUSADA', 'TERMINADA', 'PERDIDA')),
                         Dificultad VARCHAR2(20) DEFAULT 'MEDIA' NOT NULL CONSTRAINT CK_PARTIDA_DIFICULTAD CHECK (Dificultad IN ('FACIL', 'MEDIA', 'DIFICIL'))
);

COMMENT ON COLUMN Partida.Id_Partida IS 'Id de la partida';
COMMENT ON COLUMN Partida.Id_Jugador IS 'Id del jugador';
COMMENT ON COLUMN Partida.Fecha_Inicio IS 'Fecha de inicio de la partida';
COMMENT ON COLUMN Partida.Fecha_Fin IS 'Fecha de fin de la partida';
COMMENT ON COLUMN Partida.Puntos IS 'Puntos de la partida';
COMMENT ON COLUMN Partida.Tiempo_Jugado IS 'Tiempo jugado de la partida';
COMMENT ON COLUMN Partida.Estado IS 'Estado de la partida';
COMMENT ON COLUMN Partida.Dificultad IS 'Dificultad de la partida';
COMMENT ON COLUMN Partida.Version IS 'Número de versión para control de concurrencia optimista';

CREATE INDEX IX_Relationship2 ON Partida (Id_Jugador);


-- TABLE CARTASPARTIDA ----------------------------------------------------

CREATE TABLE CartasPartida (
                               Id_Carta_Partida NUMBER CONSTRAINT PK_CARTASPARTIDA PRIMARY KEY,
                               Id_Partida NUMBER NOT NULL,
                               Palo VARCHAR2(20) NOT NULL,
                               Valor VARCHAR2(25) NOT NULL,
                               Columna NUMBER NOT NULL,
                               Orden NUMBER NOT NULL,
                               Version NUMBER DEFAULT 0,
                               Boca_Arriba NUMBER NOT NULL CONSTRAINT CK_CARTASPARTIDA_BOCA_ARRIBA CHECK (Boca_Arriba IN (0, 1)),
                               En_Mazo NUMBER NOT NULL CONSTRAINT CK_CARTASPARTIDA_EN_MAZO CHECK (En_Mazo IN (0, 1)),
                               En_Pila NUMBER NOT NULL CONSTRAINT CK_CARTASPARTIDA_EN_PILA CHECK (En_Pila IN (0, 1)),
                               Retirada NUMBER NOT NULL CONSTRAINT CK_CARTASPARTIDA_RETIRADA CHECK (Retirada IN (0, 1))
);

COMMENT ON COLUMN CartasPartida.Id_Carta_Partida IS 'Id de la carta';
COMMENT ON COLUMN CartasPartida.Id_Partida IS 'Id de la partida';
COMMENT ON COLUMN CartasPartida.Palo IS 'Palo elegido por el jugador';
COMMENT ON COLUMN CartasPartida.Valor IS 'Valor de la carta';
COMMENT ON COLUMN CartasPartida.Columna IS 'Columna de la carta';
COMMENT ON COLUMN CartasPartida.Orden IS 'Orden de las cartas';
COMMENT ON COLUMN CartasPartida.Boca_Arriba IS 'Estado de la carta (1=Si, 0=No)';
COMMENT ON COLUMN CartasPartida.En_Mazo IS 'Estado de la carta (1=Si, 0=No)';
COMMENT ON COLUMN CartasPartida.En_Pila IS 'Estado de la carta (1=Si, 0=No)';
COMMENT ON COLUMN CartasPartida.Retirada IS 'Estado de la carta (1=Si, 0=No)';
COMMENT ON COLUMN CartasPartida.Version IS 'Número de versión para control de concurrencia optimista';

CREATE INDEX IX_Relationship4 ON CartasPartida (Id_Partida);


-- FOREIGN KEYS -----------------------------------------------------------

ALTER TABLE Partida ADD CONSTRAINT FK_Partida_Jugador
    FOREIGN KEY (Id_Jugador) REFERENCES Jugador(Id_Jugador) ON DELETE CASCADE;

ALTER TABLE CartasPartida ADD CONSTRAINT FK_CartasPartida_Partida
    FOREIGN KEY (Id_Partida) REFERENCES Partida(Id_Partida) ON DELETE CASCADE;


-- TRIGGERS ---------------------------------------------------------------

-- Jugador
CREATE OR REPLACE TRIGGER ts_Jugador_SEQ_JUGADOR
    BEFORE INSERT ON Jugador FOR EACH ROW
BEGIN
    :new.Id_Jugador := SEQ_JUGADOR.NEXTVAL;
END;
/

CREATE OR REPLACE TRIGGER tsu_Jugador_SEQ_JUGADOR
    AFTER UPDATE OF Id_Jugador ON Jugador FOR EACH ROW
BEGIN
    RAISE_APPLICATION_ERROR(-20010,'Cannot update column Id_Jugador in table Jugador as it uses sequence.');
END;
/

-- Partida
CREATE OR REPLACE TRIGGER ts_Partida_SEQ_PARTIDA
    BEFORE INSERT ON Partida FOR EACH ROW
BEGIN
    :new.Id_Partida := SEQ_PARTIDA.NEXTVAL;
END;
/

CREATE OR REPLACE TRIGGER tsu_Partida_SEQ_PARTIDA
    AFTER UPDATE OF Id_Partida ON Partida FOR EACH ROW
BEGIN
    RAISE_APPLICATION_ERROR(-20010,'Cannot update column Id_Partida in table Partida as it uses sequence.');
END;
/

-- CartasPartida
CREATE OR REPLACE TRIGGER ts_CartasPartida_SEQ_CARTASPARTIDA
    BEFORE INSERT ON CartasPartida FOR EACH ROW
BEGIN
    :new.Id_Carta_Partida := SEQ_CARTASPARTIDA.NEXTVAL;
END;
/

CREATE OR REPLACE TRIGGER tsu_CartasPartida_SEQ_CARTASPARTIDA
    AFTER UPDATE OF Id_Carta_Partida ON CartasPartida FOR EACH ROW
BEGIN
    RAISE_APPLICATION_ERROR(-20010,'Cannot update column Id_Carta_Partida in table CartasPartida as it uses sequence.');
END;
/