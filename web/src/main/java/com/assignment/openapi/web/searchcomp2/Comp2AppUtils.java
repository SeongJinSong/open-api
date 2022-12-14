package com.assignment.openapi.web.searchcomp2;

import com.assignment.openapi.web.searchcomp2.presentation.dto.SearchComp2Request;

public class Comp2AppUtils {
    public static String host = "https://openapi.naver.com";
    public static final String clientId = "XJtfor9ELawGMydkFQeV";
    public static final String clientSecret = "sAed7tzJ85";

    public static String makeQueryString(SearchComp2Request request){
        return "query="+request.getQuery()+"&sort="+request.getSort()+"&start="+(request.getStart()==null?"0":request.getStart())+"&display="+(request.getDisplay()==null?"0":request.getDisplay());
    }
    public static String makeURI(String uri, String contentsType){
        return uri+contentsType;
    }
    public static String getComp1ApiRedisKey(String uri, String queryString) {
        return host+"/"+ uri +"?" + queryString;
    }
    public static String getUrlTemplate(String uri, String queryString){
        return "/"+uri+"?"+queryString;
    }
}
