package net.amentum.niomedic.catalogos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data // @Getter, @Setter, @ToString, @EqualsAndHashCode and @RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cat_imc5_19_psp")
public class CatImc519PSP implements Serializable {

    @Id
    @Column(name = "id_cat_imc5_19")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_cat_imc5_19 ;

    @Size(max = 100)
    private String descripcion_imc5_19;
    
}
