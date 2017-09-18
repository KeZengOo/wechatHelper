package com.nuoxin.virtual.rep.api.config;

import java.io.Serializable;
import java.util.List;

/**
 * url匹配模式
 * <p>
 * Created by fenggang on 7/28/17.
 */
public class UrlPatternConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<String> includePatterns;
    private List<String> excludePatterns;

    public List<String> getIncludePatterns() {
        return includePatterns;
    }

    public void setIncludePatterns(List<String> includePatterns) {
        this.includePatterns = includePatterns;
    }

    public List<String> getExcludePatterns() {
        return excludePatterns;
    }

    public void setExcludePatterns(List<String> excludePatterns) {
        this.excludePatterns = excludePatterns;
    }

}
