package net.amentum.niomedic.catalogos.service;

import net.amentum.niomedic.catalogos.exception.CatMotivosEnvioException;
import net.amentum.niomedic.catalogos.views.CatMotivosEnvioView;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CatMotivosEnvioService {

   CatMotivosEnvioView getDetailsByIdCatMotivosEnvio(Integer idCatMotivosEnvio) throws CatMotivosEnvioException;

   List<CatMotivosEnvioView> findAll() throws CatMotivosEnvioException;

   Page<CatMotivosEnvioView> getCatMotivosEnvioSearch(String datosBusqueda, Boolean activo, Integer page, Integer size, String orderColumn, String orderType) throws CatMotivosEnvioException;

   CatMotivosEnvioView createCatMotivosEnvio(CatMotivosEnvioView catMotivosEnvioView) throws CatMotivosEnvioException;

   CatMotivosEnvioView updateCatMotivosEnvio(CatMotivosEnvioView catMotivosEnvioView) throws CatMotivosEnvioException;
}
