//Corps du programme
load_data();

//Pour recevoir et afficher les données
function load_data(){
    //Pour obtenir la liste des synthèses favorites
    fetch('http://localhost:8080/favoriteSynthList')
        .then(response => response.json()) // Convertir la réponse en JSON
        .then(data => {
            // Afficher les synthèses favorites
            console.log(data);
            print_favorite_synth(data);
        })
        .catch(error => {
            console.error('Erreur :', error);
    });
    //Pour obtenir la liste des cours favoris
    fetch('http://localhost:8080/favoriteClassList')
        .then(response => response.json()) // Convertir la réponse en JSON
        .then(data => {
            // Afficher les cours favoris
            console.log(data);
            print_favorite_class(data);
        })
        .catch(error => {
            console.error('Erreur :', error);
    });
    //Pour obtenir les infos minimales de l'utilisateur
    fetch('http://localhost:8080/me')
        .then(response => response.json()) // Convertir la réponse en JSON
        .then(data => {
            // Afficher les cours favoris
            console.log(data);
        })
        .catch(error => {
            console.error('Erreur :', error);
    });
}

//Pour rajouter les éléments de la liste des syntheses favorites
function print_favorite_synth(data){
    let favoriteBlock = document.getElementById("synth_favoris");
    for (let favorite of data){
        let a = document.createElement("a");
        a.href = favorite.url;
        let div = document.createElement("div");
        div.classList.add("synth_element");
        div.classList.add("smooth_policy");
        div.textContent = favorite.cours.nom + " | " + favorite.nom + " | " + favorite.user.username;

        a.appendChild(div);
        favoriteBlock.appendChild(a);
    }
}

//Pour rajouter les éléments de la liste des cours favoris
function print_favorite_class(data){
    let favorite_class_block = document.getElementById("cours_favoris");
    for (let favorite of data){
        let a = document.createElement("a");
        a.href = "http://localhost:8080/cours/" + favorite.id;
        let div = document.createElement("div");
        div.classList.add("class_element");
        div.classList.add("smooth_policy");
        div.textContent = favorite.nom;

        a.appendChild(div);
        favorite_class_block.appendChild(a);
    }
}