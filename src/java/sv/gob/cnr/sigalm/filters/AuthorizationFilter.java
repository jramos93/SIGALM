package sv.gob.cnr.sigalm.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.omnifaces.filter.HttpFilter;
import org.omnifaces.util.Servlets;

@WebFilter(filterName = "AuthorizationFilter", urlPatterns = {"/*"})
public class AuthorizationFilter extends HttpFilter {

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, HttpSession session, FilterChain chain) throws ServletException, IOException {
        String loginURL = request.getContextPath() + "/index.xhtml";

        boolean loggedIn = (session != null) && (session.getAttribute("usuario") != null);
        boolean loginRequest = request.getRequestURI().equals(loginURL);
        boolean resourceRequest = Servlets.isFacesResourceRequest(request);

        if (loggedIn || loginRequest || resourceRequest) {
            if (!resourceRequest) { 
                Servlets.setNoCacheHeaders(response);
            }

            chain.doFilter(request, response); // So, just continue request.
        }
        else {
            Servlets.facesRedirect(request, response, loginURL);
        }
    }

}