package net.amentum.niomedic.catalogos.service;

import net.amentum.niomedic.catalogos.exception.CatResultadoBatellePSPException;
import net.amentum.niomedic.catalogos.views.CatResultadoBatelleView;

import java.util.List;

public interface CatResultadoBatellePSPService {

   CatResultadoBatelleView getDetailsByidBatelle(Integer idResultadoBatelle) throws CatResultadoBatellePSPException;

    List<CatResultadoBatelleView> findAll() throws CatResultadoBatellePSPException;


}

