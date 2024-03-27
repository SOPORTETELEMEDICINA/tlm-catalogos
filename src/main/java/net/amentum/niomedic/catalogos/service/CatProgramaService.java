package net.amentum.niomedic.catalogos.service;

import net.amentum.niomedic.catalogos.exception.CatProgramaException;
import net.amentum.niomedic.catalogos.views.CatProgramaView;

import java.util.List;

public interface CatProgramaService {

   CatProgramaView getDetailsByidPrograma(Integer idPrograma) throws CatProgramaException;

    List<CatProgramaView> findAll() throws CatProgramaException;


}

