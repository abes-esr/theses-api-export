package fr.abes.theses.export.controller;

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
@RequestMapping("/")
public class RedirectionController {

    @Value("${racine}")
    String racine;

    @GetMapping(value = "{nntOuNumsujet}.xml")
    public ResponseEntity<Void> redirectXML(@PathVariable String nntOuNumsujet){

        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).location(URI.create(racine + "/api/v1/export/xml/" + nntOuNumsujet)).build();
    }

    @GetMapping(value = "{nntOuNumsujet}.rdf")
    public ResponseEntity<Void> redirectRDF(@PathVariable String nntOuNumsujet){

        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).location(URI.create(racine + "/api/v1/export/rdf/" + nntOuNumsujet)).build();
    }

    @GetMapping(value = "{nntOuNumsujet}.tefudoc")
    public ResponseEntity<Void> redirectTefudoc(@PathVariable String nntOuNumsujet){

        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).location(URI.create(racine + "/api/v1/export/tefudoc/" + nntOuNumsujet)).build();
    }

    @GetMapping(value = "{nntOuNumsujet}.ris")
    public ResponseEntity<Void> redirectRis(@PathVariable String nntOuNumsujet){

        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).location(URI.create(racine + "/api/v1/export/ris/" + nntOuNumsujet)).build();
    }

    @GetMapping(value = "{nntOuNumsujet}.bib")
    public ResponseEntity<Void> redirectBib(@PathVariable String nntOuNumsujet){

        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).location(URI.create(racine + "/api/v1/export/bib/" + nntOuNumsujet)).build();
    }
}
