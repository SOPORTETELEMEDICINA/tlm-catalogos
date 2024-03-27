package net.amentum.niomedic.catalogos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data // @Getter, @Setter, @ToString, @EqualsAndHashCode and @RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cat_numeros_emergencia")
public class CatNumerosEmergencia implements Serializable {
   @Id
   @Column(name = "id_cat_numeros_emergencia")
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer idCatNumerosEmergencia;
   @Size(max = 20)
   private String numeroTelefonico;
   @Size(max = 20)
   private String numeroEmergenciaDescripcion;
   private String datosBusqueda;
}
