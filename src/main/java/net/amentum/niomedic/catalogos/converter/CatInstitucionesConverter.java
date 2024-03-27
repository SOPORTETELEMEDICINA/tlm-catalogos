package net.amentum.niomedic.catalogos.converter;

import net.amentum.niomedic.catalogos.model.CatInstituciones;
import net.amentum.niomedic.catalogos.views.CatInstitucionesView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CatInstitucionesConverter {

    private Logger logger = LoggerFactory.getLogger(CatInstitucionesConverter.class);

    public CatInstitucionesView toView(CatInstituciones catInstituciones, Boolean completeConversion){
        CatInstitucionesView catInstitucionesView = new CatInstitucionesView();
        catInstitucionesView.setIdinstitucion(catInstituciones.getIdinstitucion());
        catInstitucionesView.setInstcve(catInstituciones.getInstcve());
        catInstitucionesView.setInstcvecorta(catInstituciones.getInstcvecorta());
        catInstitucionesView.setInstnombre(catInstituciones.getInstnombre());


        logger.debug("convertir catInstitucionesView to View: {}", catInstitucionesView);
        return catInstitucionesView;
    }

}
