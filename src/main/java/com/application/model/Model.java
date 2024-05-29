package com.application.model;

import com.application.view.ViewFactory;

public class Model {

    private static Model model;
    private final ViewFactory viewFactory;
    private Model() {
        this.viewFactory = new ViewFactory();
    }

    // doan sau su dung SingleTon pattern;
    public static synchronized Model getInstance() {
        if (model == null) {
            model = new Model();
        }
        return model;
    }

    public  ViewFactory getViewFactory() {
        return viewFactory;
    }
}
