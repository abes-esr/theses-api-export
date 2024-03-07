package fr.abes.theses.export.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/api/v1/export/")
public class RedirectionController {

    @Value("${racine}")
    String racine;

    @Operation(
            summary = "Url pour maintenir la compatibilité avec les urls de theses.fr v1, redirige sur l'API au format d'url standard.",
            description = "Url pour maintenir la compatibilité avec les urls de theses.fr v1, redirige sur l'API au format d'url standard.")
    @ApiResponse(responseCode = "400", description = "Le format du numéro national de thèse fourni est incorrect")
    @ApiResponse(responseCode = "200", description = "Opération terminée avec succès")
    @ApiResponse(responseCode = "500", description = "Service indisponible")
    @GetMapping(value = "{nntOuNumsujet}.xml")
    public ResponseEntity<Void> redirectXML(@PathVariable @Parameter(name = "nntOuNumsujet", description = "Numéro National de Thèse ou numéro de sujet", example = "2013MON30092") String nntOuNumsujet){

        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).location(URI.create(racine + "/api/v1/export/xml/" + nntOuNumsujet)).build();
    }

    @Operation(
            summary = "Url pour maintenir la compatibilité avec les urls de theses.fr v1, redirige sur l'API au format d'url standard.",
            description = "Url pour maintenir la compatibilité avec les urls de theses.fr v1, redirige sur l'API au format d'url standard.")
    @ApiResponse(responseCode = "400", description = "Le format du numéro national de thèse fourni est incorrect")
    @ApiResponse(responseCode = "200", description = "Opération terminée avec succès")
    @ApiResponse(responseCode = "500", description = "Service indisponible")
    @GetMapping(value = "{nntOuNumsujet}.rdf")
    public ResponseEntity<Void> redirectRDF(@PathVariable @Parameter(name = "nntOuNumsujet", description = "Numéro National de Thèse ou numéro de sujet", example = "2013MON30092") String nntOuNumsujet){

        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).location(URI.create(racine + "/api/v1/export/rdf/" + nntOuNumsujet)).build();
    }

    @Operation(
            summary = "Url pour maintenir la compatibilité avec les urls de theses.fr v1, redirige sur l'API au format d'url standard.",
            description = "Url pour maintenir la compatibilité avec les urls de theses.fr v1, redirige sur l'API au format d'url standard.")
    @ApiResponse(responseCode = "400", description = "Le format du numéro national de thèse fourni est incorrect")
    @ApiResponse(responseCode = "200", description = "Opération terminée avec succès")
    @ApiResponse(responseCode = "500", description = "Service indisponible")
    @GetMapping(value = "{nntOuNumsujet}.tefudoc")
    public ResponseEntity<Void> redirectTefudoc(@PathVariable @Parameter(name = "nntOuNumsujet", description = "Numéro National de Thèse ou numéro de sujet", example = "2013MON30092") String nntOuNumsujet){

        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).location(URI.create(racine + "/api/v1/export/tefudoc/" + nntOuNumsujet)).build();
    }

    @Operation(
            summary = "Url pour maintenir la compatibilité avec les urls de theses.fr v1, redirige sur l'API au format d'url standard.",
            description = "Url pour maintenir la compatibilité avec les urls de theses.fr v1, redirige sur l'API au format d'url standard.")
    @ApiResponse(responseCode = "400", description = "Le format du numéro national de thèse fourni est incorrect")
    @ApiResponse(responseCode = "200", description = "Opération terminée avec succès")
    @ApiResponse(responseCode = "500", description = "Service indisponible")
    @GetMapping(value = "{nntOuNumsujet}.ris")
    public ResponseEntity<Void> redirectRis(@PathVariable @Parameter(name = "nntOuNumsujet", description = "Numéro National de Thèse ou numéro de sujet", example = "2013MON30092") String nntOuNumsujet){

        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).location(URI.create(racine + "/api/v1/export/ris/" + nntOuNumsujet)).build();
    }

    @Operation(
            summary = "Url pour maintenir la compatibilité avec les urls de theses.fr v1, redirige sur l'API au format d'url standard.",
            description = "Url pour maintenir la compatibilité avec les urls de theses.fr v1, redirige sur l'API au format d'url standard.")
    @ApiResponse(responseCode = "400", description = "Le format du numéro national de thèse fourni est incorrect")
    @ApiResponse(responseCode = "200", description = "Opération terminée avec succès")
    @ApiResponse(responseCode = "500", description = "Service indisponible")
    @GetMapping(value = "{nntOuNumsujet}.bib")
    public ResponseEntity<Void> redirectBib(@PathVariable @Parameter(name = "nntOuNumsujet", description = "Numéro National de Thèse ou numéro de sujet", example = "2013MON30092") String nntOuNumsujet){

        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).location(URI.create(racine + "/api/v1/export/bib/" + nntOuNumsujet)).build();
    }
}
