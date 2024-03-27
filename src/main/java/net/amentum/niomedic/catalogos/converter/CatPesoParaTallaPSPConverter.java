package net.amentum.niomedic.catalogos.converter;
import net.amentum.niomedic.catalogos.model.CatPesoParaTallaPSP;
import net.amentum.niomedic.catalogos.views.CatPesoParaTallaView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CatPesoParaTallaPSPConverter {
    private Logger logger = LoggerFactory.getLogger(CatPesoParaTallaPSPConverter.class);


    public CatPesoParaTallaView toview(CatPesoParaTallaPSP catPesoParaTallaPSP, Boolean completeConversion) {
        CatPesoParaTallaView catPesoParaTallaView = new CatPesoParaTallaView();

        catPesoParaTallaView.setIdPesoParaTalla(catPesoParaTallaPSP.getId_cat_peso_para_talla());
        catPesoParaTallaView.setDescripcionPesoParaTalla(catPesoParaTallaPSP.getDescripcion_peso_para_talla());

        logger.debug("convertir catPesoParaTalla to View: {}", catPesoParaTallaView);

        return catPesoParaTallaView;

    }
}
