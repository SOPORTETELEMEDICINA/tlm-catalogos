package net.amentum.niomedic.catalogos.converter;

import net.amentum.niomedic.catalogos.model.CatPersonalmedico;
import net.amentum.niomedic.catalogos.views.CatPersonalmedicoView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CatPersonalmedicoConverter {

    private Logger logger = LoggerFactory.getLogger(CatPersonalmedicoConverter.class);

    public CatPersonalmedicoView toView(CatPersonalmedico catPersonalmedico, Boolean completeConversion){
        CatPersonalmedicoView catPersonalmedicoView = new CatPersonalmedicoView();
        catPersonalmedicoView.setPerid(catPersonalmedico.getPerid());
        catPersonalmedicoView.setDescripcion(catPersonalmedico.getDescripcion());

        logger.debug("convertir catPersonalmedico to View: {}", catPersonalmedicoView);
        return catPersonalmedicoView;
    }

}
