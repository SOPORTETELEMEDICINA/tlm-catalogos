package net.amentum.niomedic.catalogos.converter;
import net.amentum.niomedic.catalogos.model.CatAnioResidencia;
import net.amentum.niomedic.catalogos.views.CatAnioResidenciaView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CatAnioResidenciaConverter {
    private Logger logger = LoggerFactory.getLogger(CatAnioResidenciaConverter.class);


    public CatAnioResidenciaView toview(CatAnioResidencia catAnioResidencia, Boolean completeConversion) {
        CatAnioResidenciaView catAnioResidenciaView = new CatAnioResidenciaView();

        catAnioResidenciaView.setIdAnioResidencia(catAnioResidencia.getId_cat_anio_de_residencia());
        catAnioResidenciaView.setDescripcionAnioResidencia(catAnioResidencia.getClave_anio_de_residencia());

        logger.debug("convertir catAnioResidencia to View: {}", catAnioResidenciaView);

        return catAnioResidenciaView;

    }
}
