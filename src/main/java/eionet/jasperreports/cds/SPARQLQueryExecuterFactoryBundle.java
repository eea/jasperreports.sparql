package eionet.jasperreports.cds;

import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReportsContext;
import net.sf.jasperreports.engine.query.JRQueryExecuterFactoryBundle;
import net.sf.jasperreports.engine.query.QueryExecuterFactory;

/**
 *
 * @author Søren Roug
 */
public class SPARQLQueryExecuterFactoryBundle implements JRQueryExecuterFactoryBundle {

    private static final SPARQLQueryExecuterFactoryBundle INSTANCE = new SPARQLQueryExecuterFactoryBundle();

    private JasperReportsContext jasperReportsContext;

    private SPARQLQueryExecuterFactoryBundle() {
        this(DefaultJasperReportsContext.getInstance());
    }

    private SPARQLQueryExecuterFactoryBundle(JasperReportsContext jasperReportsContext) {
        this.jasperReportsContext = jasperReportsContext;
    }

    /**
     *
     */
    public static SPARQLQueryExecuterFactoryBundle getInstance() {
        return INSTANCE;
    }

    /**
     *
     */
    public static SPARQLQueryExecuterFactoryBundle getInstance(JasperReportsContext jasperReportsContext) {
        return new SPARQLQueryExecuterFactoryBundle(jasperReportsContext);
    }

    @Override
    public String[] getLanguages() {
        return new String[]{"sparql"};
    }

    @Override
    public QueryExecuterFactory getQueryExecuterFactory(String language) throws JRException {
        // We could actually cache this...
        return new SPARQLQueryExecuterFactory();
    }

}
