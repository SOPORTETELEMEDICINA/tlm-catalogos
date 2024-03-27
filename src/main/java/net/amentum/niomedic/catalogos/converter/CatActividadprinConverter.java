package net.amentum.niomedic.catalogos.converter;

import net.amentum.niomedic.catalogos.model.CatActividadprin;
import net.amentum.niomedic.catalogos.views.CatActividadprinView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CatActividadprinConverter {

    private Logger logger = LoggerFactory.getLogger(CatActividadprinConverter.class);

    public CatActividadprinView toView(CatActividadprin catActividadprin, Boolean completeConversion){
        CatActividadprinView catActividadprinView = new CatActividadprinView();
        catActividadprinView.setActid(catActividadprin.getActid());
        catActividadprinView.setDescripcion(catActividadprin.getDescripcion());


        logger.debug("convertir catactividadprin to View: {}", catActividadprinView);
        return catActividadprinView;

    }

}
