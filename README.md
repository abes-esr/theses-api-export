# theses-api-export
[![build-test-pubtodockerhub](https://github.com/abes-esr/theses-api-export/actions/workflows/build-test-pubtodockerhub.yml/badge.svg)](https://github.com/abes-esr/theses-api-export/actions/workflows/build-test-pubtodockerhub.yml) [![Docker Pulls](https://img.shields.io/docker/pulls/abesesr/theses.svg)](https://hub.docker.com/r/abesesr/theses/)

Le moteur de recherche theses.fr recense l’ensemble des thèses de doctorat soutenues en France depuis 1985, les sujets de thèse préparés au sein des établissements de l’enseignement supérieur français, et les personnes impliquées dans la recherche doctorale française. 

Ce dépôt héberge le code source de l'API Export de theses.fr.

L’API permet d’exporter les métadonnées descriptives d’une thèse soutenue ou d’une thèse en préparation aux formats XML ou RDF ainsi que la référence bibliographique d’une thèse soutenue ou d’une thèse en préparation aux formats RIS et BibTeX

L'API s'adresse à toutes les personnes qui souhaitent récupérer les données relatives aux thèses de doctorat pour les réutiliser au sein de leur propre système d'information ou à des fins de recherche et de statistiques, par exemple les gestionnaires de métadonnées, enseignants-chercheurs, data scientist, bibliothécaires, etc.

URL publique : [https://theses.fr/api/v1/export/](https://theses.fr/api/v1/export/openapi.yaml))

![logo theses.fr](https://theses.fr/icone-theses-beta.svg)

L'application complète peut être déployée via Docker à l'aide du dépôt https://github.com/abes-esr/theses-docker

## Données exposées par l'API : 

Les métadonnées descriptives de la thèses (formats XML et RDF) comprennent :

### Un bloc décrivant la notice :

* URL de la notice
* Identifiants pérenne du créateur de la notice (l’Abes)
* Date de création et de dernière mise à jour de la notice dans theses.fr (en UTC)

 
### Un bloc décrivant :

* La thèse : titre, année de soutenance ou d’inscription en doctorat, type de document, langue, résumés, mots-clés, classification Dewey, URL d’accès au texte intégral
* Les personnes liées à la thèse ; auteur (nom, prénom et identifiant), directeur de thèse (nom, prénom et identifiant), membres du jury
* Les structures liées à la thèse : établissement de soutenance (nom et identifiant), école doctorale (nom et identifiant), partenaires de recherche (nom et identifiant)

### Les références bibliographiques (formats RIS et BibTeX) comprennent :
* Le titre
* L’auteur
* Le directeur de thèse
* La date de soutenance
* Le type de document
* L’URL pérenne de la page theses.fr et l’URL pérenne de diffusion du fichier de thèse
* La description matérielle du document
* Le Numéro National de Thèse
* La note de thèse (type de diplôme, établissement de soutenance, discipline, année de soutenance).

## Architecture technique

Il y a 3 API pour Theses.fr : 
* https://github.com/abes-esr/theses-api-recherche pour la recherche et l'affichage de theses
* **https://github.com/abes-esr/theses-api-export pour les exports des theses en différents formats (CSV, XML, BIBTEX, etc)** correspondant à ce dépot
* https://github.com/abes-esr/theses-api-diffusion pour la mise à disposition des documents (PDFs et autres)

L'API présente est écrite en Java 17, à l'aide du framework Spring Boot 2.

Elle est déployée automatiquement dans le SI de l'Abes sous forme d'un container docker, à l'aide de la chaine CI/CD Github.

