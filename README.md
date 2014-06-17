# README Custom SPARQL Data Source

## Installation
If you want to compile the SPARQL Data Source you'll have to first install JasperReports Server, JDK and Ant locally, and set the webAppDir property in build.xml. If you just want to install the component you can copy the files in *webapp* to the same locations in JasperReports Server. Then you must also make a few changes to the other configuration files.

Since SPARQL is not recognised by Jasper Reports as a query language, you'll have to add it otherwise you can't create queries on JasperReports Server. In WEB-INF/applicationContext-rest-services.xml find the `<util:list id="queryLanguagesCe">`add a line to the list of values.
```
<value>sparql</value>
```
You will also have to tell JasperReports Server how to display a nicer label for 'sparql'. In WEB-INF/bundles/jasperserver_messages.properties add a property for the name of sparql:
```
query.language.sparql.label=SPARQL
```

## Compilation

See the readme.txt for how to compile a Custom Data Source

## Creating a Data Source

After the installation you can log into your JasperReports Server and create a new Data Source. You chose the type: SPARQL Endpoint Data Source. There is one additional property. *Endpoint URL*. There are many endpoints in the world. Try one of these:

* http://dbpedia.org/sparql
* http://semantic.eea.europa.eu/sparql
* http://geo.linkeddata.es/sparql
* http://services.data.gov.uk/transport/sparql
* http://www.fao.org/figis/flod/endpoint/flod
* http://data.linkedmdb.org/sparql

If you try DBPedia, here is a sample query:

```
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>
PREFIX dbpp: <http://dbpedia.org/ontology/PopulatedPlace/>

SELECT ?subject ?name sample(?lat) AS ?lat sample(?long) AS ?long sample(?area) AS ?areaKM
WHERE {
  ?subject a <http://dbpedia.org/class/yago/EuropeanCountries> ;
           geo:lat ?lat;
           geo:long ?long;
           dbpp:areaTotal ?area;
           rdfs:label ?name  FILTER(LANG(?name) = "en")
}
ORDER BY ?name
```

You can then create a report in JasperReports Studio that uses the variables name, lat, long and areaKM.

## Issues

When you create an Ad Hoc View based on a topic (report) then you get an exception.

net.sf.jasperreports.engine.JRException: SPARQL statements can't be null
    at eionet.jasperreports.cds.SPARQLDataSource.init(SPARQLDataSource.java:74)
    at eionet.jasperreports.cds.SPARQLDataSource.next(SPARQLDataSource.java:151)
    at com.jaspersoft.commons.semantic.dsimpl.JRQueryDataSet$JRDataSetIterator.next(JRQueryDataSet.java:379)
    at com.jaspersoft.commons.datarator.CachedData.fetchData(CachedData.java:148)
