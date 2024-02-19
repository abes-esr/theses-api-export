package fr.abes.theses.export.controller;


import fr.abes.theses.export.service.DbRequest;
import fr.abes.theses.export.service.XslTransfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/theses/")
public class ExportController {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    DbRequest dbRequest;

    @Autowired
    XslTransfo xslTransfo;

    @Operation(
            summary = "Retourne les métadonnées de la thèse sous format RDF.",
            description = "Retourne les métadonnées de la thèse sous format RDF.")
    @ApiResponse(responseCode = "400", description = "Le format du numéro national de thèse fourni est incorrect")
    @ApiResponse(responseCode = "200", description = "Opération terminée avec succès")
    @ApiResponse(responseCode = "500", description = "DbRequest indisponible")
    @GetMapping(value = "export/rdf/{nnt}", produces = "application/xml")
    public ResponseEntity exportRDF(@PathVariable @Parameter(name = "nnt", description = "Numéro National de Thèse", example = "2013MON30092") String nnt) {
        return new ResponseEntity<>(xslTransfo.transformXsl(dbRequest.findTefByNntOrNumsujet(nnt), "tef2rdf.xsl"), HttpStatus.OK);
    }

    @Operation(
            summary = "Retourne les métadonnées de la thèse sous format RDF.",
            description = "Retourne les métadonnées de la thèse sous format RDF.")
    @ApiResponse(responseCode = "400", description = "Le format du numéro national de thèse fourni est incorrect")
    @ApiResponse(responseCode = "200", description = "Opération terminée avec succès")
    @ApiResponse(responseCode = "500", description = "DbRequest indisponible")
    @GetMapping(value = "export/xml/{nnt}", produces = "application/xml")
    public ResponseEntity exportXML(@PathVariable @Parameter(name = "nnt", description = "Numéro National de Thèse", example = "2013MON30092") String nnt) {
        return new ResponseEntity<>(xslTransfo.transformXsl(dbRequest.findTefByNntOrNumsujet(nnt), "tef2rdf.xsl"), HttpStatus.OK);
    }

    @Operation(
            summary = "Retourne les métadonnées de la thèse sous format tefudoc.",
            description = "Retourne les métadonnées de la thèse sous format tefudoc.")
    @ApiResponse(responseCode = "400", description = "Le format du numéro national de thèse fourni est incorrect")
    @ApiResponse(responseCode = "200", description = "Opération terminée avec succès")
    @ApiResponse(responseCode = "500", description = "DbRequest indisponible")
    @GetMapping(value = "export/tefudoc/{nnt}", produces = "application/xml")
    public ResponseEntity exportTefudoc(@PathVariable @Parameter(name = "nnt", description = "Numéro National de Thèse", example = "2013MON30092") String nnt) {
        return new ResponseEntity<>(xslTransfo.transformXsl(dbRequest.findTefByNntOrNumsujet(nnt), "tef2tefSansGestion.xsl"), HttpStatus.OK);
    }
}
