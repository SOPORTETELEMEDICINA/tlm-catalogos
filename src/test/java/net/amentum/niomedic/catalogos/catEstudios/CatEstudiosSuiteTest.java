package net.amentum.niomedic.catalogos.catEstudios;

import org.junit.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.core.type.TypeReference;

import net.amentum.niomedic.catalogos.ConfigurationTest;
import net.amentum.niomedic.catalogos.views.CatEstudiosView;

public class CatEstudiosSuiteTest extends ConfigurationTest {
	@Test
	public void createCatestudiosCreate() throws Exception {
		CatEstudiosView view = new CatEstudiosView();
		view.setDescripcion("descripcion");
		view.setPreparacion("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
		view.setEstudio("estudios");
		mockMvc.perform(MockMvcRequestBuilders.post("/catalogo-estudios")
				.contentType(JSON)
				.content(MAPPER.writeValueAsString(view)))
		.andExpect(MockMvcResultMatchers.status().isCreated())
		.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void createCatestudiosBadRequest() throws Exception {
		CatEstudiosView view = new CatEstudiosView();
		mockMvc.perform(MockMvcRequestBuilders.post("/catalogo-estudios")
				.contentType(JSON)
				.content(MAPPER.writeValueAsString(view)))
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andDo(MockMvcResultHandlers.print());

		view = new CatEstudiosView();
		view.setDescripcion("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
		view.setPreparacion("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
		view.setEstudio("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
		mockMvc.perform(MockMvcRequestBuilders.post("/catalogo-estudios")
				.contentType(JSON)
				.content(MAPPER.writeValueAsString(view)))
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void updateCatestudiosOk() throws Exception {
		CatEstudiosView view = createCatestudiosParaTest();
		view.setDescripcion("u_descripcion");
		view.setPreparacion("u_Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
		view.setEstudio("u_estudios");
		mockMvc.perform(MockMvcRequestBuilders.put("/catalogo-estudios/{idCatEstudio}",view.getIdCatEstudio())
				.contentType(JSON)
				.content(MAPPER.writeValueAsString(view)))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());

		view.setActivo(null);
		mockMvc.perform(MockMvcRequestBuilders.put("/catalogo-estudios/{idCatEstudio}",view.getIdCatEstudio())
				.contentType(JSON)
				.content(MAPPER.writeValueAsString(view)))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());

		view.setActivo(false);
		mockMvc.perform(MockMvcRequestBuilders.put("/catalogo-estudios/{idCatEstudio}",view.getIdCatEstudio())
				.contentType(JSON)
				.content(MAPPER.writeValueAsString(view)))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void updateCatestudiosBadRequest() throws Exception {
		CatEstudiosView view = new CatEstudiosView();
		mockMvc.perform(MockMvcRequestBuilders.put("/catalogo-estudios/{idCatEstudio}",1)
				.contentType(JSON)
				.content(MAPPER.writeValueAsString(view)))
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andDo(MockMvcResultHandlers.print());

		view = new CatEstudiosView();
		view.setDescripcion("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
		view.setPreparacion("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
		view.setEstudio("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
		mockMvc.perform(MockMvcRequestBuilders.put("/catalogo-estudios/{idCatEstudio}",1)
				.contentType(JSON)
				.content(MAPPER.writeValueAsString(view)))
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void updateCatEstudiosNotFound()throws Exception {
		CatEstudiosView view = new CatEstudiosView();
		view.setDescripcion("Lorem ipsum dolor sit amet, consectetur adipiscing eli");
		view.setPreparacion("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
		view.setEstudio("Lorem ipsum dolor sit amet, consectetur adipiscing eli");
		mockMvc.perform(MockMvcRequestBuilders.put("/catalogo-estudios/{idCatEstudio}",99999999)
				.contentType(JSON)
				.content(MAPPER.writeValueAsString(view)))
		.andExpect(MockMvcResultMatchers.status().isNotFound())
		.andDo(MockMvcResultHandlers.print());
	}


	@Test
	public void getByIdCatEstudiosOK()throws Exception {
		CatEstudiosView view = createCatestudiosParaTest();
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-estudios/{idCatEstudio}",view.getIdCatEstudio())
				.contentType(JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void getByIdCatEstudiosNotFound()throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-estudios/{idCatEstudio}",99999999)
				.contentType(JSON))
		.andExpect(MockMvcResultMatchers.status().isNotFound())
		.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void searchEstudiosOk()throws Exception {
		MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-estudios/search")
				.contentType(JSON)
				.params(params))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());


		params = new LinkedMultiValueMap<>();
		params.add("datosBusqueda", "ante");
		params.add("activo", "true");
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-estudios/search")
				.contentType(JSON)
				.params(params))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());

		params = new LinkedMultiValueMap<>();
		params.add("activo", "true");
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-estudios/search")
				.contentType(JSON)
				.params(params))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());

		params = new LinkedMultiValueMap<>();
		params.add("datosBusqueda", "ante");
		params.add("activo", "false");
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-estudios/search")
				.contentType(JSON)
				.params(params))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());


		params = new LinkedMultiValueMap<>();
		params.add("datosBusqueda", "ante");
		params.add("orderType", "asc");
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-estudios/search")
				.contentType(JSON)
				.params(params))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());

		params = new LinkedMultiValueMap<>();
		params.add("datosBusqueda", "ante");
		params.add("orderType", "desc");
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-estudios/search")
				.contentType(JSON)
				.params(params))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());

		//orderColumn
		params = new LinkedMultiValueMap<>();
		params.add("orderColumn", "idCatEstudio");
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-estudios/search")
				.contentType(JSON)
				.params(params))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());

		params = new LinkedMultiValueMap<>();
		params.add("orderColumn", "descripcion");
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-estudios/search")
				.contentType(JSON)
				.params(params))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());

		params = new LinkedMultiValueMap<>();
		params.add("orderColumn", "preparacion");
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-estudios/search")
				.contentType(JSON)
				.params(params))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());

		params = new LinkedMultiValueMap<>();
		params.add("orderColumn", "estudio");
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-estudios/search")
				.contentType(JSON)
				.params(params))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());

		params = new LinkedMultiValueMap<>();
		params.add("orderColumn", "activo");
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-estudios/search")
				.contentType(JSON)
				.params(params))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());

		params = new LinkedMultiValueMap<>();
		params.add("page", "3");
		params.add("size", "15");
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-estudios/search")
				.contentType(JSON)
				.params(params))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());

		//parametros incorrecto
		params = new LinkedMultiValueMap<>();
		params.add("orderType", "asc");
		params.add("orderColumn", "adsfsafsdf");
		params.add("page", "-1");
		params.add("size", "-1");
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-estudios/search")
				.contentType(JSON)
				.params(params))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());
	}

	public CatEstudiosView createCatestudiosParaTest() throws Exception {
		CatEstudiosView view = new CatEstudiosView();
		view.setDescripcion("descripcion");
		view.setPreparacion("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
		view.setEstudio("estudios");
		String response = mockMvc.perform(MockMvcRequestBuilders.post("/catalogo-estudios")
				.contentType(JSON)
				.content(MAPPER.writeValueAsString(view)))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andReturn().getResponse().getContentAsString();
		view = MAPPER.readValue(response, new TypeReference<CatEstudiosView>() {} );
		return view;
	}
}
