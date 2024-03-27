package net.amentum.niomedic.catalogos.converter;

import net.amentum.niomedic.catalogos.model.CatAreatrabajo;
import net.amentum.niomedic.catalogos.views.CatAreatrabajoView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CatAreatrabajoConverter {

    private Logger logger = LoggerFactory.getLogger(CatAreatrabajoConverter.class);

    public CatAreatrabajoView toView(CatAreatrabajo catAreatrabajo, Boolean completeConversion){
        CatAreatrabajoView catAreatrabajoView = new CatAreatrabajoView();
        catAreatrabajoView.setAtrid(catAreatrabajo.getAtrid());
        catAreatrabajoView.setNombre(catAreatrabajo.getNombre());

        logger.debug("convertir catDolometro to View: {}", catAreatrabajoView);
        return catAreatrabajoView;
    }
}
