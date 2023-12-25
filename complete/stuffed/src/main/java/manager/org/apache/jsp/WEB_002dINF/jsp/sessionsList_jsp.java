/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat10
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package manager.org.apache.jsp.WEB_002dINF.jsp;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.jsp.*;
import java.util.Collection;
import org.apache.catalina.manager.JspHelper;
import org.apache.catalina.Session;
import org.apache.catalina.ha.session.DeltaSession;
import org.apache.catalina.util.ContextName;
import org.apache.catalina.manager.DummyProxySession;

public final class sessionsList_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final jakarta.servlet.jsp.JspFactory _jspxFactory =
          jakarta.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("jakarta.servlet");
    _jspx_imports_packages.add("jakarta.servlet.http");
    _jspx_imports_packages.add("jakarta.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("org.apache.catalina.util.ContextName");
    _jspx_imports_classes.add("org.apache.catalina.manager.DummyProxySession");
    _jspx_imports_classes.add("org.apache.catalina.manager.JspHelper");
    _jspx_imports_classes.add("java.util.Collection");
    _jspx_imports_classes.add("org.apache.catalina.Session");
    _jspx_imports_classes.add("org.apache.catalina.ha.session.DeltaSession");
  }

  private volatile jakarta.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public jakarta.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
  }

  public void _jspDestroy() {
  }

  public void _jspService(final jakarta.servlet.http.HttpServletRequest request, final jakarta.servlet.http.HttpServletResponse response)
      throws java.io.IOException, jakarta.servlet.ServletException {

    if (!jakarta.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
      final java.lang.String _jspx_method = request.getMethod();
      if ("OPTIONS".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        return;
      }
      if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPs only permit GET, POST or HEAD. Jasper also permits OPTIONS");
        return;
      }
    }

    final jakarta.servlet.jsp.PageContext pageContext;
    final jakarta.servlet.ServletContext application;
    final jakarta.servlet.ServletConfig config;
    jakarta.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    jakarta.servlet.jsp.JspWriter _jspx_out = null;
    jakarta.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=ISO-8859-1");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, false, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
      out.write("\n\n\n\n\n\n\n<!DOCTYPE html\n     PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\"\n     \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n\n\n<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\">\n");
 String path = (String) request.getAttribute("path");
   String version = (String) request.getAttribute("version");
   ContextName cn = new ContextName(path, version);
   String submitUrl = JspHelper.escapeXml(response.encodeURL(
           ((HttpServletRequest) pageContext.getRequest()).getRequestURI() +
           "?path=" + path + "&version=" + version));
   Collection<Session> activeSessions = (Collection<Session>) request.getAttribute("activeSessions");

      out.write("\n<head>\n    <meta http-equiv=\"content-type\" content=\"text/html; charset=iso-8859-1\"/>\n    <meta http-equiv=\"pragma\" content=\"no-cache\"/><!-- HTTP 1.0 -->\n    <meta http-equiv=\"cache-control\" content=\"no-cache,must-revalidate\"/><!-- HTTP 1.1 -->\n    <meta http-equiv=\"expires\" content=\"0\"/><!-- 0 is an invalid value and should be treated as 'now' -->\n    <meta http-equiv=\"content-language\" content=\"en\"/>\n    <meta name=\"author\" content=\"Cedrik LIME\"/>\n    <meta name=\"copyright\" content=\"copyright 2005-2022 the Apache Software Foundation\"/>\n    <meta name=\"robots\" content=\"noindex,nofollow,noarchive\"/>\n    <title>Sessions Administration for ");
      out.print( JspHelper.escapeXml(cn.getDisplayName()) );
      out.write("</title>\n</head>\n<body>\n<h1>Sessions Administration for ");
      out.print( JspHelper.escapeXml(cn.getDisplayName()) );
      out.write("</h1>\n\n<p>Tips:</p>\n<ul>\n    <li>Click on a column to sort.</li>\n    <li>To view a session details and/or remove a session attributes, click on its id.</li>\n</ul>\n\n<div class=\"error\">");
      out.print( JspHelper.escapeXml(request.getAttribute("error")) );
      out.write("</div>\n<div class=\"message\">");
      out.print( JspHelper.escapeXml(request.getAttribute("message")) );
      out.write("</div>\n\n<form action=\"");
      out.print( submitUrl );
      out.write("\" method=\"post\" id=\"sessionsForm\">\n    <fieldset><legend>Active HttpSessions information</legend>\n        <input type=\"hidden\" name=\"action\" id=\"sessionsFormAction\" value=\"injectSessions\"/>\n        <input type=\"hidden\" name=\"sort\" id=\"sessionsFormSort\" value=\"");
      out.print( JspHelper.escapeXml(request.getAttribute("sort")) );
      out.write("\"/>\n        ");
 String order = (String) request.getAttribute("order");
           if (order == null || "".equals(order)) {
               order = "ASC";
           }
        
      out.write("\n        <input type=\"hidden\" name=\"order\" id=\"sessionsFormSortOrder\" value=\"");
      out.print( JspHelper.escapeXml(order) );
      out.write("\"/>\n        <input type=\"submit\" name=\"refresh\" id=\"refreshButton\" value=\"Refresh Sessions list\" onclick=\"document.getElementById('sessionsFormAction').value='refreshSessions'; return true;\"/>\n        ");
      out.print( JspHelper.formatNumber(activeSessions.size()) );
      out.write(" active Sessions<br/>\n        <table border=\"1\" cellpadding=\"2\" cellspacing=\"2\" width=\"100%\">\n            <thead>\n                <tr>\n                    <th><a onclick=\"document.getElementById('sessionsFormSort').value='id'; document.getElementById('refreshButton').click(); return true;\">Session Id</a></th>\n                    <th><a onclick=\"document.getElementById('sessionsFormSort').value='id'; document.getElementById('refreshButton').click(); return true;\">Type</a></th>\n                    <th><a onclick=\"document.getElementById('sessionsFormSort').value='locale'; document.getElementById('refreshButton').click(); return true;\">Guessed Locale</a></th>\n                    <th><a onclick=\"document.getElementById('sessionsFormSort').value='user'; document.getElementById('refreshButton').click(); return true;\">Guessed User name</a></th>\n                    <th><a onclick=\"document.getElementById('sessionsFormSort').value='CreationTime'; document.getElementById('refreshButton').click(); return true;\">Creation Time</a></th>\n");
      out.write("                    <th><a onclick=\"document.getElementById('sessionsFormSort').value='LastAccessedTime'; document.getElementById('refreshButton').click(); return true;\">Last Accessed Time</a></th>\n                    <th><a onclick=\"document.getElementById('sessionsFormSort').value='UsedTime'; document.getElementById('refreshButton').click(); return true;\">Used Time</a></th>\n                    <th><a onclick=\"document.getElementById('sessionsFormSort').value='InactiveTime'; document.getElementById('refreshButton').click(); return true;\">Inactive Time</a></th>\n                    <th><a onclick=\"document.getElementById('sessionsFormSort').value='TTL'; document.getElementById('refreshButton').click(); return true;\"><span title=\"Time To Live\">TTL</span></a></th>\n                </tr>\n            </thead>\n            ");
 if (activeSessions.size() > 10) { 
      out.write("\n            <tfoot>");
      out.write("\n                <tr>\n                    <th><a onclick=\"document.getElementById('sessionsFormSort').value='id'; document.getElementById('refreshButton').click(); return true;\">Session Id</a></th>\n                    <th><a onclick=\"document.getElementById('sessionsFormSort').value='id'; document.getElementById('refreshButton').click(); return true;\">Type</a></th>\n                    <th><a onclick=\"document.getElementById('sessionsFormSort').value='locale'; document.getElementById('refreshButton').click(); return true;\">Guessed Locale</a></th>\n                    <th><a onclick=\"document.getElementById('sessionsFormSort').value='user'; document.getElementById('refreshButton').click(); return true;\">Guessed User name</a></th>\n                    <th><a onclick=\"document.getElementById('sessionsFormSort').value='CreationTime'; document.getElementById('refreshButton').click(); return true;\">Creation Time</a></th>\n                    <th><a onclick=\"document.getElementById('sessionsFormSort').value='LastAccessedTime'; document.getElementById('refreshButton').click(); return true;\">Last Accessed Time</a></th>\n");
      out.write("                    <th><a onclick=\"document.getElementById('sessionsFormSort').value='UsedTime'; document.getElementById('refreshButton').click(); return true;\">Used Time</a></th>\n                    <th><a onclick=\"document.getElementById('sessionsFormSort').value='InactiveTime'; document.getElementById('refreshButton').click(); return true;\">Inactive Time</a></th>\n                    <th><a onclick=\"document.getElementById('sessionsFormSort').value='TTL'; document.getElementById('refreshButton').click(); return true;\"><span title=\"Time To Live\">TTL</span></a></th>\n                </tr>\n            </tfoot>\n            ");
 } // end if 
      out.write("\n            <tbody>\n");

    for (Session currentSession : activeSessions) {
       String currentSessionId = JspHelper.escapeXml(currentSession.getId());
       String type;
       if (currentSession instanceof DeltaSession) {
           if (((DeltaSession) currentSession).isPrimarySession()) {
               type = "Primary";
           } else {
               type = "Backup";
           }
       } else if (currentSession instanceof DummyProxySession) {
           type = "Proxy";
       } else {
           type = "Primary";
       }

      out.write("\n                <tr>\n                    <td><input type=\"checkbox\" name=\"sessionIds\" value=\"");
      out.print( currentSessionId );
      out.write("\" />\n                      ");

                        if ("Proxy".equals(type)) {
                            out.print(currentSessionId);
                        } else {
                      
      out.write("\n                      <a href=\"");
      out.print( submitUrl );
      out.write("&amp;action=sessionDetail&amp;sessionId=");
      out.print( currentSessionId );
      out.write("&amp;sessionType=");
      out.print( type );
      out.write('"');
      out.write('>');
      out.print( currentSessionId );
      out.write("</a>\n                      ");

                        }
                      
      out.write("\n                    </td>\n                    <td style=\"text-align: center;\">");
      out.print( type );
      out.write("</td>\n                    <td style=\"text-align: center;\">");
      out.print( JspHelper.guessDisplayLocaleFromSession(currentSession) );
      out.write("</td>\n                    <td style=\"text-align: center;\">");
      out.print( JspHelper.guessDisplayUserFromSession(currentSession) );
      out.write("</td>\n                    <td style=\"text-align: center;\">");
      out.print( JspHelper.getDisplayCreationTimeForSession(currentSession) );
      out.write("</td>\n                    <td style=\"text-align: center;\">");
      out.print( JspHelper.getDisplayLastAccessedTimeForSession(currentSession) );
      out.write("</td>\n                    <td style=\"text-align: center;\">");
      out.print( JspHelper.getDisplayUsedTimeForSession(currentSession) );
      out.write("</td>\n                    <td style=\"text-align: center;\">");
      out.print( JspHelper.getDisplayInactiveTimeForSession(currentSession) );
      out.write("</td>\n                    <td style=\"text-align: center;\">");
      out.print( JspHelper.getDisplayTTLForSession(currentSession) );
      out.write("</td>\n                </tr>\n");
 } // end while 
      out.write("\n            </tbody>\n        </table>\n        <p style=\"text-align: center;\">\n            <input type=\"submit\" name=\"invalidate\" value=\"Invalidate selected Sessions\" onclick=\"document.getElementById('sessionsFormAction').value='invalidateSessions'; return true;\"/>\n        </p>\n    </fieldset>\n</form>\n\n<form method=\"get\" action=\"");
      out.print(request.getContextPath());
      out.write("/html\">\n  <p style=\"text-align: center;\">\n    <input type=\"submit\" value=\"Return to main page\" />\n  </p>\n</form>\n\n");
      out.write("\n\n</body>\n</html>\n");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof jakarta.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}