# Projet-R4.11-ToDoList

Projet To do list R4.11 Jacquet Noé

<br>

## Diagramme de classe : 

<br>

<img width="671" height="438" alt="diagramme_classe drawio" src="https://github.com/user-attachments/assets/fadb5f9a-8336-49f1-b012-42526eedde06" />

<br>

Pour la conception du projet, j'ai choisi de faire un pattern État. Le patern état permet de gérer les actions de la tâche en fonction de son état.

- État inProgress : l'état pourra passer en état fini quand l'utilisateur aura fini la tâche.
- État done : l'état est fini, l'utilisateur pourra donc supprimer la tâche.
- État overdue : la tâche à dépassée la date limite mais l'utilisateur pourra quand même la marquer en fini.

<br>

Le pattern état permet d'avoir une bonne conception car il respecte les principe SOLID.
La classe User va servir à la création, la suppression ou a modification des tâches.

<br>

## Maquettes Figma :

<br>

Voici mes première maquette de l'application.

<br>

Le nom de l'application est "// TODO" qui fais référence au // TODO que l'on peut mettre dans notre code.

<br>

Lorsqu'un utilisateur se connecte pour la première fois, on lui demande son nom et son prenom. Une fois rentré, ils ne seront plus demandé (même si l'utilisateur pourra les modifier d'autre version).

<br>

La page d'acceuil est composée de la liste des tâches, de leurs titres ainsi que de leurs états.
L'utilisateur peut les modifier ou les supprimmer ou accèder à la page de détail de la tâche.

<br>

<img width="638" height="800" alt="image" src="https://github.com/user-attachments/assets/9b4e95fb-c01c-49c9-8d81-ad8e638949fc" />

## Choix technique ROOM :

Pour le stackage des données, j'ai choisie d'utiliser ROOM, une bibliothèque officielle d’Android reposant sur SQLite. ROOM permet de faire une base de données en local (ne nécessite donc pas de connéxion contrairement à une API par exmple) et est composée de plusieurs classes : 

- Les classes Entity : classes correspondantes aux entitées de la BDD.
- La classe DAO : permet d'interagir avec la BDD (query, insert, delete etc...)
- La classe DataBase : contient la BDD.

sources : 
- https://developer.android.com/training/data-storage/room?hl=fr#kotlin
- https://developer.android.com/codelabs/basic-android-kotlin-compose-persisting-data-room?hl=fr#3
