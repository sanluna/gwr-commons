package com.sanluna.multitenancy.multitenancy;

import com.sanluna.security.GWRTokenHelper;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

import static com.sanluna.multitenancy.multitenancy.TenantContext.TENANT_HEADER;
import static com.sanluna.multitenancy.multitenancy.TenantContext.setCurrentTenant;

/**
 * This class intercepts the request in order to get the tenantId from firstly the principal which will exist only if the user requesting is logged in
 * otherwise it will read the tenantId header the order is important for not letting users on other tenants access information from others.
 */

public class GWRTenantInterceptor extends HandlerInterceptorAdapter {

    private static ArrayList<String> ignoreList = new ArrayList<>();

    public GWRTenantInterceptor() {
    ignoreList.add("status/health");
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String pathInfo = request.getPathInfo();
        if(pathInfo == null){
            pathInfo = request.getRequestURI().substring(request.getContextPath().length());
        }
        for(String x : ignoreList){
            if(pathInfo.contains(x)){
                return true;
            }
        }
        try {
            setCurrentTenant(GWRTokenHelper.currentTenant());
            return true;
        } catch (Exception e) {
            System.out.println("unable to retrieve tenant from principal!");
        }
        if (request.getHeader(TENANT_HEADER) != null && !request.getHeader(TENANT_HEADER).isEmpty()) {
            setCurrentTenant(request.getHeader(TENANT_HEADER));
            System.out.println("Setting tenant from header! " + request.getHeader(TENANT_HEADER));
            return true;
        }
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write("No tenant!");
        response.getWriter().flush();
        return false;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        TenantContext.clear();
    }
}
