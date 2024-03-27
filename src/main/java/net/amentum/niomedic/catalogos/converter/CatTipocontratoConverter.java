package net.amentum.niomedic.catalogos.converter;
import net.amentum.niomedic.catalogos.model.CatTipocontrato;
import net.amentum.niomedic.catalogos.views.CatTipocontratoView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
@Component
public class CatTipocontratoConverter {
private Logger logger = LoggerFactory.getLogger(CatTipocontratoConverter.class);

    public CatTipocontratoView toView(CatTipocontrato catTipocontrato, Boolean completeConversion){
        CatTipocontratoView catTipocontratoView = new CatTipocontratoView();
        catTipocontratoView.setConid(catTipocontrato.getConid());
        catTipocontratoView.setTipo(catTipocontrato.getTipo());


        logger.debug("convertir catTipocontrato to View: {}", catTipocontratoView);
        return catTipocontratoView;

}
}
