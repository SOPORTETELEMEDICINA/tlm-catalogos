package net.amentum.niomedic.catalogos.converter;
import net.amentum.niomedic.catalogos.model.CatResultadoEdiPSP;
import net.amentum.niomedic.catalogos.views.CatResultadoEdiView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CatResultadoEdiPSPConverter {
    private Logger logger = LoggerFactory.getLogger(CatResultadoEdiPSPConverter.class);


    public CatResultadoEdiView toview(CatResultadoEdiPSP catResultadoEdiPSP, Boolean completeConversion) {
        CatResultadoEdiView catResultadoEdiView = new CatResultadoEdiView();

        catResultadoEdiView.setIdResultadoEdi(catResultadoEdiPSP.getId_cat_resultado_edi());
        catResultadoEdiView.setDescripcionResultadoEdi(catResultadoEdiPSP.getDescripcion_resultado_edi());
        catResultadoEdiView.setClaveEdi(catResultadoEdiPSP.getClave_edi());


        logger.debug("convertir catResultadoEdi to View: {}", catResultadoEdiView);

        return catResultadoEdiView;

    }
}
