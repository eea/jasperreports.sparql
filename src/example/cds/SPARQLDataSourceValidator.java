package example.cds;

import java.util.Map;

import org.springframework.validation.Errors;

import com.jaspersoft.jasperserver.api.engine.jasperreports.util.CustomDataSourceValidator;
import com.jaspersoft.jasperserver.api.metadata.jasperreports.domain.CustomReportDataSource;

/**
 * @author bob
 *
 */
public class SPARQLDataSourceValidator implements CustomDataSourceValidator {

	/* check the values in the map; call rejectValue if tests don't pass
	 */
	public void validatePropertyValues(CustomReportDataSource ds, Errors errors) {
		String endpoint = null;
		Map props = ds.getPropertyMap();
		if (props != null) {
			endpoint = (String) ds.getPropertyMap().get("endpoint");
		}
//TODO: check for a sane URL.
		if (endpoint == null || endpoint.length() == 0) {
			reject(errors, "endpoint");
		}
	}

	// first arg is the path of the property which has the error
	// for custom DS's this will always be in the form "reportDataSource.propertyMap[yourPropName]"
	private void reject(Errors errors, String name) {
		errors.rejectValue("reportDataSource.propertyMap[" + name + "]", "sparqlDataSource." + name + ".required");
	}
}
