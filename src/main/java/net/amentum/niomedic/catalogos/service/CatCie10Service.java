package net.amentum.niomedic.catalogos.service;

import net.amentum.niomedic.catalogos.exception.CatCie10Exception;
import net.amentum.niomedic.catalogos.views.CatCie10View;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CatCie10Service {
   CatCie10View getDetailsByIdCatCie10(Integer idCatCie10) throws CatCie10Exception;

   List<CatCie10View> findAll() throws CatCie10Exception;

   Page<CatCie10View> getCatCie10Search(String datosBusqueda, Integer page, Integer size, String orderColumn, String orderType) throws CatCie10Exception;
}
