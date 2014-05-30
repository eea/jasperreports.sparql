package example.cds;

import java.util.Map;

import net.sf.jasperreports.engine.JRDataset;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.query.JRQueryExecuter;
import net.sf.jasperreports.engine.query.JRQueryExecuterFactory;

/**
 * @author bob
 *
 */
public class SPARQLQueryExecuterFactory implements JRQueryExecuterFactory {

    /* (non-Javadoc)
     * @see net.sf.jasperreports.engine.query.JRQueryExecuterFactory#createQueryExecuter(net.sf.jasperreports.engine.JRDataset, java.util.Map)
     */
    @Override
    public JRQueryExecuter createQueryExecuter(JRDataset dataset, Map parameters)
            throws JRException {
        // TODO Auto-generated method stub
        return new SPARQLQueryExecuter(dataset, parameters);
    }

    /* (non-Javadoc)
     * @see net.sf.jasperreports.engine.query.JRQueryExecuterFactory#getBuiltinParameters()
     */
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
