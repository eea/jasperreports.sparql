package example.cds;

import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRDataset;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.query.JRAbstractQueryExecuter;

/**
 * @author bob
 *
 */
public class SPARQLQueryExecuter extends JRAbstractQueryExecuter {

    private String sparqlStatement;
    private String endpointUrl;

    /**
     * @param dataset
     * @param parameters
     */
    public SPARQLQueryExecuter(JRDataset dataset, Map parameters) {
        super(dataset, parameters);
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

    /* (non-Javadoc)
     * @see net.sf.jasperreports.engine.query.JRQueryExecuter#createDatasource()
     */
    // FIXME
    @Override
    public JRDataSource createDatasource() throws JRException {
        endpointUrl = getStringParameterOrProperty("endpoint");
        sparqlStatement = getQueryString();
        return new SPARQLDataSource(endpointUrl, sparqlStatement);
    }

    /* (non-Javadoc)
     * @see net.sf.jasperreports.engine.query.JRAbstractQueryExecuter#getParameterReplacement(java.lang.String)
     */
    @Override
    protected String getParameterReplacement(String parameterName) {
        // FIXME
        return "'" + getStringParameterOrProperty(parameterName) + "'";
    }

}
