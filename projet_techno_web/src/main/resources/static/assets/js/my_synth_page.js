//Corps principale du code
load_data();

//Pour revevoir et afficher les synthèses personnelles
function load_data(){
    fetch('http://localhost:8080/personnalSynth')
        .then(response => response.json()) // Convertir la réponse en JSON
        .then(data => {
            // Afficher les synthèses personnelles
            console.log(data);
            print_my_synth(data);
        })
        .catch(error => {
            console.error('Erreur :', error);
    });
}

//Pour rajouter les éléments de la liste des syntheses personnelles
function print_my_synth(data){
    let synth_block = document.getElementById("liste_mes_syntheses");
    for (let synth of data){
        let a = document.createElement("a");
        a.href = "http://localhost:8080/Synthese/" + synth.id;
        let div = document.createElement("div");
        div.classList.add("synth_element");
        div.classList.add("smooth_policy");
        div.textContent = synth.cours.nom + " | " + synth.nom;

        a.appendChild(div);
        synth_block.appendChild(a);
    }
}