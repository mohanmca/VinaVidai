mvn compile -Dmaven.scala.displayCmd=true  -X
mvn clean jetty:run 
- sometime remove maven repostiory to remove corrupted jar-

Run External Configuration
--------------------------
1) Location for MVN.Bat
2) Working Directory Local worksapce
3) jetty:run


MAVEN_OPTS
-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=4000,server=y,suspend=n

Maven Jetty Goals
--------------------
jetty
jetty:run
jetty:run-war
jetty:config
jetty:stop
jetty:help
jetty:help -Ddetail=true -Dgoal=<goal-name>
 
mvn eclipse:eclipse -DdownloadSources=true



