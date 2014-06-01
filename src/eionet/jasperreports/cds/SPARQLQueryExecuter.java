package eionet.jasperreports.cds;

import java.util.Map;

import net.sf.jasperreports.engine.JasperReportsContext;
import net.sf.jasperreports.engine.JRDataset;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRValueParameter;
import net.sf.jasperreports.engine.query.JRAbstractQueryExecuter;
import java.text.SimpleDateFormat;
import java.math.BigDecimal;
import java.util.Date;
import java.sql.Time;
import java.sql.Timestamp;


/**
 * @author Søren Roug
 *
 */
public class SPARQLQueryExecuter extends JRAbstractQueryExecuter {

    /** URL of the endpoint. */
    private String endpointUrl;

    private String sparqlStatement;

    /** Format of xsd:date value. */
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    /** Format of xsd:dateTime value. */
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'hh:mm:ss";

    /** Date format. */
    private static SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    /** Date-time format. */
    private static SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DATE_TIME_FORMAT);


    /**
     * Constructor.
     * @param dataset
     * @param parameters
     */
    @SuppressWarnings("deprecation")
    public SPARQLQueryExecuter(JRDataset dataset, Map parameters) {
        super(dataset, parameters);
        parseQuery();
    }

    /**
     * Constructor.
     * @param dataset
     * @param parameters
     */
    public SPARQLQueryExecuter(JasperReportsContext ctx, JRDataset dataset, Map parameters) {
        super(ctx, dataset, parameters);
        parseQuery();
    }

    /* (non-Javadoc)
     * @see net.sf.jasperreports.engine.query.JRQueryExecuter#cancelQuery()
     */
    @Override
    public boolean cancelQuery() throws JRException {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see net.sf.jasperreports.engine.query.JRQueryExecuter#close()
     */
    @Override
    public void close() {
        // TODO Auto-generated method stub

    }

    @Override
    public JRDataSource createDatasource() throws JRException {
        endpointUrl = getStringParameterOrProperty("endpoint");
        sparqlStatement = getQueryString();
        return new SPARQLDataSource(endpointUrl, sparqlStatement);
    }

    @Override
    protected String getParameterReplacement(String parameterName) {
        JRValueParameter valueParam = getValueParameter(parameterName);
        Class<?> paramClass = valueParam.getValueClass();
        if (paramClass.equals(String.class)) {
            return "'" + valueParam.getValue().toString().replaceAll("'", "''") + "'";
        } else if (Boolean.class.isInstance(paramClass) || Number.class.isInstance(paramClass)) {
            return valueParam.getValue().toString();
        } else if (Time.class.isInstance(paramClass) || Timestamp.class.isInstance(paramClass)) {
            return "\"" + dateTimeFormat.format((Date) valueParam.getValue()) + "\"^^xsd:date";
        } else if (Date.class.isInstance(paramClass)) {
            return "\"" + dateFormat.format((Date) valueParam.getValue()) + "\"^^xsd:dateTime";
//        } else {
//            throw new JRException("Field '" + parameterName + "' is of class '" + paramClass.getName() + "' and can not be converted");
        }
        return valueParam.getValue().toString();
    }

}
