package net.amentum.niomedic.catalogos.converter;
import net.amentum.niomedic.catalogos.model.CatImc519PSP;
import net.amentum.niomedic.catalogos.views.CatImc519View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CatImc519PSPConverter {
    private Logger logger = LoggerFactory.getLogger(CatImc519PSPConverter.class);


    public CatImc519View toview(CatImc519PSP catImc519PSP, Boolean completeConversion) {
        CatImc519View catImc519View = new CatImc519View();

        catImc519View.setIdImc519(catImc519PSP.getId_cat_imc5_19());
        catImc519View.setDescripcionImc519(catImc519PSP.getDescripcion_imc5_19());

        logger.debug("convertir catImc519 to View: {}", catImc519View);

        return catImc519View;

    }
}
