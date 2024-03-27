package net.amentum.niomedic.catalogos.converter;
import net.amentum.niomedic.catalogos.model.CatTipoDificultad;
import net.amentum.niomedic.catalogos.views.CatTipoDificultadView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CatTipoDificultadConverter {
    private Logger logger = LoggerFactory.getLogger(CatTipoDificultadConverter.class);


    public CatTipoDificultadView toview(CatTipoDificultad catTipoDifucultad, Boolean completeConversion) {
        CatTipoDificultadView catTipoDifucultadView = new CatTipoDificultadView();

        catTipoDifucultadView.setIdTipoDificultad(catTipoDifucultad.getId_cat_tipo_dificultad());
        catTipoDifucultadView.setDescripcionTipoDificultad(catTipoDifucultad.getDescripcion_tipo_dificultad());

        logger.debug("convertir catTipoDificultad to View: {}", catTipoDifucultadView);

        return catTipoDifucultadView;

    }
}
