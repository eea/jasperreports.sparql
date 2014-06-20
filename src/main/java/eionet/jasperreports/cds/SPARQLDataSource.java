package eionet.jasperreports.cds;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.openrdf.model.Literal;
import org.openrdf.model.Value;
import org.openrdf.model.Resource;
import org.openrdf.query.BindingSet;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.sparql.SPARQLRepository;


/**
 * SPARQL data source implementation for JasperReports Library.
 *
 * @author Søren Roug, European Environment Agency
 */
public class SPARQLDataSource implements JRDataSource {

    private static final Log log = LogFactory.getLog(SPARQLDataSource.class);

    /** URL of the endpoint. */
    private String endpointUrl;

    private SPARQLRepository endpointObj;

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
     * Make the result available to other classes in package.
     */
    BindingSet getCurrentRow() throws JRException {
        return currentBinding;
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
            throw new JRException("SPARQL statements can't be null for " + endpointUrl);
        }
        try {
            endpointObj = new SPARQLRepository(endpointUrl);
            endpointObj.initialize();
        } catch (Exception e) {
            throw new JRException("Exception initializing endpoint " + endpointUrl, e);
        }
        try {
            conn = endpointObj.getConnection();
            log.info("Executing SPARQL: " + sparqlStatement);
            TupleQuery q = conn.prepareTupleQuery(QueryLanguage.SPARQL, sparqlStatement);
            result = q.evaluate();
            log.debug("Bindings got, size: " + result.getBindingNames().size());
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

    /**
     * Gets the field value for the current position.
     * Give the caller the class it is asking for. For Resource, ask for string until we
     * have implemented a resource type.
     */
    @Override
    public Object getFieldValue(JRField field) throws JRException {
        init();
        Object o = null;

        String fieldName = field.getName();
        Class<?> fieldClass = field.getValueClass();

        Value value = currentBinding.getValue(fieldName);

        if (value == null) {
            return null;
        }

        // Can always convert to string
        if (fieldClass.equals(String.class)) {
            return value.stringValue();
        }
        if (value instanceof Literal) {
            Literal lValue = (Literal) value;
            if (fieldClass.equals(Boolean.class)) {
               return lValue.booleanValue();
            } else if (fieldClass.equals(Integer.class)) {
               return (Integer) lValue.intValue();
            } else if (fieldClass.equals(Long.class)) {
               return (Long) lValue.longValue();
            } else if (fieldClass.equals(BigDecimal.class)) {
               return lValue.decimalValue();
            } else if (fieldClass.equals(Date.class)) {
               return new Date(lValue.calendarValue().toGregorianCalendar().getTimeInMillis());
            } else if (fieldClass.equals(Time.class)) {
               return new Time(lValue.calendarValue().toGregorianCalendar().getTimeInMillis());
            } else if (fieldClass.equals(Timestamp.class)) {
               return new Timestamp(lValue.calendarValue().toGregorianCalendar().getTimeInMillis());
            } else if (fieldClass.equals(java.util.Date.class)) {
               return new java.util.Date(lValue.calendarValue().toGregorianCalendar().getTimeInMillis());
            }
//      } else if (value instanceof Resource) {
//           return value.stringValue();
        } else {
            throw new JRException("Field '" + fieldName + "' is of class '" + fieldClass.getName() + "' and can not be converted");
        }
        return null;
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
