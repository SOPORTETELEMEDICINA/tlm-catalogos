package net.amentum.niomedic.catalogos.service;

import net.amentum.niomedic.catalogos.exception.CatMedicamentosException;
import net.amentum.niomedic.catalogos.views.CatMedicamentosView;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CatMedicamentosService {

   CatMedicamentosView getDetailsByIdCatMedicamentos(Integer idCatMedicamentos) throws CatMedicamentosException;

   List<CatMedicamentosView> findAll() throws CatMedicamentosException;

   Page<CatMedicamentosView> getCatMedicamentosSearch(String datosBusqueda, Boolean activo, Integer page, Integer size, String orderColumn, String orderType) throws CatMedicamentosException;

   CatMedicamentosView createCatMedicamentos(CatMedicamentosView catMedicamentosView) throws CatMedicamentosException;

   CatMedicamentosView updateCatMedicamentos(CatMedicamentosView catMedicamentosView) throws CatMedicamentosException;
}
