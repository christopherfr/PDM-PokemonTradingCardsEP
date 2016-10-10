package pe.com.chfernandezrios.pokemontradingcards.beans.responses;

import pe.com.chfernandezrios.pokemontradingcards.beans.Usuario;

/**
 * Created by chfernandezrios on 2/10/2016.
 */
public class LoginResponse {
    private String msg;
    private Status status;
    private Usuario usuario;

    public LoginResponse(String msg, Status status, Usuario usuario) {
        this.msg = msg;
        this.status = status;
        this.usuario = usuario;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
