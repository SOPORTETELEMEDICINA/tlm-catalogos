package net.amentum.niomedic.catalogos.service;

import net.amentum.niomedic.catalogos.exception.CatEspecialidadesException;
import net.amentum.niomedic.catalogos.model.CatEspecialidades;
import net.amentum.niomedic.catalogos.views.CatEspecialidadesView;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CatEspecialidadesService {

    List<CatEspecialidades> getAllActiveEspecialidades();

    CatEspecialidadesView getDetailsByIdCatEspecialidades(Integer idCatEspecialidades) throws CatEspecialidadesException;

   List<CatEspecialidadesView> findAll() throws CatEspecialidadesException;

   Page<CatEspecialidadesView> getCatEspecialidadesSearch(String datosBusqueda, Boolean activo, Integer page, Integer size, String orderColumn, String orderType) throws CatEspecialidadesException;

   CatEspecialidadesView createCatEspecialidades(CatEspecialidadesView catEspecialidadesView) throws CatEspecialidadesException;

   CatEspecialidadesView updateCatEspecialidades(CatEspecialidadesView catEspecialidadesView) throws CatEspecialidadesException;
}
