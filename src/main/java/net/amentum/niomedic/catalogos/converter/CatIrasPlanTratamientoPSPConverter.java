package net.amentum.niomedic.catalogos.converter;
import net.amentum.niomedic.catalogos.model.CatIrasPlanTratamientoPSP;
import net.amentum.niomedic.catalogos.views.CatIrasPlanTratamientoView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CatIrasPlanTratamientoPSPConverter {
    private Logger logger = LoggerFactory.getLogger(CatIrasPlanTratamientoPSPConverter.class);


    public CatIrasPlanTratamientoView toview(CatIrasPlanTratamientoPSP catIrasPlanTratamientoPSP, Boolean completeConversion) {
        CatIrasPlanTratamientoView catIrasPlanTratamientoView = new CatIrasPlanTratamientoView();

        catIrasPlanTratamientoView.setIdIrasPlanTratamiento(catIrasPlanTratamientoPSP.getId_cat_iras_plan_tratamiento());
        catIrasPlanTratamientoView.setDescripcionIrasPlanTratamiento(catIrasPlanTratamientoPSP.getDescripcion_iras_plan_tratamiento());

        logger.debug("convertir catIrasPlanTratamiento to View: {}", catIrasPlanTratamientoView);

        return catIrasPlanTratamientoView;

    }
}
