package fr.abes.theses.export.service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;

@Component
public class XslTransfo {

    @Value("${path.xsls}")
    String pathXsls;

    /**
     * tranforme le TEF passé en paramètre avec la feuille de style passée en paramètre
     * @param tef fichier tef sous forme de chaine de caractères
     * @param xslNomFichier nom de la feuille de style
     * @return
     *
     * on utilise le parser Saxon sinon erreur sur la fonction date du XSL
     * on utilise @SneakyThrows car les exceptions sont gérées dans le ExceptionControllerHandler
     */
    @SneakyThrows
    public String transformXsl (String tef, String xslNomFichier)  {

        FileInputStream xsl = new FileInputStream(pathXsls + xslNomFichier);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        TransformerFactory transformerFactory = new net.sf.saxon.TransformerFactoryImpl();
        Transformer transformer = transformerFactory.newTransformer(new javax.xml.transform.stream.StreamSource(xsl));

        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        InputStream targetStream = new ByteArrayInputStream(tef.getBytes());
        transformer.transform(new javax.xml.transform.stream.StreamSource(targetStream),
                new javax.xml.transform.stream.StreamResult(out));
        return out.toString();

    }
}
