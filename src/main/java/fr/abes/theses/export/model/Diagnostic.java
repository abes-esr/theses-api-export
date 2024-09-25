package fr.abes.theses.export.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Diagnostic {

    private Integer etat;
    private String type;
    private String codeEtab;
    private String nnt;
    private String ppn;
    private String utilisateur;
    private String rcr;
    private String annee;
    private String gravite;
    private String libelle;
    private String consigne;

}
