package net.amentum.niomedic.catalogos.converter;
import net.amentum.niomedic.catalogos.model.CatSeConsideraIndigena;
import net.amentum.niomedic.catalogos.views.CatSeConsideraIndigenaView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CatSeConsideraIndigenaConverter {
    private Logger logger = LoggerFactory.getLogger(CatSeConsideraIndigenaConverter.class);


    public CatSeConsideraIndigenaView toview(CatSeConsideraIndigena catSeConsideraIndigena, Boolean completeConversion) {
        CatSeConsideraIndigenaView catSeConsideraIndigenaView = new CatSeConsideraIndigenaView();

        catSeConsideraIndigenaView.setIdSeConsideraIndigena(catSeConsideraIndigena.getId_cat_se_considera_indigena());
        catSeConsideraIndigenaView.setDescripcionSeConsideraIndigena(catSeConsideraIndigena.getDescripcion_se_considera_indigena());

        logger.debug("convertir catseConsideraIndigena to View: {}", catSeConsideraIndigenaView);

        return catSeConsideraIndigenaView;

    }
}
