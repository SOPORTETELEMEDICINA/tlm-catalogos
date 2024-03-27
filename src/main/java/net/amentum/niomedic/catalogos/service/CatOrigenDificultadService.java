package net.amentum.niomedic.catalogos.service;

import net.amentum.niomedic.catalogos.exception.CatOrigenDificultadException;
import net.amentum.niomedic.catalogos.views.CatOrigenDificultadView;

import java.util.List;

public interface CatOrigenDificultadService {

   CatOrigenDificultadView getDetailsByidOrigenDificultad(Integer idOrigenDificultad) throws CatOrigenDificultadException;

    List<CatOrigenDificultadView> findAll() throws CatOrigenDificultadException;


}

