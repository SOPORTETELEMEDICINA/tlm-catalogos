package net.amentum.niomedic.catalogos.converter;
import net.amentum.niomedic.catalogos.model.CatRiesgosDetectadosPSP;
import net.amentum.niomedic.catalogos.views.CatRiesgosDetectadosView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CatRiesgosDetectadosPSPConverter {
    private Logger logger = LoggerFactory.getLogger(CatRiesgosDetectadosPSPConverter.class);


    public CatRiesgosDetectadosView toview(CatRiesgosDetectadosPSP catRiesgosDetectadosPSP, Boolean completeConversion) {
        CatRiesgosDetectadosView catRiesgosDetectadosView = new CatRiesgosDetectadosView();

        catRiesgosDetectadosView.setIdRiesgoDetectado(catRiesgosDetectadosPSP.getId_cat_riesgos_detectados());
        catRiesgosDetectadosView.setDescripcionRiesgoDetectado(catRiesgosDetectadosPSP.getDescripcion_riesgos_detectados());

        logger.debug("convertir catRiesgosDetectados to View: {}", catRiesgosDetectadosView);

        return catRiesgosDetectadosView;

    }
}
