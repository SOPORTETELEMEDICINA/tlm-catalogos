package net.amentum.niomedic.catalogos.model;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Setter
@Getter
@Entity
@Table(name = "cat_app_configuration")
public class CatAppConfiguration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Integer idCliente;

    @Column(name = "cliente", nullable = false)
    @NotEmpty(message = "Debe agregar un nombre de cliente")
    private String cliente;

    @Column(name = "telefono", nullable = false)
    @NotEmpty(message = "Debe agregar un telefono")
    private String telefono;

    @Column(name = "telefono_emergencia")
    private String telefonoEmergencia;

    @Column(name = "url_agenda")
    private String urlAgenda;

    @Column(name = "urlvideollamadas")
    private String urlvideollamadas;

    @Column(name = "urlchat")
    private String urlchat;

    @Column(name = "urlmail")
    private String urlmail;

    @Column(name = "urlprivacidad")
    private String urlprivacidad;

    @Column(name = "urlsms")
    private String urlsms;

    @Column(name = "campo1")
    private String campo1;

    @Column(name = "campo2")
    private String campo2;

    @Column(name = "campo3")
    private String campo3;

    @Column(name = "campo4")
    private String campo4;

    @Override
    public String toString() {
        return "CatAppConfiguration{" +
                "id_cliente=" + idCliente +
                ", cliente='" + cliente + '\'' +
                ", telefono='" + telefono + '\'' +
                ", telefono_emergencia='" + telefonoEmergencia + '\'' +
                ", url_agenda='" + urlAgenda + '\'' +
                ", urlvideollamadas='" + urlvideollamadas + '\'' +
                ", urlchat='" + urlchat + '\'' +
                ", urlmail='" + urlmail + '\'' +
                ", urlprivacidad='" + urlprivacidad + '\'' +
                ", urlsms='" + urlsms + '\'' +
                ", campo1='" + campo1 + '\'' +
                ", campo2='" + campo2 + '\'' +
                ", campo3='" + campo3 + '\'' +
                ", campo4='" + campo4 + '\'' +
                '}';
    }
}
