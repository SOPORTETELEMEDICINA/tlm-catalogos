package net.amentum.niomedic.catalogos.rest;

import net.amentum.common.BaseController;
import net.amentum.niomedic.catalogos.exception.CatMotivoReferenciaPSPException;
import net.amentum.niomedic.catalogos.service.CatMotivoReferenciaPSPService;
import net.amentum.niomedic.catalogos.views.CatMotivoReferenciaView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
@RequestMapping("motivos-referencia")
public class CatMotivoReferenciaPSPRest extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(CatMotivoReferenciaPSPRest.class);

    private CatMotivoReferenciaPSPService CatMotivoReferenciaPSPService;

    @Autowired
    public void setCatMotivoReferenciaService(CatMotivoReferenciaPSPService CatMotivoReferenciaPSPService) {
        this.CatMotivoReferenciaPSPService = CatMotivoReferenciaPSPService;
    }
    @Value("${application.controller.direccion}")
    private String direccion;



    @RequestMapping(value = "{idMotivoReferencia}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public CatMotivoReferenciaView getDetailsByidMotivoReferencia(@PathVariable() Integer idMotivoReferencia) throws CatMotivoReferenciaPSPException {
        try {
            logger.info("===>>>Obtener los detalles del CatMotivoReferencia por Id: {}", idMotivoReferencia);
            return CatMotivoReferenciaPSPService.getDetailsByidMotivoReferencia(idMotivoReferencia);
        } catch (CatMotivoReferenciaPSPException cJor) {
            throw cJor;
        } catch (Exception ex) {
            CatMotivoReferenciaPSPException cJor = new CatMotivoReferenciaPSPException("No fue posible obtener los detalles del CatMotivoReferencia por Id", CatMotivoReferenciaPSPException.LAYER_REST, CatMotivoReferenciaPSPException.ACTION_SELECT);
            logger.error("===>>>Error al obtener los detalles del CatMotivoReferencia por Id- CODE: {} - ", cJor.getExceptionCode(), ex);
            throw cJor;
        }
    }

    @RequestMapping(value = "findAll", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<CatMotivoReferenciaView> findAll() throws CatMotivoReferenciaPSPException {
        return CatMotivoReferenciaPSPService.findAll();
    }




}
