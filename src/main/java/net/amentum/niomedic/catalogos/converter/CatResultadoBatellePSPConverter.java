package net.amentum.niomedic.catalogos.converter;
import net.amentum.niomedic.catalogos.model.CatResultadoBatellePSP;
import net.amentum.niomedic.catalogos.views.CatResultadoBatelleView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CatResultadoBatellePSPConverter {
    private Logger logger = LoggerFactory.getLogger(CatResultadoBatellePSPConverter.class);


    public CatResultadoBatelleView toview(CatResultadoBatellePSP catResultadoBatellePSP, Boolean completeConversion) {
        CatResultadoBatelleView catResultadoBatelleView = new CatResultadoBatelleView();

        catResultadoBatelleView.setIdResultadoBatelle(catResultadoBatellePSP.getId_cat_resultado_batelle());
        catResultadoBatelleView.setDescripcionResultadoBatelle(catResultadoBatellePSP.getDescripcion_resultado_batelle());

        logger.debug("convertir catResultadoBatelle to View: {}", catResultadoBatelleView);

        return catResultadoBatelleView;

    }
}
