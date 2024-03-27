package net.amentum.niomedic.catalogos.converter;
import net.amentum.niomedic.catalogos.model.CatSexo;
import net.amentum.niomedic.catalogos.views.CatSexoView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CatSexoConverter {
    private Logger logger = LoggerFactory.getLogger(CatSexoConverter.class);


    public CatSexoView toview(CatSexo catSexo, Boolean completeConversion) {
        CatSexoView catSexoView = new CatSexoView();

        catSexoView.setIdSexo(catSexo.getId_cat_sexo());
        catSexoView.setDescripcionSexo(catSexo.getDescripcion_sexo());

        logger.debug("convertir catSexo to View: {}", catSexoView);

        return catSexoView;

    }
}
