package com.assignment.openapi.web.searchcomp1;

import com.assignment.openapi.web.searchcomp1.presentation.dto.SearchComp1Request;

public class Comp1AppUtils {
    public static String makeQueryString(SearchComp1Request request){
        return "query="+request.getQuery()+"&sort="+request.getSort()+"&page="+request.getPage()+"&size="+request.getSize();
    }
    public static String makeURI(String uri, String contentsType){
        return uri+contentsType;
    }
}
