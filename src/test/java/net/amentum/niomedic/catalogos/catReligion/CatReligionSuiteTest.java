package net.amentum.niomedic.catalogos.catReligion;

import java.util.Random;

import org.junit.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.core.type.TypeReference;

import net.amentum.niomedic.catalogos.ConfigurationTest;
import net.amentum.niomedic.catalogos.views.CatReligionView;

public class CatReligionSuiteTest extends ConfigurationTest {
	private Random r= new Random();
	@Test
	public void catReligionBadRequest() throws Exception {
		CatReligionView view = new CatReligionView();
		view.setRelDescripcion("");

		System.out.println("\nrelDescripcion => vació");
		//Testigos de Lilith
		mockMvc.perform(MockMvcRequestBuilders.post("/catalogo-religion")
				.contentType(JSON)
				.content(MAPPER.writeValueAsString(view)))
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andDo(MockMvcResultHandlers.print());

		System.out.println("\nrelDescripcion => nulo");
		view.setRelDescripcion(null);
		mockMvc.perform(MockMvcRequestBuilders.post("/catalogo-religion")
				.contentType(JSON)
				.content(MAPPER.writeValueAsString(view)))
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andDo(MockMvcResultHandlers.print());	
	}

	@Test
	public void catReligionCreated() throws Exception {
		CatReligionView view = new CatReligionView();
		view.setRelDescripcion("Testigos de Lilith"+r.nextInt(99999));
		view.setActivo(null);
		//Testigos de Lilith
		mockMvc.perform(MockMvcRequestBuilders.post("/catalogo-religion")
				.contentType(JSON)
				.content(MAPPER.writeValueAsString(view)))
		.andExpect(MockMvcResultMatchers.status().isCreated())
		.andDo(MockMvcResultHandlers.print());

		System.out.println("\ncon un idCatReligion");

		view.setRelDescripcion("Testigos de Lilith"+r.nextInt(99999));
		view.setIdCatReligion(98765);
		mockMvc.perform(MockMvcRequestBuilders.post("/catalogo-religion")
				.contentType(JSON)
				.content(MAPPER.writeValueAsString(view)))
		.andExpect(MockMvcResultMatchers.status().isCreated())
		.andDo(MockMvcResultHandlers.print());	
	}

	@Test
	public void catReligionUpdateOK() throws Exception{
		CatReligionView Rview = religionParaTest();

		Rview.setRelDescripcion("Testigos de Lilith"+r.nextInt(99999));
		Rview.setActivo(false);

		mockMvc.perform(MockMvcRequestBuilders.put("/catalogo-religion/{idcatReligion}",Rview.getIdCatReligion())
				.contentType(JSON)
				.content(MAPPER.writeValueAsString(Rview)))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());	
	}

	@Test
	public void catReligionUpdateNotFound() throws Exception{
		CatReligionView view = new CatReligionView();
		view.setRelDescripcion("Testigos de Lilith"+r.nextInt(99999));
		view.setActivo(false);
		mockMvc.perform(MockMvcRequestBuilders.put("/catalogo-religion/{idcatReligion}",99999999)
				.contentType(JSON)
				.content(MAPPER.writeValueAsString(view)))
		.andExpect(MockMvcResultMatchers.status().isNotFound())
		.andDo(MockMvcResultHandlers.print());		
	}
	@Test
	public void catReligionSerchOK()throws Exception {
		MultiValueMap<String, String> params= new LinkedMultiValueMap<String, String>();

		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-religion/search")
				.contentType(JSON)
				.params(params))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());

		params.add("datosBusqueda", "cat");
		params.add("activo", "true");
		params.add("page", "0");
		params.add("size", "10");
		params.add("orderColumn", "relDescripcion");
		params.add("orderType", "asc");

		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-religion/search")
				.contentType(JSON)
				.params(params))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());	

		params= new LinkedMultiValueMap<String, String>();
		params.add("datosBusqueda", "cat");
		params.add("activo", "true");
		params.add("page", "0");
		params.add("size", "10");
		params.add("orderColumn", "relDescripcion");
		params.add("orderType", "desc");

		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-religion/search")
				.contentType(JSON)
				.params(params))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());
		
		params= new LinkedMultiValueMap<String, String>();
		params.add("activo", "true");
		params.add("page", "0");
		params.add("size", "10");
		params.add("orderColumn", "relDescripcion");
		params.add("orderType", "desc");

		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-religion/search")
				.contentType(JSON)
				.params(params))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());
		
		
		//pruebas sobre los campos page, size, orderColumn, orderType
		params= new LinkedMultiValueMap<String, String>();
		params.add("page", "-10");
		params.add("size", "-10");
		params.add("orderColumn", "ljkadshjsdfh");
		params.add("orderType", "kljdfhlkasd");

		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-religion/search")
				.contentType(JSON)
				.params(params))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());
		
		
		params= new LinkedMultiValueMap<String, String>();

		params.add("page", "10");
		params.add("size", "10");
		params.add("orderColumn", "ljkadshjsdfh");
		params.add("orderType", "kljdfhlkasd");

		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-religion/search")
				.contentType(JSON)
				.params(params))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void catReligionByIdOk() throws Exception {
		//Buscando uno caragado desde el csv
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-religion/{idCatReligion}",1)
				.contentType(JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());
		
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-religion/{idCatReligion}",5)
				.contentType(JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());
		
		//creando uno y lo busca 
		CatReligionView view = religionParaTest();
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-religion/{idCatReligion}",view.getIdCatReligion())
				.contentType(JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void catReligionByIdNotFound() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-religion/{idCatReligion}",99999999)
				.contentType(JSON))
		.andExpect(MockMvcResultMatchers.status().isNotFound())
		.andDo(MockMvcResultHandlers.print());
	}
	@Test
	public void catReligionFindAll() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-religion/findAll")
				.contentType(JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());
	}
	
	public CatReligionView religionParaTest() throws Exception {
		CatReligionView view = new CatReligionView();
		view.setRelDescripcion("Testigos de Lilith"+r.nextInt(99999));
		view.setActivo(true);
		String jsonResponse = mockMvc.perform(MockMvcRequestBuilders.post("/catalogo-religion")
				.contentType(JSON)
				.content(MAPPER.writeValueAsString(view)))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andReturn().getResponse().getContentAsString();

		view = MAPPER.readValue(jsonResponse, new TypeReference<CatReligionView>(){});
		return view;
	}

}
