# README - SPARQL Data Source
The SPARQL Data Source is a module to install on a JasperReports Server to connect queries to public SPARQL endpoints according to the [W3C SPARQL Protocol specification](http://www.w3.org/TR/sparql11-protocol/). When governments are launching projects to make data available in this form, then it is called Linked Open Data (LOD).

## Installation
If you just want to install the component you can copy the files in *webapp* to the same locations in JasperReports Server. Then you must also make a few changes to the other configuration files.

Since SPARQL is not recognised by Jasper Reports as a query language, you'll have to add it otherwise you can't create queries on JasperReports Server. In WEB-INF/applicationContext-rest-services.xml find the `<util:list id="queryLanguagesCe">`add a line to the list of values.
```
<value>sparql</value>
```
You will also have to tell JasperReports Server how to display a nicer label for 'sparql'. In WEB-INF/bundles/jasperserver_messages.properties add a property for the name of sparql:
```
query.language.sparql.label=SPARQL
```

## Compilation
If you want to compile the SPARQL Data Source you'll have to first install the Java Development Kit and Maven locally.

To compile you first have to get some JAR files from a JasperReports Server installation. You can find them in the server's WEB-INF/lib. We have provided them from the Community Edition version 5.6.0, but the version you want to build against might be different. The files are jasperserver-api-engine-impl-X.Y.Z.jar, jasperserver-api-common-X.Y.Z.jar and jasperserver-api-metadata-X.Y.Z.jar. Copy them to the project root (here) and deploy them to your local 3rd party library:

```
mvn deploy:deploy-file \
  -Dfile=jasperserver-api-engine-impl-X.Y.Z.jar \
  -DgroupId=com.jaspersoft.jasperserver.api.engine.impl \
  -DartifactId=jasperserver-api-engine-impl \
  -Dversion=X.Y.Z -Dpackaging=jar -Durl=file:3rdparty

mvn deploy:deploy-file \
  -Dfile=jasperserver-api-common-X.Y.Z.jar \
  -DgroupId=com.jaspersoft.jasperserver.api.common \
  -DartifactId=jasperserver-api-common \
  -Dversion=X.Y.Z -Dpackaging=jar -Durl=file:3rdparty

mvn deploy:deploy-file \
  -Dfile=jasperserver-api-metadata-X.Y.Z.jar \
  -DgroupId=com.jaspersoft.jasperserver.api.metadata \
  -DartifactId=jasperserver-api-metadata \
  -Dversion=X.Y.Z -Dpackaging=jar -Durl=file:3rdparty
```
You can then delete them from the project root.

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

## Installing it into iReport Designer

Copy openrdf-sesame-2.5.1.jar, slf4j-api-1.5.6.jar, slf4j-log4j12-1.5.6.jar and SPARQLDataSource-1.0-SNAPSHOT.jar in the ireport directory to a location that can be seen by iReport. Add the JAR files to the Classpath tab in the Options dialog. On the *Query Executers* tab, add a query executer for sparql. The Query Executer Factory is eionet.jasperreports.cds.SPARQLQueryExecuterFactory. The Fields Provider Class can be eionet.jasperreports.cds.SPARQLQueryFieldsProvider or null.

Add a "Query Executer mode" data source to your iReport data sources. It facilitates the execution of a report's embedded query.

## Creating a report in iReport Designer

* Choose a template.
* Add a parameter called 'endpoint'. At the right side, you can then select the properties of the parameter. If you don't see the properties, select it from the Window drop down menu. Set the default value expression to the URL of the SPARQL endpoint. (Usually ends with .../sparql). Remember to put it in double-quotes.
* Add your SPARQL query via the Report query button.
* You can now read the field names and get a preview of the output
* When you upload the report to the report repository, don't select a data source.
