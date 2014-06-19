package test;

import java.util.Map;
import java.util.Set;
import java.util.ArrayList;

import org.openrdf.query.algebra.TupleExpr;
import org.openrdf.query.parser.ParsedQuery;
import org.openrdf.query.parser.ParsedTupleQuery;
import org.openrdf.query.parser.QueryParserUtil;
import org.openrdf.query.QueryLanguage;

import org.junit.Test;
import org.junit.Assert;

public class QueryFieldsTest {


    @Test
    public void simpleStarQuery() throws Exception {
        String queryString = "SELECT * WHERE { ?a a ?b }";
        ParsedTupleQuery pq = QueryParserUtil.parseTupleQuery(QueryLanguage.SPARQL, queryString, null);
        TupleExpr te = pq.getTupleExpr();
        Set<String> vars = te.getBindingNames();
        Assert.assertTrue(vars.contains("a"));
        Assert.assertTrue(vars.contains("b"));
//        for (String var : vars) {
//            System.out.println(var);
//        }
    }

    @Test
    public void queryWithAlias() throws Exception {
        String queryString = "SELECT (COUNT(?b) AS ?count) ?a  WHERE { ?a a ?b } GROUP BY ?a";
        ParsedTupleQuery pq = QueryParserUtil.parseTupleQuery(QueryLanguage.SPARQL, queryString, null);
        TupleExpr te = pq.getTupleExpr();
        Set<String> vars = te.getBindingNames();
        Assert.assertTrue(vars.contains("a"));
        Assert.assertTrue(vars.contains("count"));
    }

}

