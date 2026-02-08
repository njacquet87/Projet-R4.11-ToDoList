# Projet-R4.11-ToDoList

Projet To do list R4.11 Jacquet Noé

<img width="671" height="438" alt="diagramme_classe drawio" src="https://github.com/user-attachments/assets/fadb5f9a-8336-49f1-b012-42526eedde06" />

Pour la conception du projet, j'ai choisi de faire un pattern État. Le patern état permet de gérer les actions de la tâche en fonction de son état.

- État inProgress : l'état pourra passer en état fini quand l'utilisateur aura fini la tâche.
- État done : l'état est fini, l'utilisateur pourra donc supprimer la tâche.
- État overdue : la tâche à dépassée la date limite mais l'utilisateur pourra quand même la marquer en fini.

Le pattern état permet d'avoir une bonne conception car il respecte les principe SOLID.
La classe User va servir à la création, la suppression ou a modification des tâches.
