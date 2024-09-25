package fr.abes.theses.export.service;

import org.springframework.stereotype.Component;

@Component
public class TraitementParams {

    /**
     * Pour pouvoir gérer une liste potentielle de params, les params sont placés
     *     entre parenthèses pour pouvoir utilise IN
     * @param valeur
     * @return
     */
    public String getParamInteger(String valeur) {
        String val = "";
        if (valeur != null) {
            val = valeur;
            val = "'" + val.replaceAll(",", "','");
            if (val.lastIndexOf(',') == val.length()) {
                val = val.substring(0, val.length() - 1);
            }
            val += "'";
            val = "(" + val + ")";
        }
        return val;
    }

    /**
     * Pour pouvoir gérer une liste potentielle de params, les params sont placés
     *     entre parenthèses pour pouvoir utilise IN + ajout de guillemets simple car le(s) param(s)
     *     est (sont) de type String
     * @param valeur
     * @return
     */
    public String getParamString(String valeur) {
        String val = "";
        if (valeur != null) {
            val = valeur;
            if (val.lastIndexOf(',') == val.length()) {
                val = val.substring(0, val.length() - 1);
            }
            val = "(" + val + ")";
        }
        return val;
    }

    public String getSort(String valeur) {
        String sort = "";
        if(valeur!=null)
        {
            sort = valeur;
            if (sort.lastIndexOf(',') == sort.length()) {
                sort = sort.substring(0, sort.length() - 1);
            }
        }
        return sort;
    }

    public String getAnnee (String valeur) {

        String annee = "";
        if (valeur != null) {
            annee = valeur;
            if (annee.length() == 4 && Integer.parseInt(annee) > 0) {
                annee = "'" + annee + "'";
            } else {
                annee = "";
            }
        }
    return annee;
    }

    public String getRownum (String valeur) {
        String rownum = "1000000";
        if (valeur != null) {
            if (Integer.parseInt(valeur) > 0) {
                rownum = valeur;
            }
        }
        return rownum;
    }


}
