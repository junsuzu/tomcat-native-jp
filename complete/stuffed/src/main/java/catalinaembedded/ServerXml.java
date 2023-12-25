package catalinaembedded;
public class ServerXml implements org.apache.catalina.startup.Catalina.ServerXml {
public void load(org.apache.catalina.startup.Catalina tc_Catalina_0) {


org.apache.catalina.core.StandardServer tc_StandardServer_1 = new org.apache.catalina.core.StandardServer();
tc_StandardServer_1.setPort(Integer.valueOf("8005"));
tc_StandardServer_1.setShutdown("SHUTDOWN");


org.apache.catalina.startup.VersionLoggerListener tc_VersionLoggerListener_2 = new org.apache.catalina.startup.VersionLoggerListener();
tc_StandardServer_1.addLifecycleListener(tc_VersionLoggerListener_2);


org.apache.catalina.core.AprLifecycleListener tc_AprLifecycleListener_3 = new org.apache.catalina.core.AprLifecycleListener();
tc_AprLifecycleListener_3.setSSLEngine("on");
tc_StandardServer_1.addLifecycleListener(tc_AprLifecycleListener_3);


org.apache.catalina.core.JreMemoryLeakPreventionListener tc_JreMemoryLeakPreventionListener_4 = new org.apache.catalina.core.JreMemoryLeakPreventionListener();
tc_StandardServer_1.addLifecycleListener(tc_JreMemoryLeakPreventionListener_4);


org.apache.catalina.mbeans.GlobalResourcesLifecycleListener tc_GlobalResourcesLifecycleListener_5 = new org.apache.catalina.mbeans.GlobalResourcesLifecycleListener();
tc_StandardServer_1.addLifecycleListener(tc_GlobalResourcesLifecycleListener_5);


org.apache.catalina.core.ThreadLocalLeakPreventionListener tc_ThreadLocalLeakPreventionListener_6 = new org.apache.catalina.core.ThreadLocalLeakPreventionListener();
tc_StandardServer_1.addLifecycleListener(tc_ThreadLocalLeakPreventionListener_6);


org.apache.catalina.deploy.NamingResourcesImpl tc_NamingResourcesImpl_7 = new org.apache.catalina.deploy.NamingResourcesImpl();


org.apache.tomcat.util.descriptor.web.ContextResource tc_ContextResource_8 = new org.apache.tomcat.util.descriptor.web.ContextResource();
tc_ContextResource_8.setName("UserDatabase");
tc_ContextResource_8.setAuth("Container");
tc_ContextResource_8.setType("org.apache.catalina.UserDatabase");
tc_ContextResource_8.setDescription("User database that can be updated and saved");
tc_ContextResource_8.setProperty("factory", "org.apache.catalina.users.MemoryUserDatabaseFactory");
tc_ContextResource_8.setProperty("pathname", "conf/tomcat-users.xml");
tc_NamingResourcesImpl_7.addResource(tc_ContextResource_8);
tc_StandardServer_1.setGlobalNamingResources(tc_NamingResourcesImpl_7);


org.apache.catalina.core.StandardService tc_StandardService_9 = new org.apache.catalina.core.StandardService();
tc_StandardService_9.setName("Catalina");

org.apache.catalina.connector.Connector tc_Connector_10 = new org.apache.catalina.connector.Connector(new org.apache.coyote.http11.Http11NioProtocol());
tc_Connector_10.setPort(Integer.valueOf("8080"));
tc_Connector_10.setProperty("connectionTimeout", "20000");
tc_Connector_10.setRedirectPort(Integer.valueOf("8443"));
tc_Connector_10.setPortOffset(tc_StandardServer_1.getPortOffset());
tc_StandardService_9.addConnector(tc_Connector_10);


org.apache.catalina.core.StandardEngine tc_StandardEngine_11 = new org.apache.catalina.core.StandardEngine();
tc_StandardEngine_11.setName("Catalina");
tc_StandardEngine_11.setDefaultHost("localhost");
tc_StandardEngine_11.addLifecycleListener(new org.apache.catalina.startup.EngineConfig());
tc_StandardEngine_11.setParentClassLoader(tc_Catalina_0.getParentClassLoader());


org.apache.catalina.realm.LockOutRealm tc_LockOutRealm_12 = new org.apache.catalina.realm.LockOutRealm();


org.apache.catalina.realm.UserDatabaseRealm tc_UserDatabaseRealm_13 = new org.apache.catalina.realm.UserDatabaseRealm();
tc_UserDatabaseRealm_13.setResourceName("UserDatabase");
tc_LockOutRealm_12.addRealm(tc_UserDatabaseRealm_13);
tc_StandardEngine_11.setRealm(tc_LockOutRealm_12);


org.apache.catalina.core.StandardHost tc_StandardHost_14 = new org.apache.catalina.core.StandardHost();
tc_StandardHost_14.setName("localhost");
tc_StandardHost_14.setAppBase("webapps");
tc_StandardHost_14.setUnpackWARs(Boolean.valueOf("true"));
tc_StandardHost_14.setAutoDeploy(Boolean.valueOf("true"));
tc_StandardHost_14.setParentClassLoader(tc_StandardEngine_11.getParentClassLoader());
tc_StandardHost_14.addLifecycleListener(new org.apache.catalina.startup.HostConfig());


org.apache.catalina.valves.AccessLogValve tc_AccessLogValve_15 = new org.apache.catalina.valves.AccessLogValve();
tc_AccessLogValve_15.setDirectory("logs");
tc_AccessLogValve_15.setPrefix("localhost_access_log");
tc_AccessLogValve_15.setSuffix(".txt");
tc_AccessLogValve_15.setPattern("%h %l %u %t \"%r\" %s %b");
tc_StandardHost_14.addValve(tc_AccessLogValve_15);
tc_StandardEngine_11.addChild(tc_StandardHost_14);
tc_StandardService_9.setContainer(tc_StandardEngine_11);
tc_StandardServer_1.addService(tc_StandardService_9);
tc_Catalina_0.setServer(tc_StandardServer_1);
}
}
