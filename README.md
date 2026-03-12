# Projet-R4.11-ToDoList

Projet To do list R4.11 Jacquet Noé

<br>

## Spécifications fonctionnelles de //TODO :

V0 : 
- Création d’une tâche
- Listage des tâches
- Navigabilité sur l’application
- Mise en place de Room pour le stockage de données

V1 : 
- Modification de la tâche
- Marquer la tâche comme réalisée

V2 : 
- Filtrer la tâche par état
- Changement du statut quand la tâche et en retard (avec WorkManager) et envoi d’une notification
- Ajout d’une animation de feu d’artifice et envoi d’une notification quand une tâche est réalisée.

V3 : 
- Suppression des tâches
- Ajout et modification de la périodicitée des tâches
- Changement du statut lors de la modification de la date (si on mes une date ou une heure antérieure à la date / heure actuelle, le statut devient “En retard”)
- L’utilisateur peut réaliser la tâche depuis le menu Home

V4 : 
- Ajout et modification de la priorisation des tâches (1 : haute, 2 : moyenne, 3 : basse)
- Les tâches sont triées par prioritée croissante

V5 : 
- Ajout d’une nouvelle table User pour récupérer le nombre de tâches acomplies
- Affichage du nombre de tâches effectuées et d’une icone de trophé qui change de couleur en fonction du nombre de tâches réalisées
- Ajout d’une Box pour visualiser les paliers (20 : Bronze, 50 : Argent, 100 : or et 200 : diamant)
- Mise à jour : l’utilisateur peut enlever la date et l’heure d’une tâche dans la page modifier.

V6 : 
- Ajout, modification et suppression de l’image de la tâche stocké sous forme de uri et utilisation d’un FileProvider pour accéder aux fichiers de l’appareil

V7 : 
- Ajout de nouveaux filtres

<br>

## Comparatif technique :

Pour le stackage des données, j'ai choisie d'utiliser ROOM, une bibliothèque officielle d’Android reposant sur SQLite. ROOM permet de faire une base de données en local et est composée de plusieurs classes : 

- Les classes Entity : classes correspondantes aux entitées de la BDD.
- La classe DAO : permet d'interagir avec la BDD (query, insert, delete etc...)
- L’interface Repository : utilise les methode du DAO pour modifier la BDD
- La classe DataBase : contient la BDD.

J’ai aussi mis en place un viewModel pour accéder aux données de la BDD depuis l’interface utilisateur. Le ViewModel va reprendre les méthode d’un repository pour les utiliser dans les pages ou les components.

### Alternatives : 

L’utilisation d’une API REST avec une BDD sur un serveur. Cette alternative peut être couteuse car il faut maintenir un serveur ainsi que mettre en place une API REST ce qui est plus compliqué que ROOM.

<br>

## Diagramme de classe : 

<br>

La MainActivity va lancer la ToDoApp qui va mettre en place le Worker. Elle initialise aussi la TaskDatabase qui prend un TaskDao et un UserDao. Les Dao utilisent leurs Entity respectives et ils sont utilisés dans les Repositories pour accéder au méthodes. La MainActicvity utilise le AppNavigation qui fais la navigation entre les pages. Les pages utilisent des components. Elles utilisent aussi les ViewModel qui servent à faire le lien entre les repositories de la BDD et l’interface utilisateur. La MainActivity possède aussi une fonction qui demande la permission pour avoir le droit d’envoyer des notifications qui sont envoyées via un NotificationHelper avec un NotificationChannel.

<br>

<img width="1182" height="1113" alt="Diagramme_classe_final drawio" src="https://github.com/user-attachments/assets/9c1b58c6-e66f-4135-9769-0a237149d3ca" />

<br>

## Maquettes Figma :

<br>

Voici mes maquette de l'application (différent du résultat final).

<br>

Le nom de l'application est "//TODO" qui fais référence aux //TODO que l'on peut mettre dans notre code.

<br>

La page d'acceuil est composée de la liste des tâches, de leurs titres ainsi que de leurs états.
L'utilisateur peut les modifier ou les supprimmer ou accèder à la page de détail de la tâche.

<br>

![maquette_todo](https://github.com/user-attachments/assets/180f6312-28fc-46db-8f45-1848beb6a8f2)
