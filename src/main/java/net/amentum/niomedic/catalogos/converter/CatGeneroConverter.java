package net.amentum.niomedic.catalogos.converter;
import net.amentum.niomedic.catalogos.model.CatGenero;
import net.amentum.niomedic.catalogos.views.CatGeneroView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CatGeneroConverter {
    private Logger logger = LoggerFactory.getLogger(CatGeneroConverter.class);


    public CatGeneroView toview(CatGenero catGenero, Boolean completeConversion) {
        CatGeneroView catGeneroView = new CatGeneroView();

        catGeneroView.setIdGenero(catGenero.getId_cat_genero());
        catGeneroView.setDescripcionGenero(catGenero.getDescripcion_genero());

        logger.debug("convertir catGenero to View: {}", catGeneroView);

        return catGeneroView;

    }
}
