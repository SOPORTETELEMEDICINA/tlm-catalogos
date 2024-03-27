package net.amentum.niomedic.catalogos.service;

import net.amentum.niomedic.catalogos.exception.CatSubtitulosCie10Exception;
import net.amentum.niomedic.catalogos.views.CatSubtitulosCie10View;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CatSubtitulosCie10Service {
   CatSubtitulosCie10View getDetailsByIdCatSubtitulosCie10(Integer idCatSubtitulosCie10) throws CatSubtitulosCie10Exception;

   List<CatSubtitulosCie10View> findAll() throws CatSubtitulosCie10Exception;

   Page<CatSubtitulosCie10View> getCatSubtitulosCie10Search(String datosBusqueda, Integer page, Integer size, String orderColumn, String orderType) throws CatSubtitulosCie10Exception;
}
