# Tomcatサーバーnative image化デモ
このデモではTomcatサーバをnative image化する手順を示しています。

## Overview  
* このデモではTomcatサーバをnative image化する手順を示しています。
* 以下のTomcatオフィシャルサイトの記事[Ahead of Time compilation support](https://tomcat.apache.org/tomcat-11.0-doc/graal.html)を参考にしています：
* サンプルアプリケーションはSpring Frameworkに準拠しています。

## デモ環境
* [Tomcat 10.0.27](https://tomcat.apache.org/download-10.cgi)
* [GraalVM for JDK 21](https://www.oracle.com/jp/java/technologies/downloads/#graalvmjava21)
* [Apache Maven 3.6.3](https://maven.apache.org/download.cgi)
* [Apache Ant 1.10.14](https://ant.apache.org/bindownload.cgi)

> **NOTE:** 上記ソフトウェアはすべてインストール済みという前提でデモ手順を記載します。

## Contents
* **[1. サンプルアプリケーションをTomcatに導入](#1-サンプルアプリケーションをTomcatに導入)**

* **[2. AOT用テンプレートのダウンロード](#2-AOT用テンプレートのダウンロード)**
   
* **[3. パッケージングとネイティブビルド](#3-パッケージングとネイティブビルド)**

## 1. サンプルアプリケーションをTomcatに導入
```
git clone https://github.com/junsuzu/tomcat-native-jp
$ cd spring-framework-tomcat-sample
$ mvn clean package
$ cd target
```

target配下にwarファイルがビルドされることを確認します。
```
[opc@instance target]$ ls -la
total 4276
drwxrwxr-x. 7 opc opc     132 Dec 24 06:09 .
drwxrwxr-x. 4 opc opc      46 Dec 24 06:09 ..
drwxrwxr-x. 3 opc opc      17 Dec 24 06:09 classes
drwxrwxr-x. 3 opc opc      25 Dec 24 06:09 generated-sources
drwxrwxr-x. 2 opc opc      28 Dec 24 06:09 maven-archiver
drwxrwxr-x. 3 opc opc      35 Dec 24 06:09 maven-status
drwxrwxr-x. 4 opc opc      54 Dec 24 06:09 springTomcat
-rw-rw-r--. 1 opc opc 4378492 Dec 24 06:09 springTomcat.war
```
springTomcat.warファイルをTomcatサーバのwebappsディレクトリにコピーし、Tomcatサーバ上アプリが正常に稼働することを確認してください。

```
[opc@jms-instance-2 target]$ cd /opt/apache-tomcat-10.0.27/bin
[opc@jms-instance-2 bin]$ ./startup.sh
Using CATALINA_BASE:   /opt/apache-tomcat-10.0.27
Using CATALINA_HOME:   /opt/apache-tomcat-10.0.27
Using CATALINA_TMPDIR: /opt/apache-tomcat-10.0.27/temp
Using JRE_HOME:        /usr/lib64/graalvm/graalvm-java21
Using CLASSPATH:       /opt/apache-tomcat-10.0.27/bin/bootstrap.jar:/opt/apache-tomcat-10.0.27/bin/tomcat-juli.jar
Using CATALINA_OPTS:   
Tomcat started.
[opc@jms-instance-2 bin]$ curl http://localhost:8080/springTomcat/greeting
Hello Spring Framework World
[opc@jms-instance-2 bin]$
```

## 2. AOT用テンプレートのダウンロード

Tomcatの公式ドキュメントhttps://tomcat.apache.org/tomcat-11.0-doc/graal.html に従って、Tomcat Stuffed moduleをダウンロードしてください。
```
git clone https://github.com/apache/tomcat.git

cd tomcat/modules/stuffed
```

Tomcatサーバ配下デプロイ済みのspringTomcatをstuffed配下のwebappsディレクトリにコピーします。
spring-framework-tomcat-sample/src/main/java配下のJavaソースをstuffed/webappsの配下にコピーします。
```
cd spring-framework-tomcat-sample
cp -r src/main/java/* ../stuffed/webapps/springTomcat/WEB-INF/classes/
```

Tomcatサーバのconfディレクトリ配下のすべてをstuffed/confディレクトリにコピーします。


## 3. パッケージングとネイティブビルド
stuffed配下のpom.xmlを修正します。

tomcat.versionを実際に使用するTomcatのバージョンに変更します。
springframeworkのバージョンを追加します。

```
<properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <mainClass>org.apache.catalina.startup.Tomcat</mainClass>
    <!--tomcat.version>11.0.0-M14</tomcat.version-->
    <tomcat.version>10.0.27</tomcat.version>
    <!--add for springframework-->
    <spring-framework.version>6.0.2</spring-framework.version>
</properties>
```

springframework用のdependencyを追加します。
```
<dependencies>
        <!-- add for springframework -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-core</artifactId>
            <version>${spring-framework.version}</version>
        </dependency>       
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>${spring-framework.version}</version>
        </dependency>
        <!-- add for springframework -->
.....................
</dependencies>
```

mavenでビルドをします。
```
cd stuffed
mvn package
```
mavenによるビルドが完了後、Antによりビルドします。

```
ant -Dwebapp.name=springTomcat -f webapp-jspc.ant.xml
```

再度mavenでビルドをします。
```
mvn package
```