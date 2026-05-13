-- Le fichier qui va insérer les différentes données dans la base (DML data management language)
USE GROUPE02;


INSERT INTO faculte (nom, image) VALUES ('Faculté de droit', 'assets/images/hp_fac_droit.ICO');
INSERT INTO faculte (nom, image) VALUES ('Faculté Économie Management Communication sciencesPo', 'assets/images/hp_ecogestion.ICO');
INSERT INTO faculte (nom, image) VALUES ('Faculté d''informatique', 'assets/images/hp_informatique.ico');
INSERT INTO faculte (nom, image) VALUES ('Faculté de médecine', 'assets/images/hp_medecine.ico');
INSERT INTO faculte (nom, image) VALUES ('Faculté de philosophie et lettres', 'assets/images/hp_philo_lettres.ico');
INSERT INTO faculte (nom, image) VALUES ('Faculté des sciences', 'assets/images/hp_sciences.ico');
INSERT INTO faculte (nom, image) VALUES ('Faculté des sciences de l''éducation et de la formation', 'assets/images/hp_education_formation.ico');


INSERT INTO users (username, password, email, role) VALUES ('Gilbert François', 'GF', 'FrancoisGilbert@gmail.com', 'admin');
INSERT INTO users (username, password, email, role) VALUES ('2shoes Tony', '2T', 'Tony2shoes@gmail.com', 'casual');
INSERT INTO users (username, password, email, role) VALUES ('Delmort Anne', 'DA', 'AnneDelmort@gmail.com', 'casual');
INSERT INTO users (username, password, email, role) VALUES ('Delevaux Mark', 'DM', 'MarkDelevaux@gmail.com', 'casual');
INSERT INTO users (username, password, email, role) VALUES ('invite', 'invite', 'invite', 'invite');


INSERT INTO cours (nom) VALUES ('ICDLB212-Anglais');
INSERT INTO cours (nom) VALUES ('INFOB212-Base de données');
INSERT INTO cours (nom) VALUES ('INFOB223-Probabilités et statistiques');
INSERT INTO cours (nom) VALUES ('INFOB224-Intelligence artificielle et optimisation');
INSERT INTO cours (nom) VALUES ('INFOB234-Conception et programmation orientée objet');
INSERT INTO cours (nom) VALUES ('INFOB236-Projet de programmation');
INSERT INTO cours (nom) VALUES ('INFOB237-Algorithmique');
INSERT INTO cours (nom) VALUES ('INFOB238-Technologie Web');


INSERT INTO synthese (nom, url, author_id, cours_id) VALUES ('Full Vocabulary List', "https://docs.google.com/document/d/1MCZjwVZP5BSvwrrSv768I2rUFADmPOKQFaI555x8I1E/edit?tab=t.0", 1, 1);
INSERT INTO synthese (nom, url, author_id, cours_id) VALUES ('Transformation shéma relationnel à shéma logique', "https://docs.google.com/document/d/1MCZjwVZP5BSvwrrSv768I2rUFADmPOKQFaI555x8I1E/edit?tab=t.0", 2, 2);
INSERT INTO synthese (nom, url, author_id, cours_id) VALUES ('Analyse Combinatoire guide complet', "https://docs.google.com/document/d/1MCZjwVZP5BSvwrrSv768I2rUFADmPOKQFaI555x8I1E/edit?tab=t.0", 3, 3);
INSERT INTO synthese (nom, url, author_id, cours_id) VALUES ('Fuzzy logic', "https://docs.google.com/document/d/1MCZjwVZP5BSvwrrSv768I2rUFADmPOKQFaI555x8I1E/edit?tab=t.0", 1, 4);
INSERT INTO synthese (nom, url, author_id, cours_id) VALUES ('Fonction d abstraction et invariant de représentation', "https://docs.google.com/document/d/1MCZjwVZP5BSvwrrSv768I2rUFADmPOKQFaI555x8I1E/edit?tab=t.0", 4, 5);
INSERT INTO synthese (nom, url, author_id, cours_id) VALUES ('HTML/CSS/JS notion de base', "https://docs.google.com/document/d/1MCZjwVZP5BSvwrrSv768I2rUFADmPOKQFaI555x8I1E/edit?tab=t.0", 2, 8);


INSERT INTO teacher (nom, prenom) VALUES ('Fiéviez', 'François-Xavier');
INSERT INTO teacher (nom, prenom) VALUES ('Cleve', 'Anthony');
INSERT INTO teacher (nom, prenom) VALUES ('Remiche', 'Marie-Ange');
INSERT INTO teacher (nom, prenom) VALUES ('Frenay', 'Benoît');
INSERT INTO teacher (nom, prenom) VALUES ('Heymans', 'Patick');
INSERT INTO teacher (nom, prenom) VALUES ('Jacquet', 'Jean-Marie');
INSERT INTO teacher (nom, prenom) VALUES ('Schobbens', 'Pierre-Yves');
INSERT INTO teacher (nom, prenom) VALUES ('Cauz', 'Maxime');


INSERT INTO fav_cours (user_id, cours_id) VALUES (1, 2);
INSERT INTO fav_cours (user_id, cours_id) VALUES (1, 3);
INSERT INTO fav_cours (user_id, cours_id) VALUES (1, 8);


INSERT INTO fav_synth (user_id, synth_id) VALUES (1, 1);
INSERT INTO fav_synth (user_id, synth_id) VALUES (2, 3);
INSERT INTO fav_synth (user_id, synth_id) VALUES (3, 5);


INSERT INTO teacher_to_cours (teacher_id, cours_id) VALUES (1, 1);
INSERT INTO teacher_to_cours (teacher_id, cours_id) VALUES (2, 2);
INSERT INTO teacher_to_cours (teacher_id, cours_id) VALUES (3, 3);
INSERT INTO teacher_to_cours (teacher_id, cours_id) VALUES (4, 4);
INSERT INTO teacher_to_cours (teacher_id, cours_id) VALUES (5, 5);
INSERT INTO teacher_to_cours (teacher_id, cours_id) VALUES (6, 6);
INSERT INTO teacher_to_cours (teacher_id, cours_id) VALUES (7, 7);
INSERT INTO teacher_to_cours (teacher_id, cours_id) VALUES (8, 8);


INSERT INTO cours_to_faculte (cours_id, faculte_id) VALUES (1, 5);
INSERT INTO cours_to_faculte (cours_id, faculte_id) VALUES (2, 3);
INSERT INTO cours_to_faculte (cours_id, faculte_id) VALUES (3, 3);
INSERT INTO cours_to_faculte (cours_id, faculte_id) VALUES (4, 3);
INSERT INTO cours_to_faculte (cours_id, faculte_id) VALUES (5, 3);
INSERT INTO cours_to_faculte (cours_id, faculte_id) VALUES (6, 3);
INSERT INTO cours_to_faculte (cours_id, faculte_id) VALUES (7, 3);
INSERT INTO cours_to_faculte (cours_id, faculte_id) VALUES (8, 3);

INSERT INTO commentaire (contenu, synthese_id, author_id) VALUES ("Ceci est un test de commentaire", 2, 1);