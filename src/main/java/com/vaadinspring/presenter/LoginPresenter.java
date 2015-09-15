/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vaadinspring.presenter;

import com.vaadin.navigator.View;

/**
 *
 * @author m.zhaksygeldy
 */
public interface LoginPresenter {
    public boolean doLogin(String username,String password);
    public String getNavigatorView();
    public boolean hasRole(String role);
    public boolean canUserAccessView(View oldView,View newView);
}
