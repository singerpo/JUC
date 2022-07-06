package com.sing.design.b04chainofresponsibility;

import java.util.ArrayList;
import java.util.List;

/**
 * @author songbo
 * @since 2022-07-06
 */
public class FilterMain {
    public static void main(String[] args) {

    }
}

interface Filter {
    boolean doFilter(Request request, Response response,FilterChain filterChain);
}

class Request {
    String str;
}

class Response {
    String str;
}

class FilterChain implements Filter{
    List<Filter> filters = new ArrayList<>();
    int index = 0;

    public Filter addFilter(Filter filter){
        filters.add(filter);
        return this;
    }

    @Override
    public boolean doFilter(Request request, Response response,FilterChain filterChain) {
        boolean flag = filters.get(index).doFilter(request,response,filterChain);
        index++;
        return flag;
    }
}

class HtmlFilter implements Filter{

    @Override
    public boolean doFilter(Request request, Response response,FilterChain filterChain) {
        System.out.println(request);
        filterChain.doFilter(request,response,filterChain);
        System.out.println(response);
        return false;
    }
}

class WordFilter implements Filter{

    @Override
    public boolean doFilter(Request request, Response response,FilterChain filterChain) {
        return false;
    }
}
