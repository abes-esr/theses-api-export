package fr.abes.theses.export.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DbRequest {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public String findTefByNntOrNumsujet (String nntOrNumsujet) {

        String sql = "select d.doc from PORTAIL.document d where d.nnt=? or d.numsujet=?";
        return jdbcTemplate.queryForObject(sql, new TefMapper(), nntOrNumsujet,nntOrNumsujet);
    }
}
