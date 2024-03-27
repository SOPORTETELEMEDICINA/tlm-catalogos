package net.amentum.niomedic.catalogos.catCif;

import com.fasterxml.jackson.core.type.TypeReference;
import net.amentum.niomedic.catalogos.ConfigurationTest;

import net.amentum.niomedic.catalogos.views.CatCifView;
import org.junit.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class CatCifTest extends ConfigurationTest {

   @Test
   public void getDetailsByIdCatCif() throws Exception {
//      mvn clean test -Dtest=CatCifTest#getDetailsByIdCatCif test
      mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-discapacidades/3"))
         .andExpect(MockMvcResultMatchers.status().isOk())
         .andDo(MockMvcResultHandlers.print());
      System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>OK");
   }

   @Test
   public void getDetailsByIdCifBAD() throws Exception {
//      mvn clean test -Dtest=CatCifTest#getDetailsByIdCifBAD test
      mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-discapacidades/9999999"))
         .andExpect(MockMvcResultMatchers.status().isNotFound())
         .andDo(MockMvcResultHandlers.print());
      System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>404");

      mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-discapacidades/9a"))
         .andExpect(MockMvcResultMatchers.status().isBadRequest())
         .andDo(MockMvcResultHandlers.print());
      System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>400");
   }


   @Test
   public void findAll() throws Exception {
//      mvn clean test -Dtest=CatCifTest#findAll test
      mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-discapacidades/findAll"))
         .andExpect(MockMvcResultMatchers.status().isOk())
         .andDo(MockMvcResultHandlers.print());
   }

   @Test
   public void getCatCifSearch() throws Exception {
//      mvn clean test -Dtest=CatCifTest#getCatCifSearch test
      mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-discapacidades/search")
         .contentType(JSON))
         .andExpect(MockMvcResultMatchers.status().isOk())
         .andDo(MockMvcResultHandlers.print());
//
      mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-discapacidades/search?datosBusqueda=ORIENTACIÓN&activo=false&page=1&size=10&orderColumn=&orderType=asc")
         .contentType(JSON))
         .andExpect(MockMvcResultMatchers.status().isOk())
         .andDo(MockMvcResultHandlers.print());

      mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-discapacidades/search?activo=false")
         .contentType(JSON))
         .andExpect(MockMvcResultMatchers.status().isOk())
         .andDo(MockMvcResultHandlers.print());

      mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-discapacidades/search?orderColumn=idCif")
         .contentType(JSON))
         .andExpect(MockMvcResultMatchers.status().isOk())
         .andDo(MockMvcResultHandlers.print());

      mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-discapacidades/search?activo=false&orderType=desc")
         .contentType(JSON))
         .andExpect(MockMvcResultMatchers.status().isOk())
         .andDo(MockMvcResultHandlers.print());

   }

   @Test
   public void getCatCifSearchBAD() throws Exception {
//      mvn clean test -Dtest=CatCifTest#getCatCifSearchBAD test
      mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-discapacidades/search?datosBusqueda=&activo=&page=-1&size=-10&orderColumn=ERRORNADA&orderType=ascdesc")
         .contentType(JSON))
         .andExpect(MockMvcResultMatchers.status().isOk())
         .andDo(MockMvcResultHandlers.print());

      mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-discapacidades/search?activo=treu")
         .contentType(JSON))
         .andExpect(MockMvcResultMatchers.status().isBadRequest())
         .andDo(MockMvcResultHandlers.print());

   }

   @Test
   public void createCatCif() throws Exception {
//      mvn clean test -Dtest=CatCifTest#createCatCif test
      CatCifView view = new CatCifView();
      view.setDiscCodigo("DiscCodigo");
      view.setDiscDescripcion("DiscDescripcion");
      view.setActivo(false);

      mockMvc.perform(MockMvcRequestBuilders.post("/catalogo-discapacidades")
         .contentType(JSON)
         .content(MAPPER.writeValueAsString(view)))
         .andExpect(MockMvcResultMatchers.status().isCreated())
         .andDo(MockMvcResultHandlers.print());
      System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>OK");
   }

   @Test
   public void createCatCifBAD() throws Exception {
//      mvn clean test -Dtest=CatCifTest#createCatCifBAD test
      CatCifView view = new CatCifView();

      mockMvc.perform(MockMvcRequestBuilders.post("/catalogo-discapacidades")
         .contentType(JSON)
         .content(MAPPER.writeValueAsString(view)))
         .andExpect(MockMvcResultMatchers.status().isBadRequest())
         .andDo(MockMvcResultHandlers.print());
      System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>400");
   }

   @Test
   public void updateCatCif() throws Exception {
//      mvn clean test -Dtest=CatCifTest#updateCatCif test
      CatCifView view = createRecordCatCif();
      view.setDiscCodigo("UPD-DiscCo");
      view.setDiscDescripcion("UPD-DiscDescripcion");
      view.setActivo(true);

      mockMvc.perform(MockMvcRequestBuilders.put("/catalogo-discapacidades/{idCatCif}", view.getIdCatCif())
         .contentType(JSON)
         .content(MAPPER.writeValueAsString(view)))
         .andExpect(MockMvcResultMatchers.status().isOk())
         .andDo(MockMvcResultHandlers.print());
      System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>OK");

   }

   @Test
   public void updateCatCifBAD() throws Exception {
//      mvn clean test -Dtest=CatCifTest#updateCatCifBAD test
      CatCifView view = createRecordCatCif();

      mockMvc.perform(MockMvcRequestBuilders.put("/catalogo-discapacidades/{idCatCif}",view.getIdCatCif())
         .contentType(JSON)
         .content(MAPPER.writeValueAsString(new CatCifView())))
         .andExpect(MockMvcResultMatchers.status().isBadRequest())
         .andDo(MockMvcResultHandlers.print());
      System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>400");

      mockMvc.perform(MockMvcRequestBuilders.put("/catalogo-discapacidades/{idCatCif}", view.getIdCatCif() * 9)
         .contentType(JSON)
         .content(MAPPER.writeValueAsString(view)))
         .andExpect(MockMvcResultMatchers.status().isNotFound())
         .andDo(MockMvcResultHandlers.print());
      System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>404");

   }

   public CatCifView createRecordCatCif() throws Exception {

      CatCifView view = new CatCifView();
      view.setDiscCodigo("DiscCodigo");
      view.setDiscDescripcion("DiscDescripcion");
      view.setActivo(true);
      String response = mockMvc.perform(MockMvcRequestBuilders.post("/catalogo-discapacidades")
         .contentType(JSON)
         .content(MAPPER.writeValueAsString(view)))
         .andExpect(MockMvcResultMatchers.status().isCreated())
         .andReturn().getResponse().getContentAsString();
      view = MAPPER.readValue(response, new TypeReference<CatCifView>() {
      });
      return view;
   }

}

