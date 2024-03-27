package net.amentum.niomedic.catalogos.service;

import net.amentum.niomedic.catalogos.exception.CatGpoTerapeuticoMedException;
import net.amentum.niomedic.catalogos.views.CatGpoTerapeuticoMedView;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CatGpoTerapeuticoMedService {
   CatGpoTerapeuticoMedView getDetailsByIdCatGpoTerapeuticoMed(Integer idCatGpoTerapeuticoMed) throws CatGpoTerapeuticoMedException;

   List<CatGpoTerapeuticoMedView> findAll() throws CatGpoTerapeuticoMedException;

   Page<CatGpoTerapeuticoMedView> getCatGpoTerapeuticoMedSearch(String datosBusqueda, Integer page, Integer size, String orderColumn, String orderType) throws CatGpoTerapeuticoMedException;
}
