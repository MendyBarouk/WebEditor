package com.firstproject.mendy.webeditor;

import java.io.Serializable;

/**
 * Created by Mendy on 20/12/2016.
 */


public class WebString implements Serializable {
    private String first;
    private String second;
    private String third;

    private String html;
    private String css;
    private String javaScript;

    public WebString(String html, String css, String javaScript) {
        this.first = "<!DOCTYPE html><html><head><style>";
        this.second = "</style></head><body>";
        this.third = "</script></body></html> ";
        this.html = html;
        this.css = css;
        this.javaScript = "<script>" + javaScript;
    }

    @Override
    public String toString() {
        return first+css+second+html+javaScript+third;
    }
}
