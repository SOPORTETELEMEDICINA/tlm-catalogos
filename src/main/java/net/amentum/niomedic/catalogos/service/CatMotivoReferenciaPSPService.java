package net.amentum.niomedic.catalogos.service;

import net.amentum.niomedic.catalogos.exception.CatMotivoReferenciaPSPException;
import net.amentum.niomedic.catalogos.views.CatMotivoReferenciaView;

import java.util.List;

public interface CatMotivoReferenciaPSPService {

   CatMotivoReferenciaView getDetailsByidMotivoReferencia(Integer idMotivoReferencia) throws CatMotivoReferenciaPSPException;

    List<CatMotivoReferenciaView> findAll() throws CatMotivoReferenciaPSPException;



}

