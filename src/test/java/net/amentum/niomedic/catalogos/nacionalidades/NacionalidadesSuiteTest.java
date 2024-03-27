package net.amentum.niomedic.catalogos.nacionalidades;

import java.util.Random;

import org.junit.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.core.type.TypeReference;

import net.amentum.niomedic.catalogos.ConfigurationTest;
import net.amentum.niomedic.catalogos.views.CatNacionalidadesView;

public class NacionalidadesSuiteTest extends ConfigurationTest {
	@Test
	public void createNacionalidadesBadRequest()throws Exception{
		CatNacionalidadesView view = new CatNacionalidadesView();
		mockMvc.perform(MockMvcRequestBuilders.post("/catalogo-nacionalidades")
				.contentType(JSON)
				.content(MAPPER.writeValueAsString(view)))
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andDo(MockMvcResultHandlers.print());
	}
	Random r= new Random();
	@Test
	public void createNacionalidadesCreated()throws Exception{
		CatNacionalidadesView view = new CatNacionalidadesView();
		view.setActivo(true);
		view.setNacPaisClave("n_p_c");
		view.setNacPaisCodigo(r.nextInt(9999999));
		view.setNacPaisDescripcion("nac_pais_description");
		mockMvc.perform(MockMvcRequestBuilders.post("/catalogo-nacionalidades")
				.contentType(JSON)
				.content(MAPPER.writeValueAsString(view)))
		.andExpect(MockMvcResultMatchers.status().isCreated())
		.andDo(MockMvcResultHandlers.print());
	}
	@Test
	public void createNacionalidadesConflict()throws Exception{
		CatNacionalidadesView view = createNacionalidadesParaTest();
		view.setActivo(true);
		view.setNacPaisClave("npc"+r.nextInt(99));
		view.setNacPaisCodigo(view.getNacPaisCodigo());
		view.setNacPaisDescripcion("nac_pais_description");
		mockMvc.perform(MockMvcRequestBuilders.post("/catalogo-nacionalidades")
				.contentType(JSON)
				.content(MAPPER.writeValueAsString(view)))
		.andExpect(MockMvcResultMatchers.status().isConflict())
		.andDo(MockMvcResultHandlers.print());
	}


	@Test
	public void updateNacionalidadesOk()throws Exception{
		CatNacionalidadesView view = createNacionalidadesParaTest();
		view.setActivo(false);
		view.setNacPaisClave("upc"+r.nextInt(99));
		view.setNacPaisDescripcion("u_nac_pais_description");
		mockMvc.perform(MockMvcRequestBuilders.put("/catalogo-nacionalidades/{idCatNacionalidades}",view.getIdCatNacionalidades())
				.contentType(JSON)
				.content(MAPPER.writeValueAsString(view)))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());

		view.setNacPaisCodigo(r.nextInt(9999999));
		mockMvc.perform(MockMvcRequestBuilders.put("/catalogo-nacionalidades/{idCatNacionalidades}",view.getIdCatNacionalidades())
				.contentType(JSON)
				.content(MAPPER.writeValueAsString(view)))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void updateNacionalidadesBadRequest() throws Exception{
		CatNacionalidadesView view = new CatNacionalidadesView();
		mockMvc.perform(MockMvcRequestBuilders.put("/catalogo-nacionalidades/{idCatNacionalidades}",9)
				.contentType(JSON)
				.content(MAPPER.writeValueAsString(view)))
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void updateNacionalidadesNotFound() throws Exception{
		CatNacionalidadesView view = new CatNacionalidadesView();
		view.setActivo(true);
		view.setNacPaisClave("n_p_c");
		view.setNacPaisCodigo(r.nextInt(9999999));
		view.setNacPaisDescripcion("nac_pais_description");
		mockMvc.perform(MockMvcRequestBuilders.put("/catalogo-nacionalidades/{idCatNacionalidades}",999999999)
				.contentType(JSON)
				.content(MAPPER.writeValueAsString(view)))
		.andExpect(MockMvcResultMatchers.status().isNotFound())
		.andDo(MockMvcResultHandlers.print());
	}


	@Test
	public void updateNacionalidadesConflict()throws Exception{
		CatNacionalidadesView view = createNacionalidadesParaTest();
		CatNacionalidadesView view2 = createNacionalidadesParaTest();

		view.setActivo(false);
		view.setNacPaisClave("upc"+r.nextInt(99));
		view.setNacPaisCodigo(view2.getNacPaisCodigo());
		view.setNacPaisDescripcion("u_nac_pais_description");
		mockMvc.perform(MockMvcRequestBuilders.put("/catalogo-nacionalidades/{idCatNacionalidades}",view.getIdCatNacionalidades())
				.contentType(JSON)
				.content(MAPPER.writeValueAsString(view)))
		.andExpect(MockMvcResultMatchers.status().isConflict())
		.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void seacrhCatNacionalidadOk() throws Exception {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-nacionalidades/search")
				.contentType(JSON)
				.params(params))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());

		//orderColumn
		params = new LinkedMultiValueMap<>();
		params.add("orderColumn", "activo");

		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-nacionalidades/search")
				.contentType(JSON)
				.params(params))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());

		params = new LinkedMultiValueMap<>();
		params.add("orderColumn", "idCatNacionalidades");
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-nacionalidades/search")
				.contentType(JSON)
				.params(params))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());

		params = new LinkedMultiValueMap<>();
		params.add("orderColumn", "nacPaisCodigo");
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-nacionalidades/search")
				.contentType(JSON)
				.params(params))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());

		params = new LinkedMultiValueMap<>();
		params.add("orderColumn", "nacPaisDescripcion");
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-nacionalidades/search")
				.contentType(JSON)
				.params(params))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());

		params = new LinkedMultiValueMap<>();
		params.add("orderColumn", "nacPaisClave");
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-nacionalidades/search")
				.contentType(JSON)
				.params(params))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());
		//orderColumn no existente
		params = new LinkedMultiValueMap<>();
		params.add("orderColumn", "kjsadfsfsjd");
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-nacionalidades/search")
				.contentType(JSON)
				.params(params))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());

		//orderType
		params = new LinkedMultiValueMap<>();
		params.add("orderColumn", "nacPaisClave");
		params.add("orderType", "asc");
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-nacionalidades/search")
				.contentType(JSON)
				.params(params))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());
		params = new LinkedMultiValueMap<>();
		//desc
		params = new LinkedMultiValueMap<>();
		params.add("orderColumn", "nacPaisClave");
		params.add("orderType", "desc");
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-nacionalidades/search")
				.contentType(JSON)
				.params(params))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());
		
		params = new LinkedMultiValueMap<>();
		params.add("orderColumn", "nacPaisClave");
		params.add("orderType", "lkadslfksdjlkf");
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-nacionalidades/search")
				.contentType(JSON)
				.params(params))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());

		//page y size

		params = new LinkedMultiValueMap<>();
		params.add("page", "0");
		params.add("sizie", "20");
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-nacionalidades/search")
				.contentType(JSON)
				.params(params))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());

		params = new LinkedMultiValueMap<>();
		params.add("page", "-1");
		params.add("size", "-20");
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-nacionalidades/search")
				.contentType(JSON)
				.params(params))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());
		
		//campos busquedas
		params = new LinkedMultiValueMap<>();
		params.add("datosBusqueda", "an");
		params.add("activo", "true");
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-nacionalidades/search")
				.contentType(JSON)
				.params(params))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());
		
		params = new LinkedMultiValueMap<>();

		params.add("activo", "false");
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-nacionalidades/search")
				.contentType(JSON)
				.params(params))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());

	}
	
	@Test
	public void findAllCatNacionalidadOk() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-nacionalidades/findAll")
				.contentType(JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void getByIdCatNacionalidadOk() throws Exception {
		CatNacionalidadesView view = createNacionalidadesParaTest();
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-nacionalidades/{idCatNacionalidades}",view.getIdCatNacionalidades())
				.contentType(JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void getByIdCatNacionalidadNotFound() throws Exception {	
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-nacionalidades/{idCatNacionalidades}",999999999)
				.contentType(JSON))
		.andExpect(MockMvcResultMatchers.status().isNotFound())
		.andDo(MockMvcResultHandlers.print());
	}
	
	public CatNacionalidadesView createNacionalidadesParaTest()throws Exception{
		CatNacionalidadesView view = new CatNacionalidadesView();
		view.setNacPaisClave("n_p_c");
		view.setNacPaisCodigo(r.nextInt(9999999));
		view.setNacPaisDescripcion("nac_pais_description");
		String response = mockMvc.perform(MockMvcRequestBuilders.post("/catalogo-nacionalidades")
				.contentType(JSON)
				.content(MAPPER.writeValueAsString(view)))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andReturn().getResponse().getContentAsString();
		view = MAPPER.readValue(response, new TypeReference<CatNacionalidadesView>() {}); 
		return view;

	}


}
