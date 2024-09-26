package fr.abes.theses.export.controller;

import fr.abes.theses.export.service.DbRequest;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/v1/outils/")
public class OutilsController {

    @Autowired
    DbRequest dbRequest;

//    @GetMapping(value = "algoTheses",produces = "application/vnd.ms-excel")
    @GetMapping(value = "algoTheses")
    public ResponseEntity algoTheses(
            @RequestParam @Parameter(name = "etat", description = "Code du problème, multivalué possible", example = "52") Optional<List<Integer>> etat,
            @RequestParam @Parameter(name = "type", description = "Type d'intervention, multivalué possible", example = "amelioration") Optional<List<String>> type,
            @RequestParam @Parameter(name = "codeetab", description = "Code de l'établissement", example = "NICE") Optional<List<String>> codeetab,
            @RequestParam @Parameter(name = "nnt", description = "Numéro national de thèse", example = "2020MONT1234") Optional<List<String>> nnt,
            @RequestParam @Parameter(name = "ppn", description = "Identifiant de la notice", example = "236863509") Optional<List<String>> ppn,
            @RequestParam @Parameter(name = "utilisateur", description = "Type d'utilisateur", example = "reseau") Optional<List<String>> utilisateur,
            @RequestParam @Parameter(name = "rcr", description = "Identifiant de la bibliothèque", example = "751052105") Optional<List<String>> rcr,
            @RequestParam @Parameter(name = "sort", description = "Ordre de tri avec noms du (ou des) champ(s)", example = "type,codeEtab,ppn") Optional<List<String>> sort,
            @RequestParam @Parameter(name = "annee", description = "Année de soutenance", example = "1985") Optional<String> annee,
            @RequestParam @Parameter(name = "rownum", description = "Nombre de lignes à renvoyer", example = "1000") Optional<String> rownum
            ) {
        return new ResponseEntity<>(dbRequest.getDiagnosticAlgoTheses(
                etat,
                type,
                codeetab,
                nnt,
                ppn,
                utilisateur,
                rcr,
                sort,
                annee,
                rownum), HttpStatus.OK);
    }
}
