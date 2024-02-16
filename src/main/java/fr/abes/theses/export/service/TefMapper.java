package fr.abes.theses.export.service;

import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.OracleResultSet;
import oracle.xdb.XMLType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class TefMapper implements RowMapper<String> {

    @Override
    public String mapRow(ResultSet rs, int rowNum) throws SQLException {

        try {

            OracleResultSet rsOra = (OracleResultSet) rs;
            return XMLType.createXML(rsOra.getOPAQUE("doc")).getStringVal();

        }
        catch (NullPointerException e) {
            log.error("dans TefMapper : " + e.toString());
            return null;
        }
    }
}

