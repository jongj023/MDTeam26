# MDTeam26 
Jelle de Jong, Bas van Dueren den Hollander, Moenish Baladien, Wessel van Wezel, Randall Theuns.

*This file will contain all startup information, as well as project structure explanation.*

#TO START THE APPLICATION:

1. Add to your IDE of choice as existing project / Import Project. Make sure the IDE knows its a Maven project.
2. Add the database connection. This can be done in the 'WebSecurityConfig.java', the DataSource bean. For database structure it will assume that the database contains at least 2 tables called 'user' and 'user_roles'. For full database structure, refer to the SQL file also added to the project.
3. The application should now be able to start.

- How to import into IDE - 
IntelliJ IDEA - https://www.jetbrains.com/idea/help/importing-project-from-maven-model.html
Eclipse - https://books.sonatype.com/m2eclipse-book/reference/creating-sect-importing-projects.html
Netbeans - https://stackoverflow.com/questions/20846641/import-a-maven-project-into-netbeans

#PROJECT STRUCTURE 
All the main Java-based code will be located under: "/src/main/java/com/locker/*" This location has more sub-folder, each with their own responsibility:
/configuration/ : Contains all the @Configuration files. Currently this includes the following:
    - MvcConfig: Basic Spring MVC configuration.
    - SpringSecurityInitializer: Initializes Spring Security
    - ThymeleafConfiguration: All the configuration needed for Thymeleaf.
    - WebSecurityConfig: Spring Security configuration. 
/controller/ : Contains all the @Controller which handle the RequestMappings. 
/dao/ : This folder contains all the @Repository files. These handle any database queries and will extend CrudRepository<X, Y>. Any Repository files will be picked up the JPA folder scanner (as defined in 'LockerApplication.java') so that they can be Autowired.
/errorhandling/ : Will contain all and any files pertaining to ErrorHandling. 
/model/ : All the @Entity classes are located here. These are classes modeled after database tables so that Spring can both validate, as well as use these in DAO classes.
/service/ : Any @Service files are located here. 

/src/main/resources/ only uses the logback.xml* for now. (* is used for SLF4J logging)

/src/main/webapp/ Contains two major folder:
/resources/ : Contains any resource in the form of CSS, Fonts, JS, Images.
/WEB-INF/ : Primary used for:
    /pages/ : Location of all the HTML webpages, which are actually Thymeleaf templates. 
        /fragments/ : All HTML fragments to by used by th:include/th:replace are located here. 
