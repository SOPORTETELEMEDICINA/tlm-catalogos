package net.amentum.niomedic.catalogos.catMedicamentos;

import com.fasterxml.jackson.core.type.TypeReference;
import net.amentum.niomedic.catalogos.ConfigurationTest;
import net.amentum.niomedic.catalogos.views.CatMedicamentosView;
import org.junit.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class CatMedicamentosTest extends ConfigurationTest {

   @Test
   public void getDetailsByIdCatMedicamentos() throws Exception {
//      mvn clean test -Dtest=CatMedicamentosTest#getDetailsByIdCatMedicamentos test
      mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-medicamentos/3"))
         .andExpect(MockMvcResultMatchers.status().isOk())
         .andDo(MockMvcResultHandlers.print());
      System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>OK");
   }

   @Test
   public void getDetailsByIdCatMedicamentosBAD() throws Exception {
//      mvn clean test -Dtest=CatMedicamentosTest#getDetailsByIdCatMedicamentosBAD test
      mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-medicamentos/9999999"))
         .andExpect(MockMvcResultMatchers.status().isNotFound())
         .andDo(MockMvcResultHandlers.print());
      System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>404");

      mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-medicamentos/9a"))
         .andExpect(MockMvcResultMatchers.status().isBadRequest())
         .andDo(MockMvcResultHandlers.print());
      System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>400");
   }


   @Test
   public void findAll() throws Exception {
//      mvn clean test -Dtest=CatMedicamentosTest#findAll test
      mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-medicamentos/findAll"))
         .andExpect(MockMvcResultMatchers.status().isOk())
         .andDo(MockMvcResultHandlers.print());
   }

   @Test
   public void getCatMedicamentosSearch() throws Exception {
//      mvn clean test -Dtest=CatMedicamentosTest#getCatMedicamentosSearch test
      mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-medicamentos/search")
         .contentType(JSON))
         .andExpect(MockMvcResultMatchers.status().isOk())
         .andDo(MockMvcResultHandlers.print());
//
      mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-medicamentos/search?datosBusqueda=MEDICINA&activo=false&page=1&size=10&orderColumn=&orderType=asc")
         .contentType(JSON))
         .andExpect(MockMvcResultMatchers.status().isOk())
         .andDo(MockMvcResultHandlers.print());

      mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-medicamentos/search?activo=false")
         .contentType(JSON))
         .andExpect(MockMvcResultMatchers.status().isOk())
         .andDo(MockMvcResultHandlers.print());

      mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-medicamentos/search?orderColumn=idCif")
         .contentType(JSON))
         .andExpect(MockMvcResultMatchers.status().isOk())
         .andDo(MockMvcResultHandlers.print());

      mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-medicamentos/search?activo=false&orderType=desc")
         .contentType(JSON))
         .andExpect(MockMvcResultMatchers.status().isOk())
         .andDo(MockMvcResultHandlers.print());

   }

   @Test
   public void getCatMedicamentosSearchBAD() throws Exception {
//      mvn clean test -Dtest=CatMedicamentosTest#getCatMedicamentosSearchBAD test
      mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-medicamentos/search?datosBusqueda=&activo=&page=-1&size=-10&orderColumn=ERRORNADA&orderType=ascdesc")
         .contentType(JSON))
         .andExpect(MockMvcResultMatchers.status().isOk())
         .andDo(MockMvcResultHandlers.print());

      mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-medicamentos/search?activo=treu")
         .contentType(JSON))
         .andExpect(MockMvcResultMatchers.status().isBadRequest())
         .andDo(MockMvcResultHandlers.print());

   }

   @Test
   public void createCatMedicamentos() throws Exception {
//      mvn clean test -Dtest=CatMedicamentosTest#createCatMedicamentos test
      CatMedicamentosView view = new CatMedicamentosView();
      view.setCveCodigo("CveCodigo");
      view.setSubCveCodigo("SubCveCodigo");
      view.setNombreGenerico("NombreGenerico");
      view.setFormaFarmaceutica("FormaFarmaceutica");
      view.setConcentracion("Concentracion");
      view.setPresentacion("Presentacion");
      view.setPrincipalIndicaciones("PrincipalIndicaciones");
      view.setDemasIndicaciones("DemasIndicaciones");
      view.setTipoActualizacion("TipoActualizacion");
      view.setNumActualizacion("NumActualizacion");
      view.setDescripcionCompleta("DescripcionCompleta");
      view.setCatTipoInsumoId(3);
      view.setCatCuadroIoId(3);
      view.setCatGpoTerapeuticoId(3);
      view.setActivo(false);

      mockMvc.perform(MockMvcRequestBuilders.post("/catalogo-medicamentos")
         .contentType(JSON)
         .content(MAPPER.writeValueAsString(view)))
         .andExpect(MockMvcResultMatchers.status().isCreated())
         .andDo(MockMvcResultHandlers.print());
      System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>OK");
   }

   @Test
   public void createCatMedicamentosBAD() throws Exception {
//      mvn clean test -Dtest=CatMedicamentosTest#createCatMedicamentosBAD test
      CatMedicamentosView view = new CatMedicamentosView();

      mockMvc.perform(MockMvcRequestBuilders.post("/catalogo-medicamentos")
         .contentType(JSON)
         .content(MAPPER.writeValueAsString(view)))
         .andExpect(MockMvcResultMatchers.status().isBadRequest())
         .andDo(MockMvcResultHandlers.print());
      System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>400");
   }

   @Test
   public void updateCatMedicamentos() throws Exception {
//      mvn clean test -Dtest=CatMedicamentosTest#updateCatMedicamentos test
      CatMedicamentosView view = createRecordCatMedicamentos();
      view.setCveCodigo("UPD-CveCodigo");
      view.setSubCveCodigo("UPD-SubCveCodigo");
      view.setNombreGenerico("UPD-NombreGenerico");
      view.setFormaFarmaceutica("UPD-FormaFarmaceutica");
      view.setConcentracion("UPD-Concentracion");
      view.setPresentacion("UPD-Presentacion");
      view.setPrincipalIndicaciones("UPD-PrincipalIndicaciones");
      view.setDemasIndicaciones("UPD-DemasIndicaciones");
      view.setTipoActualizacion("UPD-TipoActualizacion");
      view.setNumActualizacion("UPD-NumActualizacion");
      view.setDescripcionCompleta("UPD-DescripcionCompleta");
      view.setCatTipoInsumoId(1);
      view.setCatCuadroIoId(2);
      view.setCatGpoTerapeuticoId(3);
      view.setActivo(true);

      mockMvc.perform(MockMvcRequestBuilders.put("/catalogo-medicamentos/{idCatMedicamentos}", view.getIdCatMedicamentos())
         .contentType(JSON)
         .content(MAPPER.writeValueAsString(view)))
         .andExpect(MockMvcResultMatchers.status().isOk())
         .andDo(MockMvcResultHandlers.print());
      System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>OK");

   }

   @Test
   public void updateCatMedicamentosBAD() throws Exception {
//      mvn clean test -Dtest=CatMedicamentosTest#updateCatMedicamentosBAD test
      CatMedicamentosView view = createRecordCatMedicamentos();

      mockMvc.perform(MockMvcRequestBuilders.put("/catalogo-medicamentos/{idCatMedicamentos}",view.getIdCatMedicamentos())
         .contentType(JSON)
         .content(MAPPER.writeValueAsString(new CatMedicamentosView())))
         .andExpect(MockMvcResultMatchers.status().isBadRequest())
         .andDo(MockMvcResultHandlers.print());
      System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>400");

      mockMvc.perform(MockMvcRequestBuilders.put("/catalogo-medicamentos/{idCatMedicamentos}", view.getIdCatMedicamentos() * 9)
         .contentType(JSON)
         .content(MAPPER.writeValueAsString(view)))
         .andExpect(MockMvcResultMatchers.status().isNotFound())
         .andDo(MockMvcResultHandlers.print());
      System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>404");

   }

   public CatMedicamentosView createRecordCatMedicamentos() throws Exception {

      CatMedicamentosView view = new CatMedicamentosView();
      view.setCveCodigo("CveCodigo");
      view.setSubCveCodigo("SubCveCodigo");
      view.setNombreGenerico("NombreGenerico");
      view.setFormaFarmaceutica("FormaFarmaceutica");
      view.setConcentracion("Concentracion");
      view.setPresentacion("Presentacion");
      view.setPrincipalIndicaciones("PrincipalIndicaciones");
      view.setDemasIndicaciones("DemasIndicaciones");
      view.setTipoActualizacion("TipoActualizacion");
      view.setNumActualizacion("NumActualizacion");
      view.setDescripcionCompleta("DescripcionCompleta");
      view.setCatTipoInsumoId(3);
      view.setCatCuadroIoId(2);
      view.setCatGpoTerapeuticoId(1);
      view.setActivo(true);

      String response = mockMvc.perform(MockMvcRequestBuilders.post("/catalogo-medicamentos")
         .contentType(JSON)
         .content(MAPPER.writeValueAsString(view)))
         .andExpect(MockMvcResultMatchers.status().isCreated())
         .andReturn().getResponse().getContentAsString();
      view = MAPPER.readValue(response, new TypeReference<CatMedicamentosView>() {
      });
      return view;
   }

}

