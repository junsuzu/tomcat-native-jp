package catalinaembedded.Catalina.localhost;
public class ContextXml__manager implements org.apache.catalina.startup.ContextConfig.ContextXml {
public void load(org.apache.catalina.Context tc_StandardContext_0) {
org.apache.catalina.core.StandardContext tc_StandardContext_1 = (org.apache.catalina.core.StandardContext) tc_StandardContext_0;
tc_StandardContext_1.setAntiResourceLocking(Boolean.valueOf("false"));
tc_StandardContext_1.setPrivileged(Boolean.valueOf("true"));


org.apache.tomcat.util.http.Rfc6265CookieProcessor tc_Rfc6265CookieProcessor_2 = new org.apache.tomcat.util.http.Rfc6265CookieProcessor();
tc_Rfc6265CookieProcessor_2.setSameSiteCookies("strict");
tc_StandardContext_1.setCookieProcessor(tc_Rfc6265CookieProcessor_2);


org.apache.catalina.session.StandardManager tc_StandardManager_3 = new org.apache.catalina.session.StandardManager();
tc_StandardManager_3.setSessionAttributeValueClassNameFilter("java\\.lang\\.(?:Boolean|Integer|Long|Number|String)|org\\.apache\\.catalina\\.filters\\.CsrfPreventionFilter\\$LruCache(?:\\$1)?|java\\.util\\.(?:Linked)?HashMap");
tc_StandardContext_1.setManager(tc_StandardManager_3);
}
}
