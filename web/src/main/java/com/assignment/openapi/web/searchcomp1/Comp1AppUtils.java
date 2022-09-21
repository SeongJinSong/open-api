package com.assignment.openapi.web.searchcomp1;

import com.assignment.openapi.web.searchcomp1.presentation.dto.SearchComp1Request;

public class Comp1AppUtils {
    public static String host = "https://dapi.kakao.com";
    public static String contentType = "application/x-www-form-urlencoded";
    public static String accept = "application/json";
    public static String authorization = "KakaoAK 9ccac738a217f2aa9d006c01900809cc";
    public static String makeQueryString(SearchComp1Request request){
        return "query="+request.getQuery()+"&sort="+request.getSort()+"&page="+request.getPage()+"&size="+request.getSize();
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
