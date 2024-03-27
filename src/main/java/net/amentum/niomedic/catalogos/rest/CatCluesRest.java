package net.amentum.niomedic.catalogos.rest;

import net.amentum.common.BaseController;
import net.amentum.niomedic.catalogos.exception.CatCluesException;
import net.amentum.niomedic.catalogos.service.CatCluesService;
import net.amentum.niomedic.catalogos.views.CatCluesView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
@RequestMapping("catalogo-Clues")


public class CatCluesRest extends BaseController  {

    private final Logger logger = LoggerFactory.getLogger(CatCluesRest.class);

    private CatCluesService CatCluesService;

    @Autowired
    public void setCatCluesService(CatCluesService CatCluesService) {
        this.CatCluesService = CatCluesService;
    }

    @RequestMapping(value = "{idCatClues}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)

    public CatCluesView getDetailsByidCatClues(@PathVariable() Integer idCatClues) throws CatCluesException {
        try {
            logger.info("===>>>Obtener los detalles del CatClues por Id: {}", idCatClues);
            return CatCluesService.getDetailsByidCatClues(idCatClues);
        } catch (CatCluesException CActp) {
            throw CActp;
        } catch (Exception ex) {
            CatCluesException CActp = new CatCluesException("No fue posible obtener los detalles del CatClues por Id", CatCluesException.LAYER_REST, CatCluesException.ACTION_SELECT);
            logger.error("===>>>Error al obtener los detalles del CatClues por Id- CODE: {} - ", CActp.getExceptionCode(), ex);
            throw CActp;
        }
    }

    @RequestMapping(value = "findAll", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<CatCluesView> findAll() throws CatCluesException {
        return CatCluesService.findAll();
    }

    @RequestMapping(value = "search", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Page<CatCluesView> getCatCluesSearch(@RequestParam(required = false) String fkEntidad,
                                                @RequestParam(required = false) String claveInstitucion) throws CatCluesException {

        logger.info("===>>>getCatCluesSearch(): - datosBusqueda: {} - page: {} - size: {} - orderColumn: {} - orderType: {}",
                fkEntidad,claveInstitucion);



        return CatCluesService.getCatCluesSearch(fkEntidad,claveInstitucion);
    }


}
