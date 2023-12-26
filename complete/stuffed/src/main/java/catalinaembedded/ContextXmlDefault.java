package catalinaembedded;
public class ContextXmlDefault implements org.apache.catalina.startup.ContextConfig.ContextXml {
public void load(org.apache.catalina.Context tc_StandardContext_0) {
org.apache.catalina.core.StandardContext tc_StandardContext_1 = (org.apache.catalina.core.StandardContext) tc_StandardContext_0;
tc_StandardContext_1.addWatchedResource("WEB-INF/web.xml");
tc_StandardContext_1.addWatchedResource("WEB-INF/tomcat-web.xml");
tc_StandardContext_1.addWatchedResource("/home/opc/project/tmp/tomcat-native-jp/stuffed/conf/web.xml");
}
}
