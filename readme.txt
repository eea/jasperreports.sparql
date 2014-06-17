Installing Custom Data Source Examples

These examples may be used in both JasperServer or JasperServer Pro. For further information on developing
custom data sources, refer to the JasperServer User's Guide, or the JasperServer Pro Administration Guide.

The examples are found in <js-install>/samples/customDataSource. Once you have deployed a JasperServer web 
application to your application server, you can use Ant to build and deploy the examples. 
If you used an installer to install JasperServer version 2.1 or later, you will have Ant installed already. Ant may be run with 
the following command:

<js-install>/ant/bin/ant <ant-arguments>(for Linux/Unix)
<js-install>\ant\bin\ant <ant-arguments> (for Windows)

If you installed JasperServer manually with a WAR file, you will need to download Ant from http://ant.apache.org. Ant 
1.6.2 was used for testing, but earlier versions should also work.

The JVM used for installing the examples needs to be a full Java Development Kit, because it needs to compile Java source 
files. Ensure that the JAVA_HOME environment variable points to a JDK installation.
The sample directory includes the following files and directories:
*	build.xml: The Ant build file
*	src: Java source directory
*	webapp: A directory containing other files required by the examples, such as JSPs and Spring configuration files, 
    which are copied directly to the JasperServer web application directory
*	reports: A directory containing example JRXML files that use the sample custom data sources

Take the following steps to install the samples in your JasperServer web application (this can be built from the source code 
or the delivered version of JasperServer):
*	At the command line, change directories to the custom data source sample directory (<js-
    install>/samples/customDataSource)
*	Edit build.xml and set the webAppDir property to the root of the JasperServer web application.
*	Run the Ant command (see above) with no arguments, which will execute the default target, which is named 
    deploy. The deploy target will run the following tasks:
    o	Compile the Java source under the src directory
    o	Deploy the compiled Java class files to the JasperServer web application
    o	Deploy files under the webapp directory to the the JasperServer web application
*	Restart the application server
