# JMS Message Capture Tool
### This tool can be used to capture the JMS based messages from Queue/Topic sources

> The tool works with all type of servers supporting JMS Based message transfer mechanism like Weblogic Queue/Topics, ActiveMQ Queue/Topics, JBoss Queue/Topics, etc.., all that is needed is placing your own library jar file in “src/main/resources/wljar” directory with name “wlfullclient.jar”
> The tool captures messages from Queue/Topic sources, displays them on console for easy access and also archives them for future needs.


> Capturing messages is very easy, just add the JNDI names into the “config.properties” and update the password into the “login.properties” file within “src/main/resources” directory.

### Weblogic specific example

* Go to the server/lib directory in the weblogic server 
* cd WL_HOME/server/lib 
* Use the following command to create wlfullclient.jar in the server/lib directory: 
* java -jar wljarbuilder.jar 
* You can now copy and bundle the wlfullclient.jar with client applications. 
* Add the wlfullclient.jar to the client application’s classpath by copying it to src/main/resourcces/wljar directory

Todo: upadte jars
