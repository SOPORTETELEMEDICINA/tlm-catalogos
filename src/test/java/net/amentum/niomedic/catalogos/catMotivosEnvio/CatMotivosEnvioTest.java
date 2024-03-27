package net.amentum.niomedic.catalogos.catMotivosEnvio;

import com.fasterxml.jackson.core.type.TypeReference;
import net.amentum.niomedic.catalogos.ConfigurationTest;
import net.amentum.niomedic.catalogos.views.CatMotivosEnvioView;
import org.junit.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class CatMotivosEnvioTest extends ConfigurationTest {

   @Test
   public void getDetailsByIdCatMotivosEnvio() throws Exception {
//      mvn clean test -Dtest=CatMotivosEnvioTest#getDetailsByIdCatMotivosEnvio test
      mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-motivos-envio/3"))
         .andExpect(MockMvcResultMatchers.status().isOk())
         .andDo(MockMvcResultHandlers.print());
      System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>OK");
   }

   @Test
   public void getDetailsByIdCatMotivosEnvioBAD() throws Exception {
//      mvn clean test -Dtest=CatMotivosEnvioTest#getDetailsByIdCatMotivosEnvioBAD test
      mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-motivos-envio/9999999"))
         .andExpect(MockMvcResultMatchers.status().isNotFound())
         .andDo(MockMvcResultHandlers.print());
      System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>404");

      mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-motivos-envio/9a"))
         .andExpect(MockMvcResultMatchers.status().isBadRequest())
         .andDo(MockMvcResultHandlers.print());
      System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>400");
   }


   @Test
   public void findAll() throws Exception {
//      mvn clean test -Dtest=CatMotivosEnvioTest#findAll test
      mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-motivos-envio/findAll"))
         .andExpect(MockMvcResultMatchers.status().isOk())
         .andDo(MockMvcResultHandlers.print());
   }

   @Test
   public void getCatMotivosEnvioSearch() throws Exception {
//      mvn clean test -Dtest=CatMotivosEnvioTest#getCatMotivosEnvioSearch test
      mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-motivos-envio/search")
         .contentType(JSON))
         .andExpect(MockMvcResultMatchers.status().isOk())
         .andDo(MockMvcResultHandlers.print());
//
      mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-motivos-envio/search?datosBusqueda=ORIENTACIÓN&activo=false&page=1&size=10&orderColumn=&orderType=asc")
         .contentType(JSON))
         .andExpect(MockMvcResultMatchers.status().isOk())
         .andDo(MockMvcResultHandlers.print());

      mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-motivos-envio/search?activo=false")
         .contentType(JSON))
         .andExpect(MockMvcResultMatchers.status().isOk())
         .andDo(MockMvcResultHandlers.print());

      mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-motivos-envio/search?orderColumn=idCif")
         .contentType(JSON))
         .andExpect(MockMvcResultMatchers.status().isOk())
         .andDo(MockMvcResultHandlers.print());

      mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-motivos-envio/search?activo=false&orderType=desc")
         .contentType(JSON))
         .andExpect(MockMvcResultMatchers.status().isOk())
         .andDo(MockMvcResultHandlers.print());

   }

   @Test
   public void getCatMotivosEnvioSearchBAD() throws Exception {
//      mvn clean test -Dtest=CatMotivosEnvioTest#getCatMotivosEnvioSearchBAD test
      mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-motivos-envio/search?datosBusqueda=&activo=&page=-1&size=-10&orderColumn=ERRORNADA&orderType=ascdesc")
         .contentType(JSON))
         .andExpect(MockMvcResultMatchers.status().isOk())
         .andDo(MockMvcResultHandlers.print());

      mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-motivos-envio/search?activo=treu")
         .contentType(JSON))
         .andExpect(MockMvcResultMatchers.status().isBadRequest())
         .andDo(MockMvcResultHandlers.print());

//nunca sucedera
//      mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-motivos-envio/search")
//         .contentType(JSON))
//         .andExpect(MockMvcResultMatchers.status().isBadRequest())
//         .andDo(MockMvcResultHandlers.print());

   }

   @Test
   public void createCatMotivosEnvio() throws Exception {
//      mvn clean test -Dtest=CatMotivosEnvioTest#createCatMotivosEnvio test
      CatMotivosEnvioView view = new CatMotivosEnvioView();
      view.setMotivosEnvioDescripcion("MotivosEnvioDescripcionMotivosEnvioDescripcionMotivosEnvioDescripcion");
      view.setActivo(false);


      mockMvc.perform(MockMvcRequestBuilders.post("/catalogo-motivos-envio")
         .contentType(JSON)
         .content(MAPPER.writeValueAsString(view)))
         .andExpect(MockMvcResultMatchers.status().isCreated())
         .andDo(MockMvcResultHandlers.print());
      System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>OK");
   }

   @Test
   public void createCatMotivosEnvioBAD() throws Exception {
//      mvn clean test -Dtest=CatMotivosEnvioTest#createCatMotivosEnvioBAD test
      CatMotivosEnvioView view = new CatMotivosEnvioView();

      mockMvc.perform(MockMvcRequestBuilders.post("/catalogo-motivos-envio")
         .contentType(JSON)
         .content(MAPPER.writeValueAsString(view)))
         .andExpect(MockMvcResultMatchers.status().isBadRequest())
         .andDo(MockMvcResultHandlers.print());
      System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>400");
   }

   @Test
   public void updateCatMotivosEnvio() throws Exception {
//      mvn clean test -Dtest=CatMotivosEnvioTest#updateCatMotivosEnvio test
      CatMotivosEnvioView view = createRecordCatMotivosEnvio();
      view.setMotivosEnvioDescripcion("UPD-MotivosEnvioDescripcion");
      view.setActivo(true);

      mockMvc.perform(MockMvcRequestBuilders.put("/catalogo-motivos-envio/{idCatMotivosEnvio}", view.getIdCatMotivosEnvio())
         .contentType(JSON)
         .content(MAPPER.writeValueAsString(view)))
         .andExpect(MockMvcResultMatchers.status().isOk())
         .andDo(MockMvcResultHandlers.print());
      System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>OK");

   }

   @Test
   public void updateCatMotivosEnvioBAD() throws Exception {
//      mvn clean test -Dtest=CatMotivosEnvioTest#updateCatMotivosEnvioBAD test
      CatMotivosEnvioView view = createRecordCatMotivosEnvio();

      mockMvc.perform(MockMvcRequestBuilders.put("/catalogo-motivos-envio/{idCatMotivosEnvio}", view.getIdCatMotivosEnvio())
         .contentType(JSON)
         .content(MAPPER.writeValueAsString(new CatMotivosEnvioView())))
         .andExpect(MockMvcResultMatchers.status().isBadRequest())
         .andDo(MockMvcResultHandlers.print());
      System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>400");

      mockMvc.perform(MockMvcRequestBuilders.put("/catalogo-motivos-envio/{idCatMotivosEnvio}", view.getIdCatMotivosEnvio() * 9)
         .contentType(JSON)
         .content(MAPPER.writeValueAsString(view)))
         .andExpect(MockMvcResultMatchers.status().isNotFound())
         .andDo(MockMvcResultHandlers.print());
      System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>404");

   }

   public CatMotivosEnvioView createRecordCatMotivosEnvio() throws Exception {

      CatMotivosEnvioView view = new CatMotivosEnvioView();
      view.setMotivosEnvioDescripcion("MotivosEnvioDescripcionMotivosEnvioDescripcionMotivosEnvioDescripcion");
      view.setActivo(true);
      String response = mockMvc.perform(MockMvcRequestBuilders.post("/catalogo-motivos-envio")
         .contentType(JSON)
         .content(MAPPER.writeValueAsString(view)))
         .andExpect(MockMvcResultMatchers.status().isCreated())
         .andReturn().getResponse().getContentAsString();
      view = MAPPER.readValue(response, new TypeReference<CatMotivosEnvioView>() {
      });
      return view;
   }

}

