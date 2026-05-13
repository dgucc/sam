-- Le fichier qui va s'occuper de créer les différentes tables de la bdd (DDL data definition language)
-- type BLOB pour stocker des images (binary large object) si on veut
-- ============================================================
--  Schema
-- ============================================================
DROP DATABASE IF EXISTS GROUPE02;
CREATE DATABASE IF NOT EXISTS GROUPE02;
USE GROUPE02;

CREATE TABLE IF NOT EXISTS users (
  id          INT          AUTO_INCREMENT PRIMARY KEY,
  username         VARCHAR(100) NOT NULL,
  password         VARCHAR(100) NOT NULL,
  email            VARCHAR(150) NOT NULL UNIQUE,
  role      VARCHAR(100) NOT NULL DEFAULT 'viewer',
  created_at       TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS cours (
  id          INT          AUTO_INCREMENT PRIMARY KEY,
  nom         VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS synthese (
  id          INT          AUTO_INCREMENT PRIMARY KEY,
  nom         VARCHAR(100) NOT NULL,
  url         VARCHAR(300) NOT NULL,
  author_id   INT          NOT NULL,
  cours_id    INT          NOT NULL,
  FOREIGN KEY (author_id)   REFERENCES users(id)      ON DELETE CASCADE,
  FOREIGN KEY (cours_id)   REFERENCES cours(id)      ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS faculte (
    id          INT          AUTO_INCREMENT PRIMARY KEY,
    nom         VARCHAR(100) NOT NULL,
    image       VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS teacher (
  id          INT          AUTO_INCREMENT PRIMARY KEY,
  nom         VARCHAR(100) NOT NULL,
  prenom      VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS fav_synth (
  user_id     INT          NOT NULL,
  synth_id    INT          NOT NULL,
  FOREIGN KEY (user_id)   REFERENCES users(id)   ON DELETE CASCADE,
  FOREIGN KEY (synth_id)    REFERENCES synthese(id)  ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS fav_cours (
  user_id     INT          NOT NULL,
  cours_id    INT          NOT NULL,
  FOREIGN KEY (user_id)   REFERENCES users(id)      ON DELETE CASCADE,
  FOREIGN KEY (cours_id)   REFERENCES cours(id)      ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS teacher_to_cours (
  teacher_id  INT          NOT NULL,
  cours_id    INT          NOT NULL,
  FOREIGN KEY (teacher_id)   REFERENCES teacher(id)      ON DELETE CASCADE,
  FOREIGN KEY (cours_id)   REFERENCES cours(id)      ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS cours_to_faculte (
  cours_id    INT          NOT NULL,
  faculte_id  INT          NOT NULL,
  FOREIGN KEY (cours_id)   REFERENCES cours(id)      ON DELETE CASCADE,
  FOREIGN KEY (faculte_id)   REFERENCES faculte(id)      ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS commentaire (
  id          INT        AUTO_INCREMENT PRIMARY KEY,
  contenu     TEXT       NOT NULL,
  author_id   INT        NOT NULL,
  synthese_id INT        NOT NULL,
  created_at  TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (author_id)   REFERENCES users(id)     ON DELETE CASCADE,
  FOREIGN KEY (synthese_id) REFERENCES synthese(id) ON DELETE CASCADE
);
