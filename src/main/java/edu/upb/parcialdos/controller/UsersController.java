/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.upb.parcialdos.controller;

import edu.upb.parcialdos.dao.UserDAO;
import edu.upb.parcialdos.model.Users;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;


/**
 *
 * @author GLEISSON
 */
@Named(value = "usersController")
@SessionScoped
public class UsersController implements Serializable {
    
    

    private Users actual;
    private DataModel<Users> listado = null;
    private Users usersSesion;

    

    @EJB
    UserDAO userDAO;

    public UsersController() {
        actual = new Users();
    }

    public Users getActual() {
        return actual;
    }

    public void setActual(Users actual) {
        this.actual = actual;
    }

    public String login() {

        Users resultado = userDAO.findAuthLogin(actual);

        if (resultado == null) {
            actual = new Users();
            return "index?faces-redirect=true";
        } else {
            usersSesion = resultado;
            return "view?faces-redirect=true";
        }
    }

    public DataModel<Users> getListado() {
        if (listado == null) {
            listado = new ListDataModel(userDAO.findAll());
        }

        return listado;
    }

    // Navigation methods
    public String viewList() {
        listado = new ListDataModel(userDAO.findAll());
        return "list?faces-redirect=true";
    }

    public String viewDetails() {
        
        return "";
    }

    public String viewCreate() {
        actual = new Users();
        return "create?faces-redirect=true";
    }

    public String viewEdit() {
        actual = listado.getRowData();
        return "edit?faces-redirect=true";
    }

    // Action methods
    public String doSave() {
        try {
            userDAO.create(actual);
        } catch (Exception ex) {
            return viewCreate();
        }

        return viewList();
    }

    public String doUpdate() {
        try {
            actual = listado.getRowData();
            userDAO.edit(actual);
        } catch (Exception ex) {
            return viewList();
        }

        return viewList();
    }

    public String doDelete() {
        try {
            actual = listado.getRowData();
            userDAO.remove(actual);
        } catch (Exception ex) {
            return viewList();
        }

        return viewList();
    }

    
}
