package eionet.jasperreports.cds;

import com.jaspersoft.ireport.designer.data.ReportQueryDialog;
import com.jaspersoft.ireport.designer.FieldsProvider;
import com.jaspersoft.ireport.designer.FieldsProviderEditor;
import com.jaspersoft.ireport.designer.IReportConnection;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import net.sf.jasperreports.engine.design.JRDesignField;
import net.sf.jasperreports.engine.JRDataset;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

import org.openrdf.query.algebra.TupleExpr;
import org.openrdf.query.parser.ParsedQuery;
import org.openrdf.query.parser.ParsedTupleQuery;
import org.openrdf.query.parser.QueryParserUtil;
import org.openrdf.query.QueryLanguage;

public class SPARQLQueryFieldsProvider implements FieldsProvider {

    @Override
    public boolean supportsGetFieldsOperation() {
        return true;
    }

    @Override
    public JRField[] getFields(IReportConnection con, JRDataset reportDataset, Map parameters )
            throws JRException, UnsupportedOperationException {
        ArrayList fields = new ArrayList();

        try {
            String queryString = reportDataset.getQuery().getText();
            ParsedTupleQuery pq = QueryParserUtil.parseTupleQuery(QueryLanguage.SPARQL, queryString, null);
            TupleExpr te = pq.getTupleExpr();
            Set<String> vars = te.getBindingNames();
            for (String var : vars) {
                JRDesignField field = new JRDesignField();
                field.setName(var);
                field.setValueClassName("java.lang.String");
                fields.add(field);
            }
            return (JRField[]) fields.toArray(new JRField[fields.size()]);
        } catch (Exception e) {
            throw new JRException("Something went wrong");
        } 

    }

    @Override
    public boolean supportsAutomaticQueryExecution() {
        return true;
    }

    @Override
    public boolean hasEditorComponent() {
        return false;
    }

    @Override
    public boolean hasQueryDesigner() {
        return false;
    }

    @Override
    public String designQuery(IReportConnection con, String query, ReportQueryDialog reportQueryDialog )
            throws JRException, UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public FieldsProviderEditor getEditorComponent(ReportQueryDialog reportQueryDialog ) {
        return null;
    }

}

