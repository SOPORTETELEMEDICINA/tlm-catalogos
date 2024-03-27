package net.amentum.niomedic.catalogos;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {NioCatalogosApplication.class})
public class CatLocalidadesDomTest {
   private final MediaType jsonType = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf-8"));
   public MockMvc mockMvc;

   @Autowired
   private WebApplicationContext webApplicationContext;

   @Autowired
   private ObjectMapper objectMapper;

   @Before
   public void setup() {
      mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
   }

   @Test
   public void getDetailsByIdCatLocalidadesDom() throws Exception {
      String idCatLocalidadesDom = "9";
      mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-localidades/" + idCatLocalidadesDom))
         .andExpect(MockMvcResultMatchers.status().isOk())
         .andDo(MockMvcResultHandlers.print());
   }

   @Test
   public void findAll() throws Exception {
      mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-localidades/findAll"))
         .andExpect(MockMvcResultMatchers.status().isOk())
         .andDo(MockMvcResultHandlers.print());
   }

   @Test
   public void getCatLocalidadesDomSearch() throws Exception {
      mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-localidades/search?datosBusqueda=&page=&size=&orderColumn=&orderType=des")
         .contentType(jsonType))
         .andExpect(MockMvcResultMatchers.status().isOk())
         .andDo(MockMvcResultHandlers.print());
   }
}
