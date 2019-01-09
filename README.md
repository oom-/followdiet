# Followdiet
Une application Androide simple aidant au suivit de régime précis au gramme près.

## Comment ça marche ?
### L'icône
<img src="https://github.com/oom-/followdiet/blob/master/screenshots/Screenshot_Home.jpg?raw=true" width="250"></br>
Un simple kiwi.

### Le profil
<img src="https://github.com/oom-/followdiet/blob/master/screenshots/Screenshot_Profil_FollowDiet.jpg?raw=true" width="250"></br>
1. Votre nom
2. Votre poids
3. Votre objectif calorique journalier
4. Sauvegarder les modifications
5. Supprimer votre profil et toutes les informations (aliments sauvegardés, historiques etc...)
6. Permet de passer le suivi en mode cétogène (les objectifs journaliers sont calculés différements)

### L'accueil
<img src="https://github.com/oom-/followdiet/blob/master/screenshots/Screenshot_Accueil_FollowDiet.jpg?raw=true" width="250"></br>
1. Votre nom - Date du jour
2. Objectif calorique atteint concernant la journée actuelle comparé à l'objectif journalier
3. Nombre de proteines / glucides / lipides consommés et comparés à l'objectif journalier
4. Temps restant avant la remise à zéro des objectifs (fin de la journée)

<img src="https://github.com/oom-/followdiet/blob/master/screenshots/Screenshot_Accueil2_FollowDiet.jpg?raw=true" width="250"></br>
1. Bouton d'ajout d'un aliment à l'historique journalier
2. Bouton d'ajout d'un aliment à la base de données des aliments connus
3. Bouton d'édition du profil
4. Bouton de modification / suppression d'un aliment dans l'historique journalier
4. Bouton de modification / suppression d'un aliment dans la base de données des aliments connus
6. Bouton d'affichage des statistiques (basées sur l'historique des historiques journalier)
7. Bonton d'affichage du combleur

### Ajout aliment à la base de données
<img src="https://github.com/oom-/followdiet/blob/master/screenshots/Screenshot_AdDb_FollowDiet.jpg?raw=true" width="250"></br>
1. Nom de l'aliment à ajouté
2. Sa valeur de proteines en gr
3. Sa valeur de glucides en gr
4. Sa valeur de lipides en gr
5. Choix de l'unité : gr / unité / autre (géré comme unité pour le moment)
6. Ajout de l'aliment et vide les champs pour ajouter un nouvel aliment à la suite
7. Ajout l'aliment et retourne à l'accueil

### Modification d'un aliment dans la base de données
<img src="https://github.com/oom-/followdiet/blob/master/screenshots/Screenshot_ModDb_FollowDiet.jpg?raw=true" width="250"></br>
1. Choix de l'aliment à modifier / supprimer
2. Modification de la valeur de proteines en gr
3. Modification de la valeur de glucides en gr
4. Modification de la valeur de lipides en gr
5. Modification du type d'unité : gr / unité / autre (géré comme unité pour le moment)
6. Sauvegarder la modification de l'aliment
7. Supprimer l'aliment de la base de données

> ⚠️ Lorsqu'un aliment est mis à jour dans la base de données, l'ensemble des historiques contenant cet aliment est mis à jour en même temps.

### Ajout d'un aliment à l'historique journalier (du jour)
<img src="https://github.com/oom-/followdiet/blob/master/screenshots/Screenshot_AjoutHisto_FollowDiet.jpg?raw=true" width="250"></br>
1. Date du jour actuel
2. Nom de l'aliment à ajouter
3. Quantité de l'aliment à ajouter (avec l'unité à sa droite)
4. Bouton d'ajout

### Modification d'un aliment dans l'historique journalier (du jour)
<img src="https://github.com/oom-/followdiet/blob/master/screenshots/Screenshot_ModHisto_FollowDiet.jpg?raw=true" width="250"></br>
1. Date du jour à modifier
2. Nom de l'aliment du jour à modifier
3. Modification de la quantité de l'aliment
4. Sauvegarder la modification de l'aliment dans l'historique
5. Supprimer l'aliment de l'historique

### Utilisation du combleur
<img src="https://github.com/oom-/followdiet/blob/master/screenshots/Screenshot_Combleur_FollowDiet.jpg?raw=true" width="250"></br>
1. Date du jour
2. Selectionner un aliment
3. Affichage du nombre de gr restant pour chaque matro-nutriment (proteines, glucides, lipides)
4. Affichage de la quantité (dans l'unité de l'aliment) de l'aliment qu'il faudrait ingérer pour atteindre l'objectif journalier

### Affichage des statistiques
<img src="https://github.com/oom-/followdiet/blob/master/screenshots/Screenshot_Stats_FollowDiet.jpg?raw=true" width="250"></br>
1. Nombre de jours dont l'objectif journalier à été atteint (objectif -10kcal comme limite)
2. Liste des jours dont l'objectif journalier à été atteint (kiwi -> ok / croix rouge -> ko)
3. Total des proteines, glucides, lipides et kcal ingéré au cours de la période
4. Le nombre d'animaux que ça peut représenter en proteines (au moins)
5. Nettoyer l'ensemble de tous les historiques depuis le début
