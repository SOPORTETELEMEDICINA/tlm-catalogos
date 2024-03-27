package net.amentum.niomedic.catalogos.converter;
import net.amentum.niomedic.catalogos.model.CatJornada;
import net.amentum.niomedic.catalogos.views.CatJornadaView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CatJornadaConverter {

    private Logger logger = LoggerFactory.getLogger(CatJornadaConverter.class);

    public CatJornadaView toView(CatJornada catJornada, Boolean completeConversion){
        CatJornadaView catJornadaView = new CatJornadaView();
        catJornadaView.setJorid(catJornada.getJorid());
        catJornadaView.setDescripcion(catJornada.getDescripcion());


        logger.debug("convertir catJornada to View: {}", catJornadaView);
        return catJornadaView;
    }


}
