package net.amentum.niomedic.catalogos.service;

import net.amentum.niomedic.catalogos.exception.CatResultadoEdiPSPException;
import net.amentum.niomedic.catalogos.views.CatResultadoEdiView;

import java.util.List;

public interface CatResultadoEdiPSPService {

   CatResultadoEdiView getDetailsByidEdi(Integer idResultadoEdi) throws CatResultadoEdiPSPException;

    List<CatResultadoEdiView> findAll() throws CatResultadoEdiPSPException;



}

