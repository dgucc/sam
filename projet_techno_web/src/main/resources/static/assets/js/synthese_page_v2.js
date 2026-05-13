//Corps principal
const params = new URLSearchParams(window.location.search);
const synth_id = params.get("id");
console.log(synth_id);
// const csrfToken = getCookie("XSRF-TOKEN");
// console.log(document.cookie);
// console.log(csrfToken);

load_page_data();

//===================================
//Fonctions
//===================================

//charge et affiche les différentes informations de la page
function load_page_data(){
    fetch('http://localhost:8080/SynthInfo/' + synth_id)
        .then(response => response.json()) // Convertir la réponse en JSON
        .then(data => {
            console.log(data);
            print_synth_info(data);
            print_author_info(data.user);

            add_favorite_synth_button();
            add_delete_synth_button(data);
        })
        .catch(error => {
            console.error('Erreur :', error);
    });
}

//charge et affiche les différents commentaires de la page
function load_comments(user){
    fetch('http://localhost:8080/commentaires/' + synth_id)
            .then(response => response.json()) // Convertir la réponse en JSON
            .then(data => {
                console.log(data);
                print_comments(data, user);
            })
            .catch(error => {
                console.error('Erreur :', error);
        });
}

//Affiche les informations de la synthèse
function print_synth_info(synth){
    let name_block = document.getElementById("titre_synthese");
    name_block.textContent = synth.nom;

    let url_block = document.getElementById("synth_url");
    url_block.href = synth.url;

    let class_name_block = document.getElementById("nom_cours");
    class_name_block.textContent = synth.cours.nom;
}

//Affiche les informations de l'auteur
function print_author_info(author){
    let name_block = document.getElementById("auteur_nom");
    name_block.textContent = author.username;

    let email_block = document.getElementById("auteur_email");
    email_block.textContent = author.email;
}

// Fonction pour lire un cookie
// function getCookie(name) {
//     const value = document.cookie
//         .split("; ")
//         .find(row => row.startsWith(name + "="))
//         ?.split("=")[1];

//     return value ? decodeURIComponent(value) : null;
// }

//Pour gérer l'ajout ou le retrait aux favoris de la synthèse
function add_favorite_synth_button(){
    fetch('http://localhost:8080/isFavoriteSynth/' + synth_id)
        .then(response => response.json()) // Convertir la réponse en JSON
        .then(isFavorite => {
            console.log(isFavorite);
            let favorite_button = document.getElementById("favorite_button");

            if(isFavorite){
                //Pour retirer des favoris
                var request = "/retrieveFavoriteSynth/" + synth_id;
                var method_request = "DELETE";
                favorite_button.textContent = "Retirer des favoris";
            }
            else{
                //Pour mettre en favoris
                var request = "/newFavoriteSynth/" + synth_id;
                var method_request = "POST";
                favorite_button.textContent = "Ajouter aux favoris";
            }

            document.getElementById("favorite_button").addEventListener("click", () => {
                    fetch(request, {
                        method: method_request,
                        // credentials: "include",
                        // headers: { "X-XSRF-TOKEN": csrfToken}
                    })
                    .then(res => {
                        if (res.ok) {
                            // add_favorite_button();
                            window.location.reload();
                        }
                    });
                })
        })
        .catch(error => {
            console.error('Erreur :', error);
    });
}

//Pour le bouton qui permet de supprimer la synthèse (réservé au modérateur ou à l'auteur de la synthèse)
//On profite aussi de faire la requête "/me" pour pouvoir afficher les commentaires correctement
function add_delete_synth_button(synth_info){
    fetch('http://localhost:8080/me')
        .then(response => response.json()) // Convertir la réponse en JSON
        .then(user => {
            console.log(user);
            console.log(synth_info);
     
            load_comments(user)
            if(user.role == "admin" || synth_info.user.id == user.id){
                print_delete_synth_button();
            }
        })
        .catch(error => {
            console.error('Erreur :', error);
    });
}

function print_delete_synth_button(){
    let delete_button = document.createElement("button");
    delete_button.id = "delete_button";
    delete_button.classList.add("smooth_policy");
    delete_button.classList.add("btn-delete");
    delete_button.textContent = "Supprimer cette synthèse";

    let button_block = document.getElementById("button_block");
    button_block.appendChild(delete_button);

    delete_button.addEventListener("click", () =>{
        fetch("/deleteSynth/" + synth_id,{
            method: "DELETE"
        })
        .then(res => {
            if (res.ok) {
                window.location = "http://localhost:8080/my_synth_page.html"; //Pour retourner à la page des synthèses personnelles
            }
        });
    })
}

//Affiche les différents commentaires
function print_comments(comments, user){
    let comment_list = document.getElementById("liste_commentaires");
    for (let comment of comments){
        let comment_content = document.createElement("div");
        comment_content.textContent = comment.user.username + " : " + comment.contenu;
        comment_content.classList.add("doc-block");

        //On rajoute le bouton pour supprimer ce commentaire si l'utilisateur est un admin ou si il est l'auteur du commentaire
        if(user.role == "admin" || comment.user.id == user.id){
                add_delete_comment_button(comment_content, comment.id);
        }

        comment_list.appendChild(comment_content);
    }
}

//Pour rajouter un bouton de suppresion à un commentaire
function add_delete_comment_button(comment_block, comment_id){
    let delete_button = document.createElement("button");
    delete_button.classList.add("smooth_policy");
    delete_button.classList.add("btn-delete");
    delete_button.textContent = "Supprimer";

    delete_button.addEventListener("click", () =>{
        fetch("/DeleteComment/" + comment_id,{
            method: "DELETE"
        })
        .then(res => {
            if (res.ok) {
                window.location.reload(); //Pour retourner à la page des synthèses personnelles
            }
        });
    })

    comment_block.appendChild(delete_button);
}

//===================================
//Pour les eventListeners
//===================================

//Poster un commentaire
document.getElementById("post_comment").addEventListener("click", () => {
    const contenu = document.getElementById("comment_content").value;
    if (!contenu) return;
    console.log("/PostComment/" + synth_id);

    fetch("/PostComment/" + synth_id, {
        method: "POST",
        // credentials: "include",
        headers: { "Content-Type": "application/json",
                //    "X-XSRF-TOKEN": csrfToken
         },
        body: JSON.stringify({
            contenu: contenu,
})
    }).then(res => {
        //Pour immédiatement retirer le champ de commentaire
        if (res.ok) {
            window.location.reload();
        }
    });
});