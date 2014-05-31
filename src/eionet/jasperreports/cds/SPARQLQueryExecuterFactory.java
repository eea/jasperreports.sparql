package eionet.jasperreports.cds;

import java.util.Map;

import net.sf.jasperreports.engine.JasperReportsContext;
import net.sf.jasperreports.engine.JRDataset;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRValueParameter;
import net.sf.jasperreports.engine.query.JRQueryExecuter;
import net.sf.jasperreports.engine.query.QueryExecuterFactory;

/**
 * @author Søren Roug
 *
 */
@SuppressWarnings("deprecation")
public class SPARQLQueryExecuterFactory implements QueryExecuterFactory {

    @Override
    public JRQueryExecuter createQueryExecuter(JasperReportsContext ctx,
            JRDataset dataset, Map<String,? extends JRValueParameter> parameters) throws JRException {
        return new SPARQLQueryExecuter(dataset, parameters);
    }

    @Override
    public JRQueryExecuter createQueryExecuter(JRDataset dataset, Map<String,? extends JRValueParameter> parameters) throws JRException {
        return new SPARQLQueryExecuter(dataset, parameters);
    }

    @Override
    public Object[] getBuiltinParameters() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see net.sf.jasperreports.engine.query.JRQueryExecuterFactory#supportsQueryParameterType(java.lang.String)
     */
    @Override
    public boolean supportsQueryParameterType(String className) {
        // TODO Auto-generated method stub
        return true;
    }

}
