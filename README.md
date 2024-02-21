# Médilabo - Projet Étudiant

Médilabo est un projet étudiant ayant pour objectif de créer un outil pour aider les médecins à identifier les patients les plus à risque de développer du diabète. Il s'agit d'une application en architecture Micro-Service développée avec Java Spring et Dockerisée.

## Contexte

Les patients souffrant de mauvais choix nutritionnels sont particulièrement exposés au diabète de type 2. Pour prévenir cette maladie, il est important de pouvoir identifier les patients à risque et de leur fournir les conseils et les soins appropriés. C'est dans ce contexte que le projet Médilabo a été développé.

Ce projet propose une solution avec une interface graphique. Le médecin peut accéder à sa liste de patients, en ajouter de nouveaux, les modifier, ou les supprimer. Il est aussi possible d'ajouter des notes pendant les rendez-vous avec les patients. Ces notes sont traitées avec un service de traitement de données pour alerter le médecin d'éventuels risques de diabète. En fonction de l'âge, du sexe et du contenu des notes, un indicateur de risque peut être affiché pour le patient.

## Technologies Utilisées 

- Java Spring
- Spring Cloud Gateway
- Authentification avec OAuth
- Client avec Thymeleaf
- MySQL
- MongoDB
- Docker
- Docker-Compose

## Structure du Projet

Le projet est composé de plusieurs microservices : 
- un Spring Cloud Gateway
- un Eureka
- un Config Server
- un Server d'Authorization OAuth
- un client avec Thymeleaf
- une API utilisant MySQL
- une API utilisant MongoDB
- et un service de traitement de données.

Chaque microservice est Dockerisé avec un docker-compose pour les gérer.

