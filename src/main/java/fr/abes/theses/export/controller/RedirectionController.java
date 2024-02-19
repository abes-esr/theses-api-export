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

    @GetMapping(value = "{nnt}.xml")
    public ResponseEntity<Void> redirectXML(@PathVariable String nnt){

        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).location(URI.create(racine + "/api/v1/theses/export/xml/" + nnt)).build();
    }

    @GetMapping(value = "{nnt}.rdf")
    public ResponseEntity<Void> redirectRDF(@PathVariable String nnt){

        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).location(URI.create(racine + "/api/v1/theses/export/rdf/" + nnt)).build();
    }

    @GetMapping(value = "{nnt}.tefudoc")
    public ResponseEntity<Void> redirectTefudoc(@PathVariable String nnt){

        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).location(URI.create(racine + "/api/v1/theses/export/tefudoc/" + nnt)).build();
    }
}
