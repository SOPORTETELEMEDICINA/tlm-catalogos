package net.amentum.niomedic.catalogos.catViaAdministracion;

import org.junit.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.core.type.TypeReference;

import net.amentum.niomedic.catalogos.ConfigurationTest;
import net.amentum.niomedic.catalogos.views.CatViaAdministracionView;

public class catViaAdministracionSuiteTest extends ConfigurationTest {

	@Test
	public void createViaAdministracionCreated() throws Exception {
		CatViaAdministracionView view = new CatViaAdministracionView();
		view.setVaDescripcion("nueva_via_administracion");
		mockMvc.perform(MockMvcRequestBuilders.post("/catalogo-via-administracion")
				.contentType(JSON)
				.content(MAPPER.writeValueAsString(view)))
		.andExpect(MockMvcResultMatchers.status().isCreated())
		.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void ceateViaAdministracionBadRequest() throws Exception {
		CatViaAdministracionView view = new CatViaAdministracionView();
		mockMvc.perform(MockMvcRequestBuilders.post("/catalogo-via-administracion")
				.contentType(JSON)
				.content(MAPPER.writeValueAsString(view)))
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andDo(MockMvcResultHandlers.print());
		//superando el maximo de caracteres
		view = new CatViaAdministracionView();
		view.setVaDescripcion("INgrooves (en nombre de Dubmission Records); Downtown Music Publishing, BMI - Broadcast Music Inc. y 10 sociedades de derechos musicales");
		mockMvc.perform(MockMvcRequestBuilders.post("/catalogo-via-administracion")
				.contentType(JSON)
				.content(MAPPER.writeValueAsString(view)))
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void updateViaAdministracionOK() throws Exception {
		CatViaAdministracionView view = createViaAdministracionParaTest();

		view.setVaDescripcion("u_nueva_via_administracion");
		view.setActivo(null);
		mockMvc.perform(MockMvcRequestBuilders.put("/catalogo-via-administracion/{idCatViaAdministracion}",view.getIdCatViaAdministracion())
				.contentType(JSON)
				.content(MAPPER.writeValueAsString(view)))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());

		view.setVaDescripcion("u_nueva_via_administracion2");
		view.setActivo(false);
		mockMvc.perform(MockMvcRequestBuilders.put("/catalogo-via-administracion/{idCatViaAdministracion}",view.getIdCatViaAdministracion())
				.contentType(JSON)
				.content(MAPPER.writeValueAsString(view)))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());	
	}

	@Test
	public void updateViaAdministracionNotFound() throws Exception {
		CatViaAdministracionView view = new CatViaAdministracionView();
		view.setVaDescripcion("U_nueva_via_administracion");
		view.setActivo(false);
		mockMvc.perform(MockMvcRequestBuilders.put("/catalogo-via-administracion/{idCatViaAdministracion}",999999999)
				.contentType(JSON)
				.content(MAPPER.writeValueAsString(view)))
		.andExpect(MockMvcResultMatchers.status().isNotFound())
		.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void findAllViaAdministracionOk() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-via-administracion/findAll")
				.contentType(JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());
	}

	@Test 
	public void getByIdViaAdministracionOk()throws Exception {
		CatViaAdministracionView view = createViaAdministracionParaTest();
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-via-administracion/{idCatViaAdministracion}",view.getIdCatViaAdministracion())
				.contentType(JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());
	}

	@Test 
	public void getByIdViaAdministracionNotFound()throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-via-administracion/{idCatViaAdministracion}",999999999)
				.contentType(JSON))
		.andExpect(MockMvcResultMatchers.status().isNotFound())
		.andDo(MockMvcResultHandlers.print());
	}


	@Test 
	public void searchViaAdministracionOK()throws Exception {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

		params = new LinkedMultiValueMap<>();
		params.add("datosBusqueda", "adn");
		params.add("activo", "true");
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-via-administracion/search")
				.contentType(JSON).params(params))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());

		params = new LinkedMultiValueMap<>();
		params.add("datosBusqueda", "adn");
		params.add("activo", "false");
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-via-administracion/search")
				.contentType(JSON).params(params))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());
		
		params = new LinkedMultiValueMap<>();
		params.add("activo", "false");
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-via-administracion/search")
				.contentType(JSON).params(params))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());
		//orderColumn y orderType
		params = new LinkedMultiValueMap<>();
		params.add("orderColumn", "activo");
		params.add("orderType", "asc");
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-via-administracion/search")
				.contentType(JSON).params(params))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());

		params = new LinkedMultiValueMap<>();
		params.add("orderColumn", "idCatViaAdministracion");
		params.add("orderType", "asc");
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-via-administracion/search")
				.contentType(JSON).params(params))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());

		params = new LinkedMultiValueMap<>();
		params.add("orderColumn", "vaDescripcion");
		params.add("orderType", "asc");
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-via-administracion/search")
				.contentType(JSON).params(params))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());
		
		
		params = new LinkedMultiValueMap<>();
		params.add("orderColumn", "vaDescripcion");
		params.add("orderType", "desc");
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-via-administracion/search")
				.contentType(JSON).params(params))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());
		//paramectros incorrectos
		
		params = new LinkedMultiValueMap<>();
		params.add("activo", "true");
		params.add("orderColumn", "sadñlkfahdskjldfha");
		params.add("orderType", "kjdhkjhdñdsa");
		params.add("page", "-1");
		params.add("size", "-1");
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-via-administracion/search")
				.contentType(JSON).params(params))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());
		
	}


	public CatViaAdministracionView	createViaAdministracionParaTest() throws Exception {
		CatViaAdministracionView view = new CatViaAdministracionView();
		view.setVaDescripcion("nueva_via_administracion");
		view.setActivo(true);
		String response = mockMvc.perform(MockMvcRequestBuilders.post("/catalogo-via-administracion")
				.contentType(JSON)
				.content(MAPPER.writeValueAsString(view)))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andReturn().getResponse().getContentAsString();
		view = MAPPER.readValue(response, new TypeReference<CatViaAdministracionView>(){});
		return view;
	}


}
