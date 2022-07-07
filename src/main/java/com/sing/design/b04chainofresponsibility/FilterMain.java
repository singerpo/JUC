package com.sing.design.b04chainofresponsibility;

import java.util.ArrayList;
import java.util.List;

/**
 * @author songbo
 * @since 2022-07-06
 */
public class FilterMain {
    public static void main(String[] args) {
        Request request = new Request("request");
        Response response = new Response("response");
        FilterChain filterChain = new FilterChain();
        filterChain.addFilter(new HtmlFilter()).addFilter(new WordFilter())
                .doFilter(request,response);


    }
}

interface Filter {
    boolean doFilter(Request request, Response response, FilterChain filterChain);
}

class Request {
    String str;

    public Request(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return "Request{" +
                "str='" + str + '\'' +
                '}';
    }
}

class Response {
    String str;

    public Response(String str) {
        this.str = str;
    }


    @Override
    public String toString() {
        return "Response{" +
                "str='" + str + '\'' +
                '}';
    }
}

class FilterChain  {
    List<Filter> filters = new ArrayList<>();
    int index = -1;

    public FilterChain addFilter(Filter filter) {
        filters.add(filter);
        return this;
    }


    public boolean doFilter(Request request, Response response) {
        index++;
        if (index >= filters.size()) {
            return true;
        }
        boolean result = filters.get(index).doFilter(request, response, this);

        return result;
    }
}

class HtmlFilter implements Filter {

    @Override
    public boolean doFilter(Request request, Response response, FilterChain filterChain) {
        System.out.println("HtmlFilter" + request);
        filterChain.doFilter(request, response);
        System.out.println("HtmlFilter" + response);
        return true;
    }
}

class WordFilter implements Filter {

    @Override
    public boolean doFilter(Request request, Response response, FilterChain filterChain) {
        System.out.println("WordFilter" + request);
        filterChain.doFilter(request, response);
        System.out.println("WordFilter" + response);
        return true;
    }
}
