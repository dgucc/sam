//Corps principal
load_class_options();


//===================================================
//Fonctions
//===================================================

//Pour charger et afficher les différents cours qui apparaissent dans les options
function load_class_options(){
    fetch('http://localhost:8080/favoriteClassList')
        .then(response => response.json()) // Convertir la réponse en JSON
        .then(data => {
            // Afficher les universités
            console.log(data);
            print_class_options(data);
        })
        .catch(error => {
            console.error('Erreur :', error);
    });
}

//Pour afficher les différents cours dans les options
function print_class_options(classes){
    let class_list = document.getElementById("class_list");
    for (let cours of classes){
        let cours_option = document.createElement("option");
        cours_option.textContent = cours.nom;
        cours_option.id = cours.id;
        class_list.appendChild(cours_option);
    }
}


//===================================
//Pour les eventListeners
//===================================

//Poster la synthèse
document.getElementById("submit_synth_button").addEventListener("click", () => {

    let titre = document.getElementById("titre_synth").value;
    let url = document.getElementById("lien_synth").value;
    let class_options = document.getElementById("class_list");
    let cours_id = class_options.options[class_options.selectedIndex].id;

    //Vérification
    if (!titre)return;
    if (!url.startsWith("https://"))return;

    console.log("titre : " + titre + " url : " + url + " cours_id : " + cours_id);

    fetch("/new_synth", {
        method: "POST",
        // credentials: "include",
        headers: { "Content-Type": "application/json",
                //    "X-XSRF-TOKEN": csrfToken
        },
        body: JSON.stringify({
            titre:titre,
            url:url,
            cours_id:cours_id
        })
    }).then(res => {
        if (res.ok) {
            window.location = "http://localhost:8080/my_synth_page.html"; //Pour retourner à la page des synthèses personnelles
        }
    });
});