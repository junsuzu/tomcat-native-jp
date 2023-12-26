# Tomcatサーバーnative image化デモ
このデモではTomcatサーバをnative image化する手順を示しています。

## Overview  
* このデモはTomcatサーバおよびサーバ上で稼働するアプリケーションをnative image化する手順を示します。  
* 以下のTomcatオフィシャルサイトの記事https://tomcat.apache.org/tomcat-11.0-doc/graal.html を参考にしています。  
        1. Tomcatサーバおよび稼働アプリケーションの依存関係を含む単一実行可能なJARファイルを生成します。  
        2. そのJARファイルに対して、GraalVMが提供するエージェントツールを使用してnative imageを生成するためのメタデータを生成します。  
        3. GraalVMが提供するネイティブビルドツールを使用してnative imageを生成します。  
* 本デモにおけるサンプルアプリケーションはSpring Framework6.xに準拠しています。

## デモ環境
* [Tomcat 10.0.27](https://tomcat.apache.org/download-10.cgi)
* [GraalVM for JDK 21](https://www.oracle.com/jp/java/technologies/downloads/#graalvmjava21)
* [Apache Maven 3.6.3](https://maven.apache.org/download.cgi)
* [Apache Ant 1.10.14](https://ant.apache.org/bindownload.cgi)

> **NOTE:** 上記ソフトウェアはすべてインストール済みという前提でデモ手順を記載します。Linux上インストールした各ソフトウェアの環境変数を設定する~/.bashrcの例を以下示します。
```
export CATALINA_HOME=/opt/apache-tomcat-10.0.27

export JAVA_HOME=/usr/lib64/graalvm/graalvm-java21
export PATH=$JAVA_HOME/bin:$PATH

export MVN_HOME=/opt/apache-maven-3.6.3
export PATH=$MVN_HOME/bin:$PATH

export ANT_HOME=/opt/apache-ant-1.10.14
export PATH=$ANT_HOME/bin:$PATH

```

## Contents
* **[1. サンプルアプリケーションをTomcatに導入](#1-サンプルアプリケーションをTomcatに導入)**

* **[2. AOT用テンプレートのダウンロード](#2-AOT用テンプレートのダウンロード)**
   
* **[3. パッケージングとネイティブビルド](#3-パッケージングとネイティブビルド)**

## 1. サンプルアプリケーションをTomcatに導入  
Spring Frameworkを使用したWebアプリケーションを作成して、warファイルとしてTomcatにデプロイし、正常稼働を確認します。のちにこのアプリケーションをnative image化します。

```
$ git clone https://github.com/junsuzu/tomcat-native-jp
$ cd spring-framework-tomcat-sample
$ mvn clean package
$ cd target
```

targetディレクトリ配下にspringTomcat.warファイルがビルドされることを確認します。
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
springTomcat.warファイルをTomcatサーバのwebappsディレクトリにコピーすると、warファイルが自動展開され、「springTomcat」というフォルダが生成されます。Tomcatサーバ上アプリが正常に稼働し、「Hello Spring Framework World」が表示されることを確認してください。

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

Tomcatの公式ドキュメントhttps://tomcat.apache.org/tomcat-11.0-doc/graal.html に従い、Tomcat Stuffed moduleをダウンロードしてください。
```
git clone https://github.com/apache/tomcat.git

```
本デモではstuffedフォルダーをtomcat-native-jp配下にコピーし、stuffed配下で後続タスクを行います。
```
cp -r tomcat/modules/stuffed ../tomcat-native-jp/

```
> **NOTE:** 参考のため、全タスク完了後のstuffedフォルダーをcompleteフォルダーに格納しております。  
> **NOTE:** stuffedの格納場所を環境変数として定義しておきます。以下は~/.bashrcにおける定義例です：
```
export TOMCAT_STUFFED=/home/opc/project/tomcat-native-jp/stuffed

```

Tomcatサーバにデプロイ済みのWebアプリケーション「springTomcat」フォルダーをstuffed配下のwebappsディレクトリにコピーします。 
```
cp -r $CATALINA_HOME/webapps/springTomcat $TOMCAT_STUFFED/webapps/
```
同様にTomcatサーバのROOTとmanagerもコピーします。
```
cp -r $CATALINA_HOME/webapps/ROOT $TOMCAT_STUFFED/webapps/
cp -r $CATALINA_HOME/webapps/manager $TOMCAT_STUFFED/webapps/
```


spring-framework-tomcat-sample/src/main/java配下のJavaソースをstuffed/webappsの配下にコピーします。  

```
cd spring-framework-tomcat-sample
cp -r src/main/java/* $TOMCAT_STUFFED/webapps/springTomcat/WEB-INF/classes/
```

Tomcatサーバのconfディレクトリ配下のすべてをstuffed/confディレクトリにコピーします。  
```
cp -r $CATALINA_HOME/conf/* $TOMCAT_STUFFED/conf/
```

## 3. パッケージングとネイティブビルド
stuffed配下のpom.xmlを修正します。  

tomcat.versionを実際に使用するTomcatのバージョンに変更します。  
また、springframeworkのバージョンを追加します。
> **NOTE:** 実際のWebアプリケーションに合わせて、必要に応じてライブラリやプラグインを追加してください。  

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
mavenによるビルドが完了後、Antで再度ビルドします。webapp.name変数には実際配布済みのWebアプリケーション名を指定します。

```
ant -Dwebapp.name=springTomcat -f webapp-jspc.ant.xml
ant -Dwebapp.name=ROOT -f webapp-jspc.ant.xml
ant -Dwebapp.name=manager -f webapp-jspc.ant.xml
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
> **NOTE:** 実行時にJuli関連のTomcatロギング用ライブラリが見つからないというエラーメッセージが出ますが、検証には特に支障ありません。エラーメッセージを回避したい場合、confディレクトリ配下のlogging.propertiesを編集し、Juli関連のライブラリ記述をコメントアウトしてください。

Ctrl+Cで上記コマンドで起動したプロセスを終了し、再度mavenでビルドをします。
```
mvn package
```

Native image実行時、Reflectionを解決するため、以下のagentツールを使用して、メタデータを生成します。
```
$JAVA_HOME/bin/java\
        -agentlib:native-image-agent=config-output-dir=$TOMCAT_STUFFED/target/\
        -Dorg.graalvm.nativeimage.imagecode=agent\
        -Dcatalina.base=. -Djava.util.logging.config.file=conf/logging.properties\
        -jar target/tomcat-stuffed-1.0.jar --catalina -useGeneratedCode
```
native image用メタデータを生成するため、Webアプリケーションに含まれるすべてのパターンを実行する必要があります。今回使用するWebアプリケーションはコンテキストルートおよびサーブレットが処理するためのURLパターン「greeting」がありますので、別ターミナルを立ち上げ、この２つのパターンをそれぞれ実行します。
```
[opc@jms-instance-2 /]$ curl http://localhost:8080/springTomcat/
<html>
<body>
<h2>Hello Spring!</h2>
</body>
</html>
[opc@jms-instance-2 /]$ curl http://localhost:8080/springTomcat/greeting
Hello Spring Framework World
```

実行できましたら、Ctrl+CでJavaプロセスを停止します。

GraalVMのネイティブビルドツールを使用してnative imageをビルドします。
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
stuffedディレクトリ配下にネイティブ実行ファイル「tomcat-stuffed-1.0」が生成されていることを確認してください。  
native imageを起動します。
```
./tomcat-stuffed-1.0 -Dcatalina.base=. -Djava.util.logging.config.file=conf/logging.properties --catalina -useGeneratedCode
```
別ターミナルからWebアプリケーションが正常稼働することを確認してください。
```
[opc@jms-instance-2 /]$ curl http://localhost:8080/springTomcat/greeting
Hello Spring Framework World
```