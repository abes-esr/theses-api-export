package fr.abes.theses.export.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

@Slf4j
@Component
public class BibRis {


    @SneakyThrows
    public String renvoyerFichier(String contenu, HttpServletResponse response, String nomFichier) {

        response.addHeader("Content-Disposition", "attachment; filename=" + nomFichier);
        response.setContentType("text/plain;charset=UTF-8");

        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.println(contenu);
        } catch (IOException e) {
            throw e;
        } finally {
            if (out != null) {
                out.close();
            }
        }
        return null;
    }

    public String generateRisData(String tefFinal, String nntOuNumsujet) {
        // ********** Création du format ris **********
        StringBuilder risOutput = new StringBuilder("");

        if (tefFinal != null && !tefFinal.isEmpty()) {
            try (InputStream tef = new ByteArrayInputStream(tefFinal.getBytes("UTF-8"))) {
                Document docTef;
                log.info("Création du format RIS pour nntOuNumsujet : " + nntOuNumsujet);

                SAXBuilder sx = new SAXBuilder();
                docTef = sx.build(tef);
                docTef.getRootElement().addNamespaceDeclaration(
                        Namespace.getNamespace("tefextension", "http://www.abes.fr/abes/documents/tefextension"));

                String dpTef = getValeur(docTef,
                        "//mets:mets/mets:amdSec[1]/mets:techMD[1]/mets:mdWrap[1]/mets:xmlData[1]/tef:thesisAdmin[1]/dc:identifier[1]");
                String tiTef = getValeur(docTef,
                        "//mets:mets/mets:dmdSec[2]/mets:mdWrap[1]/mets:xmlData[1]/tef:thesisRecord[1]/dc:title[1]");

                String auteurNomNaissance = getValeur(docTef,
                        "/mets:mets/mets:amdSec[1]/mets:techMD[1]/mets:mdWrap[1]/mets:xmlData[1]/tef:thesisAdmin[1]/tef:auteur[1]/tef:nomDeNaissance[1]");
                String auteurNom = getValeur(docTef,
                        "/mets:mets/mets:amdSec[1]/mets:techMD[1]/mets:mdWrap[1]/mets:xmlData[1]/tef:thesisAdmin[1]/tef:auteur[1]/tef:nom[1]");
                String auTef = "";

                if (!auteurNomNaissance.isEmpty() && !auteurNomNaissance.equalsIgnoreCase(auteurNom)) {
                    auTef = auteurNom + " (" + auteurNomNaissance + ")" + ", " + getValeur(docTef,
                            "/mets:mets/mets:amdSec[1]/mets:techMD[1]/mets:mdWrap[1]/mets:xmlData[1]/tef:thesisAdmin[1]/tef:auteur[1]/tef:prenom[1]");
                } else {
                    auTef = auteurNom + ", " + getValeur(docTef,
                            "/mets:mets/mets:amdSec[1]/mets:techMD[1]/mets:mdWrap[1]/mets:xmlData[1]/tef:thesisAdmin[1]/tef:auteur[1]/tef:prenom[1]");

                }
                auTef += "\n";

                StringBuilder auBisTef = new StringBuilder("");
                List<Element> coAuteur = (List<Element>) XPath.selectNodes(docTef, "//tefextension:coAuteur");
                for (int i = 0; i < coAuteur.size(); i++) {
                    auBisTef.append("AU  - ");
                    String coAuteurNom = getValeur(docTef, "//tefextension:coAuteur[" + (i + 1) + "]/tef:nom");
                    String coAuteurNomNaissance = getValeur(docTef,
                            "//tefextension:coAuteur[" + (i + 1) + "]/tef:nomDeNaissance");
                    String coAuteurPrenom = getValeur(docTef, "//tefextension:coAuteur[" + (i + 1) + "]/tef:prenom");
                    auBisTef.append(coAuteurNom);
                    if (!coAuteurNomNaissance.isEmpty() && !coAuteurNomNaissance.equalsIgnoreCase(coAuteurNom)) {
                        auBisTef.append(" (" + coAuteurNomNaissance + ")");
                    }
                    auBisTef.append(", ");
                    auBisTef.append(coAuteurPrenom);
                    auBisTef.append("\n");
                }
                auTef += auBisTef.toString();

                List<Element> directeurThese = (List<Element>) XPath.selectNodes(docTef,
                        "/mets:mets/mets:amdSec[1]/mets:techMD[1]/mets:mdWrap[1]/mets:xmlData[1]/tef:thesisAdmin[1]/tef:directeurThese");
                StringBuilder listedirecteur = new StringBuilder();

                for (int i = 0; i < directeurThese.size(); i++) {
                    String directeurTheseNom = getValeur(docTef,
                            "/mets:mets/mets:amdSec[1]/mets:techMD[1]/mets:mdWrap[1]/mets:xmlData[1]/tef:thesisAdmin[1]/tef:directeurThese["
                                    + (i + 1) + "]/tef:nom");
                    String directeurThesePrenom = getValeur(docTef,
                            "/mets:mets/mets:amdSec[1]/mets:techMD[1]/mets:mdWrap[1]/mets:xmlData[1]/tef:thesisAdmin[1]/tef:directeurThese["
                                    + (i + 1) + "]/tef:prenom");
                    listedirecteur.append(directeurTheseNom);
                    listedirecteur.append(", ");
                    listedirecteur.append(directeurThesePrenom);

                    if (i == directeurThese.size() - 2) {
                        listedirecteur.append("; ");
                    }
                }

                String a3Tef = listedirecteur.toString();

                String pyTef = getValeur(docTef,
                        "//mets:mets/mets:amdSec[1]/mets:techMD[1]/mets:mdWrap[1]/mets:xmlData[1]/tef:thesisAdmin[1]/dcterms:dateAccepted[1]");
                String spTef = getValeur(docTef,
                        "//mets:dmdSec[contains(@ID,'EDITION_DEPOT_NATIONAL')]//tef:edition/tefudoc:collation[1]");
                String n1Tef = "Thèse de doctorat "
                        + getValeur(docTef,
                        "//mets:mets/mets:amdSec[1]/mets:techMD[1]/mets:mdWrap[1]/mets:xmlData[1]/tef:thesisAdmin[1]/tef:thesis.degree[1]/tef:thesis.degree.discipline[1]")
                        + " "
                        + getValeur(docTef,
                        "//mets:mets/mets:amdSec[1]/mets:techMD[1]/mets:mdWrap[1]/mets:xmlData[1]/tef:thesisAdmin[1]/tef:thesis.degree[1]/tef:thesis.degree.grantor[1]/tef:nom[1]")
                        + " "
                        + getValeur(docTef,
                        "//mets:mets/mets:amdSec[1]/mets:techMD[1]/mets:mdWrap[1]/mets:xmlData[1]/tef:thesisAdmin[1]/dcterms:dateAccepted[1]")
                        .substring(0, 4);

                String n1TefCotutelle = "Thèse de doctorat "
                        + getValeur(docTef,
                        "//mets:mets/mets:amdSec[1]/mets:techMD[1]/mets:mdWrap[1]/mets:xmlData[1]/tef:thesisAdmin[1]/tef:thesis.degree[1]/tef:thesis.degree.discipline[1]")
                        + " "
                        + getValeur(docTef,
                        "/mets:mets/mets:amdSec[1]/mets:techMD[1]/mets:mdWrap[1]/mets:xmlData[1]/tef:thesisAdmin[1]/tef:thesis.degree[1]/tef:thesis.degree.grantor[2]/tef:nom[1]")
                        + " "
                        + getValeur(docTef,
                        "//mets:mets/mets:amdSec[1]/mets:techMD[1]/mets:mdWrap[1]/mets:xmlData[1]/tef:thesisAdmin[1]/dcterms:dateAccepted[1]")
                        .substring(0, 4);

                if (getValeur(docTef,
                        "/mets:mets/mets:amdSec[1]/mets:techMD[1]/mets:mdWrap[1]/mets:xmlData[1]/tef:thesisAdmin[1]/tef:thesis.degree[1]/tef:thesis.degree.grantor[2]/tef:nom[1]")
                        .isEmpty()) {
                    n1TefCotutelle = "";
                }

                String casGestionTef = getAttribut(docTef, "//star_gestion/traitements", "scenario");
                if (casGestionTef.isEmpty()) {
                    casGestionTef = "cas1";
                }

                String urTef = getValeur(docTef,
                        "//mets:mets/mets:amdSec[1]/mets:techMD[1]/mets:mdWrap[1]/mets:xmlData[1]/tef:thesisAdmin[1]/dc:identifier[1]");

                risOutput.append("TY  - THES");
                risOutput.append("\n");

                risOutput.append("DP  - " + dpTef);
                risOutput.append("\n");

                risOutput.append("TI  - " + tiTef);
                risOutput.append("\n");

                risOutput.append("AU  - " + auTef);

                risOutput.append("A3  - " + a3Tef);
                risOutput.append("\n");

                risOutput.append("PY  - " + pyTef.substring(0, 4));
                risOutput.append("\n");

                if (!spTef.isEmpty()) {
                    risOutput.append("SP  - " + spTef);
                    risOutput.append("\n");
                }

                risOutput.append("N1  - " + n1Tef);
                risOutput.append("\n");

                if (!n1TefCotutelle.isEmpty()) {
                    risOutput.append("N1  - " + n1TefCotutelle);
                    risOutput.append("\n");
                }

                risOutput.append("N1  - " + getValeur(docTef,
                        "//mets:mets/mets:amdSec[1]/mets:techMD[1]/mets:mdWrap[1]/mets:xmlData[1]/tef:thesisAdmin[1]/dc:identifier[2]"));
                risOutput.append("\n");
                if (casGestionTef.equalsIgnoreCase("cas1") || casGestionTef.equalsIgnoreCase("cas2")) {
                    risOutput.append("UR  - " + urTef + "/document");
                    risOutput.append("\n");
                }
                risOutput.append("ER  - ");
                risOutput.append("\n\r");
            } catch (Exception e) {
                risOutput.append("\n\rErreur lors de la generation\n\r");
                log.error("generateRisData : " + e.getMessage());
                log.error(e.toString());
            }
        } else {
            risOutput.append("Pas de TEF en base. \n\r");
        }
        return risOutput.toString();
    }

    public String generateBibData(String tefFinal, String nntOuNumsujet) {
        // ********** Création du format bib **********
        StringBuilder bibOutput = new StringBuilder("");

        if (tefFinal != null && !tefFinal.isEmpty()) {
            try (InputStream tef = new ByteArrayInputStream(tefFinal.getBytes("UTF-8"))) {
                Document docTef;
                log.info("Création du format BibTeX pour nntOuNumsujet : " + nntOuNumsujet);

                SAXBuilder sx = new SAXBuilder();
                docTef = sx.build(tef);
                docTef.getRootElement().addNamespaceDeclaration(
                        Namespace.getNamespace("tefextension", "http://www.abes.fr/abes/documents/tefextension"));

                String urlTef = getValeur(docTef,
                        "//mets:mets/mets:amdSec[1]/mets:techMD[1]/mets:mdWrap[1]/mets:xmlData[1]/tef:thesisAdmin[1]/dc:identifier[1]");
                String titleTef = getValeur(docTef,
                        "//mets:mets/mets:dmdSec[2]/mets:mdWrap[1]/mets:xmlData[1]/tef:thesisRecord[1]/dc:title[1]");
                String auteurNom = getValeur(docTef,
                        "/mets:mets/mets:amdSec[1]/mets:techMD[1]/mets:mdWrap[1]/mets:xmlData[1]/tef:thesisAdmin[1]/tef:auteur[1]/tef:nom[1]");
                String auteurNomNaissance = getValeur(docTef,
                        "/mets:mets/mets:amdSec[1]/mets:techMD[1]/mets:mdWrap[1]/mets:xmlData[1]/tef:thesisAdmin[1]/tef:auteur[1]/tef:nomDeNaissance[1]");
                String authorTef = "";

                if (!auteurNomNaissance.isEmpty() && !auteurNomNaissance.equalsIgnoreCase(auteurNom)) {
                    authorTef = auteurNom + " (" + auteurNomNaissance + ")" + ", " + getValeur(docTef,
                            "/mets:mets/mets:amdSec[1]/mets:techMD[1]/mets:mdWrap[1]/mets:xmlData[1]/tef:thesisAdmin[1]/tef:auteur[1]/tef:prenom[1]");
                } else {
                    authorTef = auteurNom + ", " + getValeur(docTef,
                            "/mets:mets/mets:amdSec[1]/mets:techMD[1]/mets:mdWrap[1]/mets:xmlData[1]/tef:thesisAdmin[1]/tef:auteur[1]/tef:prenom[1]");
                }

                StringBuilder coAuthorTef = new StringBuilder("");
                List<Element> coAuteur = (List<Element>) XPath.selectNodes(docTef, "//tefextension:coAuteur");
                for (int i = 0; i < coAuteur.size(); i++) {
                    coAuthorTef.append(" and ");
                    String coAuteurNom = getValeur(docTef, "//tefextension:coAuteur[" + (i + 1) + "]/tef:nom");
                    String coAuteurNomNaissance = getValeur(docTef,
                            "//tefextension:coAuteur[" + (i + 1) + "]/tef:nomDeNaissance");
                    String coAuteurPrenom = getValeur(docTef, "//tefextension:coAuteur[" + (i + 1) + "]/tef:prenom");
                    coAuthorTef.append(coAuteurNom);
                    if (!coAuteurNomNaissance.isEmpty() && !coAuteurNomNaissance.equalsIgnoreCase(coAuteurNom)) {
                        coAuthorTef.append(" (" + coAuteurNomNaissance + ")");
                    }
                    coAuthorTef.append(", ");
                    coAuthorTef.append(coAuteurPrenom);
                }
                authorTef += coAuthorTef.toString();

                String yearTef = getValeur(docTef,
                        "//mets:mets/mets:amdSec[1]/mets:techMD[1]/mets:mdWrap[1]/mets:xmlData[1]/tef:thesisAdmin[1]/dcterms:dateAccepted[1]")
                        .substring(0, 4);
                String pagesTef = getValeur(docTef,
                        "//mets:dmdSec[contains(@ID,'EDITION_DEPOT_NATIONAL')]//tef:edition/tefudoc:collation[1]");

                String authorName = getValeur(docTef,
                        "/mets:mets/mets:amdSec[1]/mets:techMD[1]/mets:mdWrap[1]/mets:xmlData[1]/tef:thesisAdmin[1]/tef:auteur[1]/tef:nom[1]");
                authorName = authorName.replaceAll("'", "");
                authorName = authorName.replaceAll(" ", "");
                authorName = authorName.replaceAll("-", "");
                if (authorName.length() > 4) {
                    authorName = authorName.substring(0, 5).toLowerCase();
                }

                String keyBib = authorName + yearTef;

                List<Element> directeurThese = (List<Element>) XPath.selectNodes(docTef,
                        "/mets:mets/mets:amdSec[1]/mets:techMD[1]/mets:mdWrap[1]/mets:xmlData[1]/tef:thesisAdmin[1]/tef:directeurThese");
                StringBuilder listedirecteur = new StringBuilder();

                for (int i = 0; i < directeurThese.size(); i++) {
                    String directeurTheseNom = getValeur(docTef,
                            "/mets:mets/mets:amdSec[1]/mets:techMD[1]/mets:mdWrap[1]/mets:xmlData[1]/tef:thesisAdmin[1]/tef:directeurThese["
                                    + (i + 1) + "]/tef:nom");
                    String directeurThesePrenom = getValeur(docTef,
                            "/mets:mets/mets:amdSec[1]/mets:techMD[1]/mets:mdWrap[1]/mets:xmlData[1]/tef:thesisAdmin[1]/tef:directeurThese["
                                    + (i + 1) + "]/tef:prenom");
                    listedirecteur.append(directeurTheseNom);
                    listedirecteur.append(", ");
                    listedirecteur.append(directeurThesePrenom);

                    if (i == directeurThese.size() - 2) {
                        listedirecteur.append(" et ");
                    }
                }

                String listedirecteurTef = listedirecteur.toString();

                String noteTef = "Thèse de doctorat dirigée par " + listedirecteurTef + " "
                        + getValeur(docTef,
                        "//mets:mets/mets:amdSec[1]/mets:techMD[1]/mets:mdWrap[1]/mets:xmlData[1]/tef:thesisAdmin[1]/tef:thesis.degree[1]/tef:thesis.degree.discipline[1]")
                        + " "
                        + getValeur(docTef,
                        "//mets:mets/mets:amdSec[1]/mets:techMD[1]/mets:mdWrap[1]/mets:xmlData[1]/tef:thesisAdmin[1]/tef:thesis.degree[1]/tef:thesis.degree.grantor[1]/tef:nom[1]")
                        + " "
                        + getValeur(docTef,
                        "//mets:mets/mets:amdSec[1]/mets:techMD[1]/mets:mdWrap[1]/mets:xmlData[1]/tef:thesisAdmin[1]/dcterms:dateAccepted[1]")
                        .substring(0, 4);

                String noteTefCotutelle = "Thèse de doctorat "
                        + getValeur(docTef,
                        "//mets:mets/mets:amdSec[1]/mets:techMD[1]/mets:mdWrap[1]/mets:xmlData[1]/tef:thesisAdmin[1]/tef:thesis.degree[1]/tef:thesis.degree.discipline[1]")
                        + " "
                        + getValeur(docTef,
                        "/mets:mets/mets:amdSec[1]/mets:techMD[1]/mets:mdWrap[1]/mets:xmlData[1]/tef:thesisAdmin[1]/tef:thesis.degree[1]/tef:thesis.degree.grantor[2]/tef:nom[1]")
                        + " "
                        + getValeur(docTef,
                        "//mets:mets/mets:amdSec[1]/mets:techMD[1]/mets:mdWrap[1]/mets:xmlData[1]/tef:thesisAdmin[1]/dcterms:dateAccepted[1]")
                        .substring(0, 4);

                if (getValeur(docTef,
                        "/mets:mets/mets:amdSec[1]/mets:techMD[1]/mets:mdWrap[1]/mets:xmlData[1]/tef:thesisAdmin[1]/tef:thesis.degree[1]/tef:thesis.degree.grantor[2]/tef:nom[1]")
                        .isEmpty()) {
                    noteTefCotutelle = "";
                }

                String casGestionTef = getAttribut(docTef, "//star_gestion/traitements", "scenario");
                if (casGestionTef.isEmpty()) {
                    casGestionTef = "cas1";
                }

                getValeur(docTef,
                        "//mets:mets/mets:amdSec[1]/mets:techMD[1]/mets:mdWrap[1]/mets:xmlData[1]/tef:thesisAdmin[1]/dc:identifier[1]");

                bibOutput.append("@PHDTHESIS{" + keyBib + ",");
                bibOutput.append("\n");

                bibOutput.append("url = \"" + urlTef + "\",");
                bibOutput.append("\n");

                bibOutput.append("title = \"" + titleTef + "\",");
                bibOutput.append("\n");

                bibOutput.append("author = \"" + authorTef + "\",");
                bibOutput.append("\n");

                bibOutput.append("year = \"" + yearTef + "\",");
                bibOutput.append("\n");

                if (!pagesTef.isEmpty()) {
                    bibOutput.append("pages = \"" + pagesTef + "\",");
                    bibOutput.append("\n");
                }

                bibOutput.append("note = \"" + noteTef + "\",");
                bibOutput.append("\n");

                if (!noteTefCotutelle.isEmpty()) {
                    bibOutput.append("note = \"" + noteTefCotutelle + "\"");
                    bibOutput.append(",");
                    bibOutput.append("\n");
                }

                bibOutput.append("note = \"" + nntOuNumsujet + "\",");
                bibOutput.append("\n");

                if (casGestionTef.equalsIgnoreCase("cas1") || casGestionTef.equalsIgnoreCase("cas2")) {
                    bibOutput.append("url = \"" + urlTef + "/document\",");
                    bibOutput.append("\n");
                }

                bibOutput.append("}");
                bibOutput.append("\n");
                bibOutput.append("\n");
            } catch (Exception e) {
                bibOutput.append("\n\rErreur lors de la generation\n\r");
                log.error("generateBibData : " + e.getMessage());
                log.error(e.toString());
            }
        } else {
            bibOutput.append("Pas de TEF en base. \n\r");
        }
        return bibOutput.toString();
    }

    private String getValeur(Document docTef, String xpath) {
        String retour = "";
        try {
            Element elem = (Element) XPath.selectSingleNode(docTef, xpath);
            if (elem != null) {
                retour = elem.getText();
            }
        } catch (Exception e) {
            log.error("Erreur dans getValeur de Export.java: " + e.toString());
            log.error(e.toString());
        }
        return retour;
    }

    private String getAttribut(Document docTef, String xpath, String attribut) {
        String retour = "";
        try {
            Element elem = (Element) XPath.selectSingleNode(docTef, xpath);
            if (elem != null) {
                retour = elem.getAttributeValue(attribut);
            }
        } catch (Exception e) {
            log.error("Erreur dans getAttribut de Export.java: " + e.toString());
            log.error(e.toString());
        }
        return retour;
    }

}
