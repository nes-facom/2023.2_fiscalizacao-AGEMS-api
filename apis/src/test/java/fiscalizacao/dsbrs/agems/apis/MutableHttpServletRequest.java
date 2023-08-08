package fiscalizacao.dsbrs.agems.apis;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MutableHttpServletRequest extends HttpServletRequestWrapper {
  private final String SERVLET_PATH;
  private final Map<String, String> CUSTOM_HEADERS;

  public MutableHttpServletRequest(HttpServletRequest request) {
    super(request);
    this.CUSTOM_HEADERS = new HashMap<String, String>();
    this.SERVLET_PATH = request.getServletPath();
  }

  public void setServletPath(String servletPath) {
    ((HttpServletRequest) getRequest()).setAttribute(
        "javax.servlet.include.servlet_path",
        servletPath
      );
  }

  @Override
  public String getServletPath() {
    String customServletPath = (String) (
      (HttpServletRequest) getRequest()
    ).getAttribute("javax.servlet.include.servlet_path");
    return customServletPath != null ? customServletPath : SERVLET_PATH;
  }

  public void putHeader(String name, String value) {
    this.CUSTOM_HEADERS.put(name, value);
  }

  public String getHeader(String name) {
    String headerValue = CUSTOM_HEADERS.get(name);

    if (headerValue != null) {
      return headerValue;
    }

    return ((HttpServletRequest) getRequest()).getHeader(name);
  }

  public Enumeration<String> getHeaderNames() {
    Set<String> set = new HashSet<String>(CUSTOM_HEADERS.keySet());

    @SuppressWarnings("unchecked")
    Enumeration<String> e =
      ((HttpServletRequest) getRequest()).getHeaderNames();
    while (e.hasMoreElements()) {
      String n = e.nextElement();
      set.add(n);
    }

    return Collections.enumeration(set);
  }
}
