package example.cds;

import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRParameter;

import com.jaspersoft.jasperserver.api.metadata.jasperreports.service.ReportDataSourceService;

/**
 * @author swood
 *
 */
public class SPARQLDataSourceService implements ReportDataSourceService {

    private String endpointUrl;
    private String sparqlStatement;

    public SPARQLDataSourceService() {
    }

    public SPARQLDataSourceService(JRDataSource ds) {
    }

    /* (non-Javadoc)
     * @see com.jaspersoft.jasperserver.api.metadata.jasperreports.service.ReportDataSourceService#closeConnection()
     */
    @Override
    public void closeConnection() {
        // Do nothing
    }

    /* (non-Javadoc)
     * @see com.jaspersoft.jasperserver.api.metadata.jasperreports.service.ReportDataSourceService#setReportParameterValues(java.util.Map)
     */
    @Override
    public void setReportParameterValues(Map parameterValues) {
        parameterValues.put(JRParameter.REPORT_DATA_SOURCE,
                new SPARQLDataSource(endpointUrl, sparqlStatement));
        // these are for the benefit of the cache
        parameterValues.put("endpoint", endpointUrl);
    }

    public String getEndpoint() {
        return endpointUrl;
    }

    public void setEndpoint(String endpointUrl) {
        this.endpointUrl = endpointUrl;
    }

//  public String getPath() {
//      return sparqlStatement;
//  }

//  public void setPath(String sparqlStatement) {
//      this.sparqlStatement = sparqlStatement;
//  }
}
