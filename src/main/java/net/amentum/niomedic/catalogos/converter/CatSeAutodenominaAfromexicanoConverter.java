package net.amentum.niomedic.catalogos.converter;
import net.amentum.niomedic.catalogos.model.CatSeAutodenominaAfromexicano;
import net.amentum.niomedic.catalogos.views.CatSeAutodenominaAfromexicanoView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CatSeAutodenominaAfromexicanoConverter {
    private Logger logger = LoggerFactory.getLogger(CatSeAutodenominaAfromexicanoConverter.class);


    public CatSeAutodenominaAfromexicanoView toview(CatSeAutodenominaAfromexicano catSeAutodenominaAfromexicano, Boolean completeConversion) {
        CatSeAutodenominaAfromexicanoView catSeAutodenominaAfromexicanoView = new CatSeAutodenominaAfromexicanoView();

        catSeAutodenominaAfromexicanoView.setIdSeAutodenominaAfromexicano(catSeAutodenominaAfromexicano.getId_cat_se_autodenomina_afromexicano());
        catSeAutodenominaAfromexicanoView.setDescripcionSeAutodenominaAfromexicano(catSeAutodenominaAfromexicano.getDescripcion_se_autodenomina_afromexicano());

        logger.debug("convertir catSeAutodenominaAfromexicano to View: {}", catSeAutodenominaAfromexicanoView);

        return catSeAutodenominaAfromexicanoView;

    }
}
