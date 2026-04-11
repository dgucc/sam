-- 1. Créer la base de données si elle n'existe pas (facultatif mais recommandé)
CREATE DATABASE IF NOT EXISTS demo;

-- 2. Utiliser cette base de données
USE demo;

-- 3. Créer la table
CREATE TABLE university (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255),
    logo VARCHAR(255),
    PRIMARY KEY (id)
) ;

-- 4. Inserts
INSERT INTO university (name, logo) VALUES('Faculté universitaire de Namur','logo/unamur.png');
INSERT INTO university (name, logo) VALUES('Université catholique de Louvain','logo/ucl.png');
INSERT INTO university (name, logo) VALUES('Université libre de Bruxelles','logo/ulb.png');
INSERT INTO university (name, logo) VALUES('Katholieke Universiteit Leuven','logo/kul.png');
