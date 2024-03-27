package net.amentum.niomedic.catalogos.service;

import net.amentum.niomedic.catalogos.exception.CatActividadprinException;
import net.amentum.niomedic.catalogos.views.CatActividadprinView;
import org.springframework.data.domain.Page;

import java.util.List;
public interface CatActividadprinService {
    CatActividadprinView getDetailsByactid(Integer actid) throws CatActividadprinException;

    List<CatActividadprinView> findAll() throws CatActividadprinException;

    Page<CatActividadprinView> getCatActividadprinSearch(String datosBusqueda, Integer page, Integer size, String orderColumn, String orderType) throws CatActividadprinException;


}
