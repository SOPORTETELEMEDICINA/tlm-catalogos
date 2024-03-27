package net.amentum.niomedic.catalogos.converter;
import net.amentum.niomedic.catalogos.model.CatEdasPlanTratamientoPSP;
import net.amentum.niomedic.catalogos.views.CatEdasPlanTratamientoView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CatEdasPlanTratamientoPSPConverter {
    private Logger logger = LoggerFactory.getLogger(CatEdasPlanTratamientoPSPConverter.class);


    public CatEdasPlanTratamientoView toview(CatEdasPlanTratamientoPSP catEdasPlanTratamiento, Boolean completeConversion) {
        CatEdasPlanTratamientoView catEdasPlanTratamientoView = new CatEdasPlanTratamientoView();

        catEdasPlanTratamientoView.setIdEdasPlanTratamiento(catEdasPlanTratamiento.getId_cat_edas_plan_tratamiento());
        catEdasPlanTratamientoView.setDescripcionEdasPlanTratamiento(catEdasPlanTratamiento.getDescripcion_edas_plan_tratamiento());

        logger.debug("convertir catEdasPlanTratamiento to View: {}", catEdasPlanTratamientoView);

        return catEdasPlanTratamientoView;

    }
}
