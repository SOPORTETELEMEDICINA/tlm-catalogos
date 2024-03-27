package net.amentum.niomedic.catalogos.service;

import net.amentum.niomedic.catalogos.exception.CatImc519PSPException;
import net.amentum.niomedic.catalogos.views.CatImc519View;

import java.util.List;

public interface CatImc519PSPService {

   CatImc519View getDetailsByidImc(Integer idImc519) throws CatImc519PSPException;

    List<CatImc519View> findAll() throws CatImc519PSPException;


}

