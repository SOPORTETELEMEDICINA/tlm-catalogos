package net.amentum.niomedic.catalogos.service;

import net.amentum.niomedic.catalogos.exception.CatPesoParaTallaPSPException;
import net.amentum.niomedic.catalogos.views.CatPesoParaTallaView;

import java.util.List;

public interface CatPesoParaTallaPSPService {

   CatPesoParaTallaView getDetailsByidPeso(Integer idPesoParaTalla) throws CatPesoParaTallaPSPException;

    List<CatPesoParaTallaView> findAll() throws CatPesoParaTallaPSPException;



}

