package net.amentum.niomedic.catalogos.catEspecialidades;

import com.fasterxml.jackson.core.type.TypeReference;
import net.amentum.niomedic.catalogos.ConfigurationTest;
import net.amentum.niomedic.catalogos.views.CatEspecialidadesView;
import org.junit.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class CatEspecialidadesTest extends ConfigurationTest {

   @Test
   public void getDetailsByIdCatEspecialidades() throws Exception {
//      mvn clean test -Dtest=CatEspecialidadesTest#getDetailsByIdCatEspecialidades test
      mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-especialidades/3"))
         .andExpect(MockMvcResultMatchers.status().isOk())
         .andDo(MockMvcResultHandlers.print());
      System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>OK");
   }

   @Test
   public void getDetailsByIdCatEspecialidadesBAD() throws Exception {
//      mvn clean test -Dtest=CatEspecialidadesTest#getDetailsByIdCatEspecialidadesBAD test
      mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-especialidades/9999999"))
         .andExpect(MockMvcResultMatchers.status().isNotFound())
         .andDo(MockMvcResultHandlers.print());
      System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>404");

      mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-especialidades/9a"))
         .andExpect(MockMvcResultMatchers.status().isBadRequest())
         .andDo(MockMvcResultHandlers.print());
      System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>400");
   }


   @Test
   public void findAll() throws Exception {
//      mvn clean test -Dtest=CatEspecialidadesTest#findAll test
      mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-especialidades/findAll"))
         .andExpect(MockMvcResultMatchers.status().isOk())
         .andDo(MockMvcResultHandlers.print());
   }

   @Test
   public void getCatEspecialidadesSearch() throws Exception {
//      mvn clean test -Dtest=CatEspecialidadesTest#getCatEspecialidadesSearch test
      mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-especialidades/search")
         .contentType(JSON))
         .andExpect(MockMvcResultMatchers.status().isOk())
         .andDo(MockMvcResultHandlers.print());
//
      mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-especialidades/search?datosBusqueda=MEDICINA&activo=false&page=1&size=10&orderColumn=&orderType=asc")
         .contentType(JSON))
         .andExpect(MockMvcResultMatchers.status().isOk())
         .andDo(MockMvcResultHandlers.print());

      mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-especialidades/search?activo=false")
         .contentType(JSON))
         .andExpect(MockMvcResultMatchers.status().isOk())
         .andDo(MockMvcResultHandlers.print());

      mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-especialidades/search?orderColumn=idCif")
         .contentType(JSON))
         .andExpect(MockMvcResultMatchers.status().isOk())
         .andDo(MockMvcResultHandlers.print());

      mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-especialidades/search?activo=false&orderType=desc")
         .contentType(JSON))
         .andExpect(MockMvcResultMatchers.status().isOk())
         .andDo(MockMvcResultHandlers.print());

   }

   @Test
   public void getCatEspecialidadesSearchBAD() throws Exception {
//      mvn clean test -Dtest=CatEspecialidadesTest#getCatEspecialidadesSearchBAD test
      mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-especialidades/search?datosBusqueda=&activo=&page=-1&size=-10&orderColumn=ERRORNADA&orderType=ascdesc")
         .contentType(JSON))
         .andExpect(MockMvcResultMatchers.status().isOk())
         .andDo(MockMvcResultHandlers.print());

      mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-especialidades/search?activo=treu")
         .contentType(JSON))
         .andExpect(MockMvcResultMatchers.status().isBadRequest())
         .andDo(MockMvcResultHandlers.print());

   }

   @Test
   public void createCatEspecialidades() throws Exception {
//      mvn clean test -Dtest=CatEspecialidadesTest#createCatEspecialidades test
      CatEspecialidadesView view = new CatEspecialidadesView();
      view.setEspecialidadCodigo("EspeCodigo");
      view.setEspecialidadDescripcion("EspecialidadDescripcion");
      view.setActivo(false);

      mockMvc.perform(MockMvcRequestBuilders.post("/catalogo-especialidades")
         .contentType(JSON)
         .content(MAPPER.writeValueAsString(view)))
         .andExpect(MockMvcResultMatchers.status().isCreated())
         .andDo(MockMvcResultHandlers.print());
      System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>OK");
   }

   @Test
   public void createCatEspecialidadesBAD() throws Exception {
//      mvn clean test -Dtest=CatEspecialidadesTest#createCatEspecialidadesBAD test
      CatEspecialidadesView view = new CatEspecialidadesView();

      mockMvc.perform(MockMvcRequestBuilders.post("/catalogo-especialidades")
         .contentType(JSON)
         .content(MAPPER.writeValueAsString(view)))
         .andExpect(MockMvcResultMatchers.status().isBadRequest())
         .andDo(MockMvcResultHandlers.print());
      System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>400");
   }

   @Test
   public void updateCatEspecialidades() throws Exception {
//      mvn clean test -Dtest=CatEspecialidadesTest#updateCatEspecialidades test
      CatEspecialidadesView view = createRecordCatEspecialidades();
      view.setEspecialidadCodigo("UPD-EsCod");
      view.setEspecialidadDescripcion("UPD-EspecialidadDescripcion");
      view.setActivo(true);

      mockMvc.perform(MockMvcRequestBuilders.put("/catalogo-especialidades/{idCatEspecialidades}", view.getIdCatEspecialidades())
         .contentType(JSON)
         .content(MAPPER.writeValueAsString(view)))
         .andExpect(MockMvcResultMatchers.status().isOk())
         .andDo(MockMvcResultHandlers.print());
      System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>OK");

   }

   @Test
   public void updateCatEspecialidadesBAD() throws Exception {
//      mvn clean test -Dtest=CatEspecialidadesTest#updateCatEspecialidadesBAD test
      CatEspecialidadesView view = createRecordCatEspecialidades();

      mockMvc.perform(MockMvcRequestBuilders.put("/catalogo-especialidades/{idCatEspecialidades}",view.getIdCatEspecialidades())
         .contentType(JSON)
         .content(MAPPER.writeValueAsString(new CatEspecialidadesView())))
         .andExpect(MockMvcResultMatchers.status().isBadRequest())
         .andDo(MockMvcResultHandlers.print());
      System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>400");

      mockMvc.perform(MockMvcRequestBuilders.put("/catalogo-especialidades/{idCatEspecialidades}", view.getIdCatEspecialidades() * 9)
         .contentType(JSON)
         .content(MAPPER.writeValueAsString(view)))
         .andExpect(MockMvcResultMatchers.status().isNotFound())
         .andDo(MockMvcResultHandlers.print());
      System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>404");

   }

   public CatEspecialidadesView createRecordCatEspecialidades() throws Exception {

      CatEspecialidadesView view = new CatEspecialidadesView();
      view.setEspecialidadCodigo("EspeCodigo");
      view.setEspecialidadDescripcion("EspecialidadDescripcion");
      view.setActivo(true);
      String response = mockMvc.perform(MockMvcRequestBuilders.post("/catalogo-especialidades")
         .contentType(JSON)
         .content(MAPPER.writeValueAsString(view)))
         .andExpect(MockMvcResultMatchers.status().isCreated())
         .andReturn().getResponse().getContentAsString();
      view = MAPPER.readValue(response, new TypeReference<CatEspecialidadesView>() {
      });
      return view;
   }

}

