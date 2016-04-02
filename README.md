[![Build Status](https://drone.io/github.com/ReturnOnIntellingenceTraineeCommunity/rtc-app/status.png)](https://drone.io/github.com/ReturnOnIntellingenceTraineeCommunity/rtc-app/latest)
=======
RTC-app

Follow this steps to try out our application:

    - first of all, install jdk for java 7, maven 3, latest version of mysql
    - create 'rtcapp' schema in mysql and grant all privileges for this schema to your mysql user (it`s recommended to create user with same username and password like in hibernate.properties file)
    - download and install (mvn clean install) rtc-util from https://github.com/ReturnOnIntellingenceTraineeCommunity/rtc-util
    - download source code from this repo, after this you have several ways to follow:
        - navigate to repo`s root folder, execute mvn clean package and copy war file from target directory to webapps directory in tomcat, start tomcat
        - execute mvn clean tomcat7:run from repo`s root folder (useful when you want to edit freemarkers templates)
        - if you are an intellij idea user you can use tomcat 7 configuration to run application (useful for debugging)
