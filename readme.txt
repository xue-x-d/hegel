
install sdk jar to maven local repository

    mvn install:install-file -Dfile=taobao-sdk-java-online_standard-20130330.jar -Dpackaging=jar -DgroupId=com.taobao.api -DartifactId=taobao-sdk -Dversion=20130330
    mvn install:install-file -Dfile=taobao-sdk-java-jdp.jar -Dpackaging=jar -DgroupId=com.taobao.api -DartifactId=jdp -Dversion=20130321
    mvn install:install-file -Dfile=tbschedule-3.2.9.jar -Dpackaging=jar -DgroupId=com.taobao.api -DartifactId=tbschedule -Dversion=3.2.9
    mvn install:install-file -Dfile=datapush-2.0.0.jar -Dpackaging=jar -DgroupId=com.taobao.api -DartifactId=datapush -Dversion=2.0.0
    mvn install:install-file -Dfile=taobao-sdk-java-online_standard-20140519.jar -Dpackaging=jar -DgroupId=com.taobao.api -DartifactId=taobao-sdk -Dversion=20140519
    mvn install:install-file -Dfile=comet-client-0.0.1.jar -Dpackaging=jar -DgroupId=jd.comet.client -DartifactId=api -Dversion=0.0.1
    mvn install:install-file -Dfile=open-api-sdk-2.0.jar -Dpackaging=jar -DgroupId=com.jd.api -DartifactId=sdk -Dversion=2.0
    
mvn compile
npm install

run test
  mvn -Dtest=xxxx.UserManagerTest#testBigIntTime test

 source  filedir/xxx.sql
 
start server
  nohup mvn exec:java -Dexec.mainClass="xxxx.xxxxx.xxxxx.xxx" -Dexec.args="yourArgs" | nohup cronolog ./nohup_%Y%m%d.log &