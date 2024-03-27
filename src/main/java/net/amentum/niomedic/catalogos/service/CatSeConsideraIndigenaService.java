package net.amentum.niomedic.catalogos.service;

import net.amentum.niomedic.catalogos.exception.CatSeConsideraIndigenaException;
import net.amentum.niomedic.catalogos.views.CatSeConsideraIndigenaView;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CatSeConsideraIndigenaService {

   CatSeConsideraIndigenaView getDetailsByidSeConsideraIndigena(Integer idSeConsideraIndigena) throws CatSeConsideraIndigenaException;

    List<CatSeConsideraIndigenaView> findAll() throws CatSeConsideraIndigenaException;


}

