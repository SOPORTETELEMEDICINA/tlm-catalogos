package net.amentum.niomedic.catalogos.converter;
import net.amentum.niomedic.catalogos.model.CatOrigenDificultad;
import net.amentum.niomedic.catalogos.views.CatOrigenDificultadView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CatOrigenDificultadConverter {
    private Logger logger = LoggerFactory.getLogger(CatOrigenDificultadConverter.class);


    public CatOrigenDificultadView toview(CatOrigenDificultad catOrigenDifucultad, Boolean completeConversion) {
        CatOrigenDificultadView catOrigenDifucultadView = new CatOrigenDificultadView();

        catOrigenDifucultadView.setIdOrigenDificultad(catOrigenDifucultad.getId_cat_origen_dificultad());
        catOrigenDifucultadView.setDescripcionOrigenDificultad(catOrigenDifucultad.getDescripcion_origen_dificultad());

        logger.debug("convertir catOrigenDificultad to View: {}", catOrigenDifucultadView);

        return catOrigenDifucultadView;

    }
}
