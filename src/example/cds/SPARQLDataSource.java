package example.cds;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

import org.openrdf.model.Value;
import org.openrdf.query.BindingSet;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.QueryResult;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.sparql.SPARQLRepository;


/**
 * SPARQL data source implementation for JasperReports Server.
 *
 * @author Søren Roug
 */
public class SPARQLDataSource implements JRDataSource {

    private String endpointUrl;

    private static SPARQLRepository endpointObj;

    private RepositoryConnection conn;

    /** Table containing the result from the query. */
    private TupleQueryResult result;

    /** Current row in result set. */
    private BindingSet currentBinding;

    private String sparqlStatement;

    /**
     * Constructor. Only sets up the configuration. Connecting to the endpoint is done in init().
     *
     * @param endpointUrl
     * @param sparqlStatement
     */
    public SPARQLDataSource(String endpointUrl, String sparqlStatement) {
        this.endpointUrl = endpointUrl;
        this.sparqlStatement = sparqlStatement;
    }

    /**
     * Execute the query and store it in the result variable.
     */
    private void init() throws JRException {
        if (result != null) {
            return;
        }
        if (endpointUrl == null) {
            throw new JRException("Endpoint URLs can't be null");
        }
        if (sparqlStatement == null || sparqlStatement.length() == 0) {
            throw new JRException("SPARQL statements can't be null");
        }
        try {
            endpointObj = new SPARQLRepository(endpointUrl);
            endpointObj.initialize();
        } catch (Exception e) {
            throw new JRException("Exception initializing endpoint " + endpointUrl, e);
        }
        try {
            conn = endpointObj.getConnection();
            TupleQuery q = conn.prepareTupleQuery(QueryLanguage.SPARQL, sparqlStatement);
            result = q.evaluate();
            System.out.println("Bindings got, size: " + result.getBindingNames().size());
        } catch (Exception e) {
            throw new JRException("Exception connecting to endpoint " + endpointUrl, e);
        } finally {
            try {
                conn.close();
            } catch (RepositoryException e) {
                e.printStackTrace();
            }
        }
    }

    // default implementation returns strings
    // TODO improve in the future to return required classes
    @Override
    public Object getFieldValue(JRField field) throws JRException {
        init();
        Object o = null;

        String fieldName = field.getName();
        Value value = currentBinding.getValue(fieldName);

        if (value != null) {
            o = value.stringValue();
        } else {
            o = "N/A for " + fieldName;
        }
        return o;
    }

    @Override
    public boolean next() throws JRException {
        init();
        try {
            if (result.hasNext()) {
                currentBinding = result.next();
                return true;
            }
        } catch (QueryEvaluationException e) {
            e.printStackTrace();
            throw new JRException("Error in next() " + e);
        }

        return false;
    }

}
