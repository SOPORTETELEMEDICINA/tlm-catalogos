package net.amentum.niomedic.catalogos.service;

import net.amentum.niomedic.catalogos.exception.CatNumerosEmergenciaException;
import net.amentum.niomedic.catalogos.views.CatNumerosEmergenciaView;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CatNumerosEmergenciaService {
   CatNumerosEmergenciaView getDetailsByIdCatNumerosEmergencia(Integer idCatNumerosEmergencia) throws CatNumerosEmergenciaException;

   List<CatNumerosEmergenciaView> findAll() throws CatNumerosEmergenciaException;

   Page<CatNumerosEmergenciaView> getCatNumerosEmergenciaSearch(String datosBusqueda, Integer page, Integer size, String orderColumn, String orderType) throws CatNumerosEmergenciaException;
}
