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
@Table(name = "Cat_area_trabajo")


public class CatAreatrabajo implements Serializable {
    @Id
    @Column(name = "atr_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer atrid;
    @Size(max = 30)
    private String nombre;
}
