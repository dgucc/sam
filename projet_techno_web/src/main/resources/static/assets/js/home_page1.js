//Corps principale du code
load_data();

// L'URL du service REST à interroger
function load_data(){
    fetch('http://localhost:8080/facultes')
        .then(response => response.json()) // Convertir la réponse en JSON
        .then(data => {
            // Afficher les universités
            console.log(data);
            print_faculte(data);
        })
        .catch(error => {
            console.error('Erreur :', error);
    });
}

//Pour rajouter les éléments de la liste des facultés
function print_faculte(data){
    let faculte_block = document.getElementById("liste_faculte");
    // const facultes = JSON.parse(json_string);
    for (let faculte_el of data){
        let a = document.createElement("a");
        a.href = "http://localhost:8080/faculte/" + faculte_el.id;

        let faculte = document.createElement("div");
        faculte.classList = "faculte";

        let image_container = document.createElement("div");
        image_container.classList = "image";

        let image = document.createElement("img");
        image.src = faculte_el.image;

        let name = document.createElement("div");
        name.classList.add("nom_faculte");
        name.classList.add("smooth_policy");
        name.textContent = faculte_el.nom;

        image_container.appendChild(image);
        faculte.appendChild(image_container);
        faculte.appendChild(name);
        a.appendChild(faculte);
        faculte_block.appendChild(a);
    }
}