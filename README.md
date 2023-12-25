# Tomcatサーバーnative image化デモ
このデモではTomcatサーバをnative image化する手順を示しています。

## Overview  
* このデモはTomcatサーバおよびサーバ上で稼働するアプリケーションをnative image化する手順を示します。  
* 以下のTomcatオフィシャルサイトの記事https://tomcat.apache.org/tomcat-11.0-doc/graal.html を参考にしています。  
        * Tomcatサーバおよび稼働アプリケーションの依存関係を含む単一実行可能なJARファイルを生成します。  
        * そのJARファイルに対して、GraalVMが提供するエージェントツールを使用してnative imageを生成するためのメタデータを生成します。  
        * GraalVMが提供するネイティブビルドツールを使用してnative imageを生成します。  
* 本デモにおけるサンプルアプリケーションはSpring Framework6.xに準拠しています。

## デモ環境
* [Tomcat 10.0.27](https://tomcat.apache.org/download-10.cgi)
* [GraalVM for JDK 21](https://www.oracle.com/jp/java/technologies/downloads/#graalvmjava21)
* [Apache Maven 3.6.3](https://maven.apache.org/download.cgi)
* [Apache Ant 1.10.14](https://ant.apache.org/bindownload.cgi)

> **NOTE:** 上記ソフトウェアはすべてインストール済みという前提でデモ手順を記載します。Linux上インストールした各ソフトウェアの環境変数を設定する~/.bashrcの例を以下示します。
```
export JAVA_HOME=/usr/lib64/graalvm/graalvm-java21
export PATH=$JAVA_HOME/bin:$PATH

export MVN_HOME=/opt/apache-maven-3.6.3
export PATH=$MVN_HOME/bin:$PATH

export ANT_HOM=/opt/apache-ant-1.10.14
export PATH=$ANT_HOME/bin:$PATH

```

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

Tomcatを含むコードを生成するために、以下のコマンドを実行します。
```
$JAVA_HOME/bin/java\
        -Dcatalina.base=. -Djava.util.logging.config.file=conf/logging.properties\
        -jar target/tomcat-stuffed-1.0.jar --catalina -generateCode src/main/java
```

再度mavenでビルドをします。
```
mvn package
```

Reflectionを解決するため、以下のagentツールを使用して、メタデータを生成します。
```
$JAVA_HOME/bin/java\
        -agentlib:native-image-agent=config-output-dir=$TOMCAT_STUFFED/target/\
        -Dorg.graalvm.nativeimage.imagecode=agent\
        -Dcatalina.base=. -Djava.util.logging.config.file=conf/logging.properties\
        -jar target/tomcat-stuffed-1.0.jar --catalina -useGeneratedCode
```

native imageをビルドします。
```
native-image --no-server\
        --allow-incomplete-classpath --enable-https\
        --initialize-at-build-time=org.eclipse.jdt,org.apache.el.parser.SimpleNode,jakarta.servlet.jsp.JspFactory,org.apache.jasper.servlet.JasperInitializer,org.apache.jasper.runtime.JspFactoryImpl\
        -H:+JNI -H:+ReportUnsupportedElementsAtRuntime\
        -H:+ReportExceptionStackTraces -H:EnableURLProtocols=http,https,jar,jrt\
        -H:ConfigurationFileDirectories=$TOMCAT_STUFFED/target/\
        -H:ReflectionConfigurationFiles=$TOMCAT_STUFFED/tomcat-reflection.json\
        -H:ResourceConfigurationFiles=$TOMCAT_STUFFED/tomcat-resource.json\
        -H:JNIConfigurationFiles=$TOMCAT_STUFFED/tomcat-jni.json\
        -jar $TOMCAT_STUFFED/target/tomcat-stuffed-1.0.jar
```

```
./tomcat-stuffed-1.0 -Dcatalina.base=. -Djava.util.logging.config.file=conf/logging.properties --catalina -useGeneratedCode
```