/**
 * Title: MatcherUtil.java
 * Description:
 * Copyright: Copyright (c) 2019
 * Company: Sharp
 * 
 * @Project Name: TanyaCommon
 * @Package: com.srct.service.tanya.common.config.filter
 * @author Sharp
 * @date 2019-02-09 09:48:22
 */
package com.srct.service.tanya.common.config.filter;

/**
 * @author Sharp
 *
 */
public class PathMatcherUtil {

    public static boolean matches(String pattern, String requestPath) {

        if (pattern != null && requestPath != null) {
            pattern = pattern.trim();
            requestPath = requestPath.trim();
            // Case 1 - Exact Match
            if (pattern.equals(requestPath))
                return (true);

            // Case 2 - Path Match ("/.../*")
            if (pattern.equals("/*"))
                return (true);
            if (pattern.endsWith("/*")) {
                if (pattern.regionMatches(0, requestPath, 0, pattern.length() - 2)) {
                    if (requestPath.length() == (pattern.length() - 2)) {
                        return (true);
                    } else if ('/' == requestPath.charAt(pattern.length() - 2)) {
                        return (true);
                    }
                }
                return (false);
            }

            // Case 3 - Extension Match
            if (pattern.startsWith("*.")) {
                int slash = requestPath.lastIndexOf('/');
                int period = requestPath.lastIndexOf('.');
                if ((slash >= 0) && (period > slash) && (period != requestPath.length() - 1)
                    && ((requestPath.length() - period) == (pattern.length() - 1))) {
                    return (pattern.regionMatches(2, requestPath, period + 1, pattern.length() - 2));
                }
            }

            // Case 4 - "Default" Match
            return (false); // NOTE - Not relevant for selecting filters
        }
        return false;
    }

}
