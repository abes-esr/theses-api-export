package fr.abes.theses.export.service;

import fr.abes.theses.export.model.Diagnostic;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Configuration
public class DbRequest {

    private final JdbcTemplate jdbcTemplateTheses;
    private final JdbcTemplate jdbcTemplateBaseXml;
    private final TraitementParams traitementParams;
    public DbRequest(JdbcTemplate jdbcTemplateTheses,
                     JdbcTemplate jdbcTemplateBaseXml,
                     TraitementParams traitementParams) {
        this.jdbcTemplateTheses = jdbcTemplateTheses;
        this.jdbcTemplateBaseXml = jdbcTemplateBaseXml;
        this.traitementParams = traitementParams;
    }

    public String findTefByNntOrNumsujet (String nntOrNumsujet) {


        String sql = "select d.doc from PORTAIL.document d where d.nnt=? or d.numsujet=?";
        return jdbcTemplateTheses.queryForObject(sql, new TefMapper(), nntOrNumsujet,nntOrNumsujet);
    }

    public List<Diagnostic> getDiagnosticAlgoTheses (Optional<List<Integer>> etats,
                                               Optional<List<String>> types,
                                               Optional<List<String>> codeEtab,
                                               Optional<List<String>> nnt,
                                               Optional<List<String>> ppn,
                                               Optional<List<String>> utilisateur,
                                               Optional<List<String>> rcr,
                                               Optional<List<String>> sort,
                                               Optional<String> annee,
                                               Optional<String> rownum) {


        StringBuilder sql = new StringBuilder("SELECT etat, type, codeetab, nnt, ppn, utilisateur, rcr, annee, gravite, libelle, consigne " +
                "FROM theses_algo WHERE 1=1");

        List<Object> params = new ArrayList<>();

        ajouteParamIntegerDansRequete(sql, params, "etat",etats);
        ajouteParamStringDansRequete(sql, params, "type", types);
        ajouteParamStringDansRequete(sql, params, "codeetab", codeEtab);
        ajouteParamStringDansRequete(sql, params, "nnt", nnt);
        ajouteParamStringDansRequete(sql, params, "ppn", ppn);
        ajouteParamStringDansRequete(sql, params, "utilisateur", utilisateur);
        ajouteParamStringDansRequete(sql, params, "rcr", rcr);
        ajouteRownum(sql, params, rownum);
        ajouteAnneeDansRequete(sql, params, annee);
        ajouteOrdre(sql, sort);

        List<Diagnostic> liste = jdbcTemplateBaseXml.query(sql.toString(), params.toArray(), (rs, rowNum) ->
                new Diagnostic(
                        rs.getInt("etat"),
                        rs.getString("type"),
                        rs.getString("codeetab"),
                        rs.getString("nnt"),
                        rs.getString("ppn"),
                        rs.getString("utilisateur"),
                        rs.getString("rcr"),
                        rs.getString("annee"),
                        rs.getString("gravite"),
                        rs.getString("libelle"),
                        rs.getString("consigne")
                )
        );

        return liste;
    }

    private void ajouteParamIntegerDansRequete (StringBuilder sql, List<Object> paramsRequete, String nomParam, Optional<List<Integer>> params) {

        if (params.isPresent()) {

            String inClause = params.get().stream()
                    .map(type -> nomParam + " = ?")
                    .collect(Collectors.joining(" OR "));
            sql.append(" AND (" + inClause + ")");
            paramsRequete.addAll(params.get());
        }
    }
    private void ajouteParamStringDansRequete (StringBuilder sql, List<Object> paramsRequete, String nomParam, Optional<List<String>> params) {

        if (params.isPresent()) {

            String clause = params.get().stream()
                    .map(type -> nomParam + " = ?")
                    .collect(Collectors.joining(" OR "));
            sql.append(" AND (" + clause + ")");
            paramsRequete.addAll(params.get());
        }
    }

    private void ajouteAnneeDansRequete (StringBuilder sql, List<Object> paramsRequete, Optional<String> annee) {

        if (annee.isPresent() && annee.get().matches("\\d{4}")) {

            sql.append(" AND annee >= ?");
            paramsRequete.add(annee.get());
        }
    }
    private void ajouteRownum (StringBuilder sql, List<Object> paramsRequete, Optional<String> rownum) {

        if (rownum.isPresent() && Integer.parseInt(rownum.get()) > 0 && Integer.parseInt(rownum.get()) < 100000) {
                sql.append(" AND rownum <= ?");
                paramsRequete.add(rownum.get());
            }
        else {
            sql.append(" AND rownum <= 100");
        }
    }

    private void ajouteOrdre (StringBuilder sql, Optional<List<String>> sort) {

        List<String> validColumns = Arrays.asList("etat", "type", "codeetab", "nnt", "ppn", "utilisateur", "rcr", "annee", "gravite", "libelle", "consigne");  // Ajouter toutes les colonnes valides

        if (sort.isPresent() && !sort.get().isEmpty()) {

            String orderClause = sort.get().stream()
                    .map(value -> {
                        String column = value.trim();

                        if (!validColumns.contains(column)) {
                            throw new IllegalArgumentException("Colonne non valide pour le tri : " + column);
                        }

                        return column + " ASC";
                    })
                    .collect(Collectors.joining(", "));

            sql.append(" ORDER BY " + orderClause);
        }
    }
}
