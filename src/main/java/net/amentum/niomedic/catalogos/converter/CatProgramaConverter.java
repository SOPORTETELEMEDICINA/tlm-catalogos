package net.amentum.niomedic.catalogos.converter;
import net.amentum.niomedic.catalogos.model.CatPrograma;
import net.amentum.niomedic.catalogos.views.CatProgramaView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CatProgramaConverter {
    private Logger logger = LoggerFactory.getLogger(CatProgramaConverter.class);


    public CatProgramaView toview(CatPrograma catOrigenDifucultad, Boolean completeConversion) {
        CatProgramaView catOrigenDifucultadView = new CatProgramaView();

        catOrigenDifucultadView.setIdPrograma(catOrigenDifucultad.getId_cat_programa());
        catOrigenDifucultadView.setDescripcionPrograma(catOrigenDifucultad.getDescripcion_programa());

        logger.debug("convertir catPrograma to View: {}", catOrigenDifucultadView);

        return catOrigenDifucultadView;

    }
}
