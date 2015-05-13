/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.unam.pixel.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

/**
 *
 * @author guillermorojas
 */
@Controller("loginController")
@Scope("request")
public class LoginController {
    
    private String username;
    private String rol = "";
    private Boolean loged;
    private Boolean admin;

    public Boolean getAdmin() {

        admin=getRol().equals("ROLE_ADMIN");
      return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }
    
    
    @PostConstruct
    public void init(){
        rol = "";
      Object user =SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if ( !(auth instanceof AnonymousAuthenticationToken) && (user instanceof UserDetails) ) {
            this.username=((UserDetails)user).getUsername();
            
            if(auth.getAuthorities().size()>0){
                this.rol=auth.getAuthorities().iterator().next().getAuthority();
            }
            this.loged=true;
            
        }
        else if(user instanceof String){
            this.username= user.toString();
            this.loged=false;
     }
        
    }

    public String getUsername() {
        return username;
    }


    public Boolean getLoged() {
        return loged;
    }


    public String getRol() {
        return rol;
    }
    
 
   
    
    public void doLogin() {
        System.out.println("sdsdsdsdsdsddsddsdsdsds");
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
                System.out.println("1");
        RequestDispatcher dispatcher =((ServletRequest) context.getRequest()).getRequestDispatcher("/j_spring_security_check");
        System.out.println("2");
        try {
            
            dispatcher.forward((ServletRequest) context.getRequest(), (ServletResponse)context.getResponse());
            System.out.println("3");
            FacesContext.getCurrentInstance().responseComplete();
            System.out.println("4");
        } catch (Exception ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String doLogout(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/index.xhtml";
    }
    
}
