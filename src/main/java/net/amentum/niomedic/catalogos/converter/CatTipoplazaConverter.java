package net.amentum.niomedic.catalogos.converter;

import net.amentum.niomedic.catalogos.model.CatTipoplaza;
import net.amentum.niomedic.catalogos.views.CatTipoplazaView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

@Component
public class CatTipoplazaConverter {

    private Logger logger = LoggerFactory.getLogger(CatTipoplazaConverter.class);

    public CatTipoplazaView toView(CatTipoplaza catTipoplaza, Boolean completeConversion) {
        CatTipoplazaView catTipoplazaView = new CatTipoplazaView();
        catTipoplazaView.setPlaid(catTipoplaza.getPlaid());
        catTipoplazaView.setTipo(catTipoplaza.getTipo());


        logger.debug("convertir catTipoplaza to View: {}", catTipoplazaView);
        return catTipoplazaView;

    }
}
