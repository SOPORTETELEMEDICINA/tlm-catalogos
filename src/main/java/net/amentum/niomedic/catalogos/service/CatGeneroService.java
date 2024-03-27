package net.amentum.niomedic.catalogos.service;

import net.amentum.niomedic.catalogos.exception.CatGeneroException;
import net.amentum.niomedic.catalogos.views.CatGeneroView;

import java.util.List;

public interface CatGeneroService {

   CatGeneroView getDetailsByidGenero(Integer idGenero) throws CatGeneroException;

    List<CatGeneroView> findAll() throws CatGeneroException;


}

