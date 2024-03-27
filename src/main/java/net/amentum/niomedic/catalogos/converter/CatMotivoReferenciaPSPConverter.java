package net.amentum.niomedic.catalogos.converter;
import net.amentum.niomedic.catalogos.model.CatMotivoReferenciaPSP;
import net.amentum.niomedic.catalogos.views.CatMotivoReferenciaView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CatMotivoReferenciaPSPConverter {
    private Logger logger = LoggerFactory.getLogger(CatMotivoReferenciaPSPConverter.class);


    public CatMotivoReferenciaView toview(CatMotivoReferenciaPSP catMotivoReferenciaPSP, Boolean completeConversion) {
        CatMotivoReferenciaView catMotivoReferenciaView = new CatMotivoReferenciaView();

        catMotivoReferenciaView.setIdMotivoReferencia(catMotivoReferenciaPSP.getId_cat_motivo_de_referencia());
        catMotivoReferenciaView.setDescripcionMotivoReferencia(catMotivoReferenciaPSP.getDescripcion_motivo_de_referencia());

        logger.debug("convertir catMotivoReferencia to View: {}", catMotivoReferenciaView);

        return catMotivoReferenciaView;

    }
}
