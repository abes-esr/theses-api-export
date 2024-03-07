package fr.abes.theses.export.controller;


import fr.abes.theses.export.service.BibRis;
import fr.abes.theses.export.service.DbRequest;
import fr.abes.theses.export.service.XslTransfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping("/api/v1/export/")
public class ExportController {

    @Autowired
    DbRequest dbRequest;

    @Autowired
    XslTransfo xslTransfo;

    @Autowired
    BibRis bibRis;

    @Operation(
            summary = "Retourne les métadonnées de la thèse sous format RDF.",
            description = "Retourne les métadonnées de la thèse sous format RDF.")
    @ApiResponse(responseCode = "400", description = "Le format du numéro national de thèse fourni est incorrect")
    @ApiResponse(responseCode = "200", description = "Opération terminée avec succès")
    @ApiResponse(responseCode = "500", description = "Service indisponible")
    @GetMapping(value = "rdf/{nntOuNumsujet}", produces = "application/xml")
    public ResponseEntity exportRDF(@PathVariable @Parameter(name = "nntOuNumsujet", description = "Numéro National de Thèse ou numéro de sujet", example = "2013MON30092") String nntOuNumsujet) {
        return new ResponseEntity<>(xslTransfo.transformXsl(dbRequest.findTefByNntOrNumsujet(nntOuNumsujet), "tef2rdf.xsl"), HttpStatus.OK);
    }

    @Operation(
            summary = "Retourne les métadonnées de la thèse sous format RDF.",
            description = "Retourne les métadonnées de la thèse sous format RDF.")
    @ApiResponse(responseCode = "400", description = "Le format du numéro national de thèse fourni est incorrect")
    @ApiResponse(responseCode = "200", description = "Opération terminée avec succès")
    @ApiResponse(responseCode = "500", description = "Service indisponible")
    @GetMapping(value = "xml/{nntOuNumsujet}", produces = "application/xml")
    public ResponseEntity exportXML(@PathVariable @Parameter(name = "nntOuNumsujet", description = "Numéro National de Thèse ou numéro de sujet", example = "2013MON30092") String nntOuNumsujet) {
        return new ResponseEntity<>(xslTransfo.transformXsl(dbRequest.findTefByNntOrNumsujet(nntOuNumsujet), "tef2rdf.xsl"), HttpStatus.OK);
    }

    @Operation(
            summary = "Retourne les métadonnées de la thèse sous format tefudoc.",
            description = "Retourne les métadonnées de la thèse sous format tefudoc.")
    @ApiResponse(responseCode = "400", description = "Le format du numéro national de thèse fourni est incorrect")
    @ApiResponse(responseCode = "200", description = "Opération terminée avec succès")
    @ApiResponse(responseCode = "500", description = "Service indisponible")
    @GetMapping(value = "tefudoc/{nntOuNumsujet}", produces = "application/xml")
    public ResponseEntity exportTefudoc(@PathVariable @Parameter(name = "nntOuNumsujet", description = "Numéro National de Thèse ou numéro de sujet", example = "2013MON30092") String nntOuNumsujet) {
        return new ResponseEntity<>(xslTransfo.transformXsl(dbRequest.findTefByNntOrNumsujet(nntOuNumsujet), "tef2tefSansGestion.xsl"), HttpStatus.OK);
    }

    @Operation(
            summary = "Retourne les métadonnées de la thèse dans un fichier bibtex.",
            description = "Retourne les métadonnées de la thèse dans un fichier bibtex.")
    @ApiResponse(responseCode = "400", description = "Le format du numéro national de thèse fourni est incorrect")
    @ApiResponse(responseCode = "200", description = "Opération terminée avec succès")
    @ApiResponse(responseCode = "500", description = "Service indisponible")
    @GetMapping(value = "bib/{nntOuNumsujet}")
    public ResponseEntity exportBib(@PathVariable @Parameter(name = "nntOuNumsujet", description = "Numéro National de Thèse ou numéro de sujet", example = "2013MON30092") String nntOuNumsujet, HttpServletResponse response) {
        return new ResponseEntity<>(bibRis.renvoyerFichier(bibRis.generateBibData(dbRequest.findTefByNntOrNumsujet(nntOuNumsujet), nntOuNumsujet), response, "resultat.bib"), HttpStatus.OK);
    }

    @Operation(
            summary = "Retourne les métadonnées de la thèse dans un fichier ris.",
            description = "Retourne les métadonnées de la thèse dans un fichier ris.")
    @ApiResponse(responseCode = "400", description = "Le format du numéro national de thèse fourni est incorrect")
    @ApiResponse(responseCode = "200", description = "Opération terminée avec succès")
    @ApiResponse(responseCode = "500", description = "Service indisponible")
    @GetMapping(value = "ris/{nntOuNumsujet}")
    public ResponseEntity exportRis(@PathVariable @Parameter(name = "nntOuNumsujet", description = "Numéro National de Thèse ou numéro de sujet", example = "2013MON30092") String nntOuNumsujet, HttpServletResponse response) {
        return new ResponseEntity<>(bibRis.renvoyerFichier(bibRis.generateRisData(dbRequest.findTefByNntOrNumsujet(nntOuNumsujet), nntOuNumsujet), response, "resultat.ris"), HttpStatus.OK);
    }

}
