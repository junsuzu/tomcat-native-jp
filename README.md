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

* **[2. AOT用テンプレートのダウンロード](#Step2-Build-sample-project)**
   
* **[3. パッケージングとネイティブビルド](#Step3-Run-the-demo)**

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