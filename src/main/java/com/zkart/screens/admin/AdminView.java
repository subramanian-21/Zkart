package com.zkart.screens.admin;

import com.zkart.screens.BaseUserView;

public class AdminView extends BaseUserView {
    private static AdminView instance;
    private AdminViewModel viewModel;
    private AdminView(){
        viewModel = new AdminViewModel();
    }
    public static AdminView getInstance(){
        if(instance == null) {
            instance = new AdminView();
        }
        return instance;
    }

    // admin operations
}
