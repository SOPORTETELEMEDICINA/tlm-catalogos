package net.amentum.niomedic.catalogos.converter;
import net.amentum.niomedic.catalogos.model.CatServicioAtencion;
import net.amentum.niomedic.catalogos.views.CatServicioAtencionView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CatServicioAtencionConverter {
    private Logger logger = LoggerFactory.getLogger(CatServicioAtencionConverter.class);


    public CatServicioAtencionView toview(CatServicioAtencion catServicioAtencion, Boolean completeConversion) {
        CatServicioAtencionView catServicioAtencionView = new CatServicioAtencionView();

        catServicioAtencionView.setIdServicioAtencion(catServicioAtencion.getId_cat_servicio_atencion());
        catServicioAtencionView.setDescripcionServicioAtencion(catServicioAtencion.getDescripcion_servicio_atencion());

        logger.debug("convertir catServicioAtencion to View: {}", catServicioAtencionView);

        return catServicioAtencionView;

    }
}
