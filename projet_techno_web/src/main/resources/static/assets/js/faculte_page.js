//Corps principale du code
const params = new URLSearchParams(window.location.search);
const faculte_id = params.get("id");
console.log("id" + faculte_id);

load_data(faculte_id);

// L'URL du service REST à interroger
function load_data(faculte_id){
    //Pour avoir la liste des cours d'une faculte
    fetch('http://localhost:8080/FaculteClassList/' + faculte_id)
        .then(response => response.json()) // Convertir la réponse en JSON
        .then(data => {
            // Afficher les universités
            console.log(data);
            print_cours(data);
        })
        .catch(error => {
            console.error('Erreur :', error);
    });
    //Pour avoir les informations de la faculte
    fetch('http://localhost:8080/faculte_info/' + faculte_id)
        .then(response => response.json()) // Convertir la réponse en JSON
        .then(data => {
            // Afficher les universités
            console.log(data);
            print_faculte_name(data);
        })
        .catch(error => {
            console.error('Erreur :', error);
    });
}

//Pour rajouter les éléments de la liste des cours
function print_cours(data){
    let class_block = document.getElementById("list_cours");
    for (let cours of data){
        let cours_block = document.createElement("div");
        cours_block.classList.add("cours_block");
        let a = document.createElement("a");
        a.href = "http://localhost:8080/cours/" + cours.id;
        let div = document.createElement("div");
        div.classList.add("class_element");
        div.classList.add("smooth_policy");
        div.textContent = cours.nom;
        a.appendChild(div);

        //Pour le bouton pour mettre ou retirer en favoris
        add_favorite_button(cours_block, cours.id);
        cours_block.appendChild(a);
       
        class_block.appendChild(cours_block);
    }
}

//Pour afficher le nom de la faculte
function print_faculte_name(data){
    let faculte_name_block = document.getElementById("faculte_name");
    faculte_name_block.textContent = data.nom;
}

//Pour gérer l'ajout ou le retrait aux favoris du cours
function add_favorite_button(class_block, cours_id){
    fetch('http://localhost:8080/isFavoriteClass/' + cours_id)
        .then(response => response.json()) // Convertir la réponse en JSON
        .then(isFavorite => {
            console.log("Réponse : " + isFavorite);
            let favorite_button = document.createElement("div");
            favorite_button.classList.add("btn-green");
            class_block.appendChild(favorite_button);

            if(isFavorite){
                //Pour retirer des favoris
                var request = "/retrieveFavoriteClass/" + cours_id;
                var method_request = "DELETE";
                favorite_button.textContent = "★";
            }
            else{
                //Pour mettre en favoris
                var request = "/newFavoriteClass/" + cours_id;
                var method_request = "POST";
                favorite_button.textContent = "☆";
            }

            favorite_button.addEventListener("click", () => {
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