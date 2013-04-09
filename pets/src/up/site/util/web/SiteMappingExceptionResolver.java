/*
 * Copyright 2002-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package up.site.util.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import up.site.constants.Constants;
import up.site.constants.SystemConfig;

public class SiteMappingExceptionResolver extends SimpleMappingExceptionResolver {

	Logger logger = Logger.getLogger(getClass());
	
	/**
	 * Actually resolve the given exception that got thrown during on handler execution, returning a ModelAndView that
	 * represents a specific error page if appropriate. <p>May be overridden in subclasses, in order to apply specific
	 * exception checks. Note that this template method will be invoked <i>after</i> checking whether this resolved applies
	 * ("mappedHandlers" etc), so an implementation may simply proceed with its actual exception handling.
	 * @param request current HTTP request
	 * @param response current HTTP response
	 * @param handler the executed handler, or <code>null</code> if none chosen at the time of the exception (for example,
	 * if multipart resolution failed)
	 * @param ex the exception that got thrown during handler execution
	 * @return a corresponding ModelAndView to forward to, or <code>null</code> for default processing
	 */
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request,
			HttpServletResponse response,
			Object handler,
			Exception ex) {

		logger.debug("Exception happended!",ex);
		
		// Expose ModelAndView for chosen error view.
		String viewName = determineViewName(ex, request);
		
		if (viewName == null) {
            return null;
		}

        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
		
        ModelAndView mv = null;
        
        if (requestURI.startsWith(contextPath + "/admin")) {
        	logger.warn("Admin module exception!" + ex.getMessage());
        	
            viewName = "admin/" + viewName;
            mv = getModelAndView(viewName, ex, request);
            mv.getModel().put("cPath", Constants.ADMIN_CPATH);

        } else if (requestURI.startsWith(contextPath + "/mobile")) {
        	if(ex instanceof IOException){}else{
        	logger.warn("Mobile module exception!" + ex.getMessage());
        	}
            viewName = "mobile/" + viewName;
            mv = getModelAndView(viewName, ex, request);
            mv.getModel().put("cPath", SystemConfig.getCpathMobile());
        } else if (requestURI.startsWith(contextPath + "/web")) {
        	if(ex instanceof IOException){}else{
        	logger.warn("Web module exception!" + ex.getMessage());
        	}
            viewName = "web/" + viewName;
            mv = getModelAndView(viewName, ex, request);
            mv.getModel().put("cPath", SystemConfig.getCpathWeb());
        } else {
        	logger.warn("Unknown module exception!" + ex.getMessage());
        }

        // Apply HTTP status code for error views, if specified.
        // Only apply it if we're processing a top-level request.
        Integer statusCode = determineStatusCode(request, viewName);
        if (statusCode != null) {
            applyStatusCodeIfPossible(request, response, statusCode);
        }

        return mv;
	}


}
