package net.amentum.niomedic.catalogos.converter;
import net.amentum.niomedic.catalogos.model.CatGradoDificultad;
import net.amentum.niomedic.catalogos.views.CatGradoDificultadView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CatGradoDificultadConverter {
    private Logger logger = LoggerFactory.getLogger(CatGradoDificultadConverter.class);


    public CatGradoDificultadView toview(CatGradoDificultad catGradoDifucultad, Boolean completeConversion) {
        CatGradoDificultadView catGradoDifucultadView = new CatGradoDificultadView();

        catGradoDifucultadView.setIdGradoDificultad(catGradoDifucultad.getId_cat_grado_dificultad());
        catGradoDifucultadView.setDescripcionGradoDificultad(catGradoDifucultad.getDescripcion_grado_dificultad());

        logger.debug("convertir catGradoDificultad to View: {}", catGradoDifucultadView);

        return catGradoDifucultadView;

    }
}
