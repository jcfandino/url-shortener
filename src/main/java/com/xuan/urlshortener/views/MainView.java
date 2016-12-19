package com.xuan.urlshortener.views;

import io.dropwizard.views.View;

public class MainView extends View {

    public MainView() {
        super("/templates/main.ftl");
    }

}
