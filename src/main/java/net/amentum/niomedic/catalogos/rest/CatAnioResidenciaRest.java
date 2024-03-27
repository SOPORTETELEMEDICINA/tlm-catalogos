package net.amentum.niomedic.catalogos.rest;

import net.amentum.common.BaseController;
import net.amentum.niomedic.catalogos.exception.CatAnioResidenciaException;
import net.amentum.niomedic.catalogos.views.CatAnioResidenciaView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
@RequestMapping("catalogo-anio-Residencia")
public class CatAnioResidenciaRest extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(CatAnioResidenciaRest.class);

    private net.amentum.niomedic.catalogos.service.CatAnioResidenciaService CatAnioResidenciaService;

    @Autowired
    public void setCatAnioResidenciaService(net.amentum.niomedic.catalogos.service.CatAnioResidenciaService CatAnioResidenciaService) {
        this.CatAnioResidenciaService = CatAnioResidenciaService;
    }
    @Value("${application.controller.direccion}")
    private String direccion;



    @RequestMapping(value = "{idAnioResidencia}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public CatAnioResidenciaView getDetailsByidAnioResidencia(@PathVariable() Integer idAnioResidencia) throws CatAnioResidenciaException {
        try {
            logger.info("===>>>Obtener los detalles del CatAnioResidencia por Id: {}", idAnioResidencia);
            return CatAnioResidenciaService.getDetailsByidAnio(idAnioResidencia);
        } catch (CatAnioResidenciaException cJor) {
            throw cJor;
        } catch (Exception ex) {
            CatAnioResidenciaException cJor = new CatAnioResidenciaException("No fue posible obtener los detalles del CatAnioResidencia por Id", CatAnioResidenciaException.LAYER_REST, CatAnioResidenciaException.ACTION_SELECT);
            logger.error("===>>>Error al obtener los detalles del CatAnioResidencia por Id- CODE: {} - ", cJor.getExceptionCode(), ex);
            throw cJor;
        }
    }

    @RequestMapping(value = "findAll", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<CatAnioResidenciaView> findAll() throws CatAnioResidenciaException {
        return CatAnioResidenciaService.findAll();
    }

    @RequestMapping(value = "search", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Page<CatAnioResidenciaView> getCatAnioResidenciaSearch(@RequestParam(required = false, defaultValue = "") String clave_anio_de_residencia,
                                                                          @RequestParam(required = false) Integer page,
                                                                          @RequestParam(required = false) Integer size,
                                                                          @RequestParam(required = false) String orderColumn,
                                                                          @RequestParam(required = false) String orderType) throws CatAnioResidenciaException {

        logger.info("===>>>getCatAnioResidenciaSearch(): - clave_anio_de_residencia: {} - page: {} - size: {} - orderColumn: {} - orderType: {}",
                clave_anio_de_residencia, page, size, orderColumn, orderType);

        if (page == null)
            page = 0;
        if (size == null)
            size = 30;
        if (orderType == null || orderType.isEmpty())
            orderType = "asc";
        if (orderColumn == null || orderColumn.isEmpty())
            orderColumn = "clave_anio_de_residencia";

        return CatAnioResidenciaService.getCatAnioResidenciaSearch(clave_anio_de_residencia, page, size, orderColumn, orderType);
    }


}
