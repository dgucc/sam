const params = new URLSearchParams(window.location.search);
const coursId = params.get("id");
const userId = parseInt(sessionStorage.getItem("user_id"));

// Charger le nom du cours
fetch(`/CoursInfo/${coursId}`)
    .then(res => res.json())
    .then(cours => {
        document.querySelector(".titre").textContent = cours.nom;
        // Passer le cours_id à la page de nouvelle synthese
        document.querySelector(".new_synth_button").href = `new_synthese.html?id=${coursId}`;
    });

// Charger les syntheses favorites du cours
fetch(`/favoriteSynthList`)
    .then(res => res.json())
    .then(favSynths => {
        const container = document.getElementById("liste-favoris");
        container.innerHTML = "";
        const favDuCours = favSynths.filter(s => s.cours.id == coursId);
        favDuCours.forEach(s => {
            const a = document.createElement("a");
            a.href = `synthese_page.html?id=${s.id}`;
            a.className = "colonne_element smooth_policy";
            a.innerHTML = `<span class="star">★</span> ${s.nom}`;
            container.appendChild(a);
        });
        if (favDuCours.length === 0) {
            container.innerHTML = `<span style="color:#aaa;font-size:0.9em;">Aucun favori pour ce cours</span>`;
        }
    });

// Charger toutes les syntheses du cours
fetch(`/ClassSynthList/${coursId}`)
    .then(res => res.json())
    .then(synths => {
        const container = document.getElementById("liste-toutes");
        container.innerHTML = "";
        synths.forEach(s => {
            const a = document.createElement("a");
            a.href = `synthese_page.html?id=${s.id}`;
            a.className = "colonne_element smooth_policy";
            a.innerHTML = `<span class="star">☆</span> ${s.nom}`;
            container.appendChild(a);
        });
        if (synths.length === 0) {
            container.innerHTML = `<span style="color:#aaa;font-size:0.9em;">Aucune synthèse pour ce cours</span>`;
        }
    });

//Rajouter le bouton pour mettre le cours en favoris
fetch('http://localhost:8080/isFavoriteClass/' + coursId)
    .then(response => response.json()) // Convertir la réponse en JSON
    .then(isFavorite => {
        console.log("Réponse : " + isFavorite);
        let favorite_button = document.createElement("div");
        favorite_button.classList.add("new_synth_button");
        favorite_button.classList.add("smooth_policy");

        if(isFavorite){
            //Pour retirer des favoris
            var request = "/retrieveFavoriteClass/" + coursId;
            var method_request = "DELETE";
            favorite_button.textContent = "retirer des favoris";
        }
        else{
            //Pour mettre en favoris
            var request = "/newFavoriteClass/" + coursId;
            var method_request = "POST";
            favorite_button.textContent = "ajouter aux favoris";
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

        let buttons_block = document.getElementById("buttons_list");
        buttons_block.appendChild(favorite_button);
    })
    .catch(error => {
        console.error('Erreur :', error);
});