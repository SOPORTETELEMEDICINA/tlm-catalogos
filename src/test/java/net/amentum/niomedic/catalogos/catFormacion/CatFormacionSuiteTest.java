package net.amentum.niomedic.catalogos.catFormacion;

import org.junit.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.core.type.TypeReference;

import net.amentum.niomedic.catalogos.ConfigurationTest;
import net.amentum.niomedic.catalogos.views.CatFormacionView;

public class CatFormacionSuiteTest extends ConfigurationTest {
	@Test
	public void createFormacionBadRequest() throws Exception {
		CatFormacionView view= new CatFormacionView();
		mockMvc.perform(MockMvcRequestBuilders.post("/catalogo-formacion")
				.contentType(JSON)
				.content(MAPPER.writeValueAsString(view)))
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void createFormacionCreadted() throws Exception {
		CatFormacionView view= new CatFormacionView();
		view.setFormAgrupacion("formacion_agrupacion");
		view.setFormDescripcion("fromacion_descripcion");
		view.setFormGrado(13);
		mockMvc.perform(MockMvcRequestBuilders.post("/catalogo-formacion")
				.contentType(JSON)
				.content(MAPPER.writeValueAsString(view)))
		.andExpect(MockMvcResultMatchers.status().isCreated())
		.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void updateFormacionOk() throws Exception {
		CatFormacionView view = createFormacionParaTest();
		view.setFormAgrupacion("u_formacion_agrupacion");
		view.setFormDescripcion("U_fromacion_descripcion");
		view.setFormGrado(12);
		mockMvc.perform(MockMvcRequestBuilders.put("/catalogo-formacion/{idCatFormacion}",view.getIdCatFormacion())
				.contentType(JSON)
				.content(MAPPER.writeValueAsString(view)))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());

		view.setActivo(false);
		mockMvc.perform(MockMvcRequestBuilders.put("/catalogo-formacion/{idCatFormacion}",view.getIdCatFormacion())
				.contentType(JSON)
				.content(MAPPER.writeValueAsString(view)))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());
	}
	@Test
	public void updateFormacionBadRequest() throws Exception {
		CatFormacionView view = createFormacionParaTest();
		mockMvc.perform(MockMvcRequestBuilders.put("/catalogo-formacion/{idCatFormacion}",view.getIdCatFormacion())
				.contentType(JSON)
				.content(MAPPER.writeValueAsString(new CatFormacionView())))
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andDo(MockMvcResultHandlers.print());
	}


	@Test
	public void updateFormacionNotFound() throws Exception {
		CatFormacionView view = new CatFormacionView();
		view.setFormAgrupacion("u_formacion_agrupacion");
		view.setFormDescripcion("U_fromacion_descripcion");
		view.setFormGrado(12);
		mockMvc.perform(MockMvcRequestBuilders.put("/catalogo-formacion/{idCatFormacion}",999999999)
				.contentType(JSON)
				.content(MAPPER.writeValueAsString(view)))
		.andExpect(MockMvcResultMatchers.status().isNotFound())
		.andDo(MockMvcResultHandlers.print());
	}


	@Test
	public void getFormacionOk() throws Exception {
		MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-formacion/search")
				.contentType(JSON)
				.params(params))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());

		params = new LinkedMultiValueMap<>();
		params.add("datosBusqueda", "");
		params.add("activo", "");
		params.add("page", "");
		params.add("size", "");
		params.add("orderColumn", "");
		params.add("orderType", "");
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-formacion/search")
				.contentType(JSON)
				.params(params))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());


		params = new LinkedMultiValueMap<>();
		params.add("datosBusqueda", "as");
		params.add("activo", "true");
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-formacion/search")
				.contentType(JSON)
				.params(params))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());


		params = new LinkedMultiValueMap<>();
		params.add("activo", "false");
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-formacion/search")
				.contentType(JSON)
				.params(params))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());

		//orderColumn
		params = new LinkedMultiValueMap<>();
		params.add("orderColumn", "idCatFormacion");
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-formacion/search")
				.contentType(JSON)
				.params(params))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());
		params = new LinkedMultiValueMap<>();
		params.add("orderColumn", "formDescripcion");
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-formacion/search")
				.contentType(JSON)
				.params(params))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());
		params = new LinkedMultiValueMap<>();
		params.add("orderColumn", "formAgrupacion");
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-formacion/search")
				.contentType(JSON)
				.params(params))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());
		params = new LinkedMultiValueMap<>();
		params.add("orderColumn", "formGrado");
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-formacion/search")
				.contentType(JSON)
				.params(params))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());
		params = new LinkedMultiValueMap<>();
		params.add("orderColumn", "activo");
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-formacion/search")
				.contentType(JSON)
				.params(params))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());
		//orderType
		params = new LinkedMultiValueMap<>();
		params.add("activo", "false");
		params.add("orderType", "desc");
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-formacion/search")
				.contentType(JSON)
				.params(params))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());

		//badRequest
		params = new LinkedMultiValueMap<>();
		params.add("orderColumn", "asfdadsfdsafasd");
		params.add("orderType", "deadssc");
		params.add("page", "-1");
		params.add("size", "-10");
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-formacion/search")
				.contentType(JSON)
				.params(params))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());


	}

	@Test
	public void getFormacionByIdOk() throws Exception {
		CatFormacionView view = createFormacionParaTest();
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-formacion/{idCatFormacion}",view.getIdCatFormacion())
				.contentType(JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());
	}


	@Test
	public void getFormacionByIdNotFound() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-formacion/{idCatFormacion}",999999999)
				.contentType(JSON))
		.andExpect(MockMvcResultMatchers.status().isNotFound())
		.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void getFormacionFinAll() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-formacion/findAll")
				.contentType(JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());
	}

	public CatFormacionView createFormacionParaTest() throws Exception {
		CatFormacionView view= new CatFormacionView();
		view.setFormAgrupacion("formacion_agrupacion");
		view.setFormDescripcion("fromacion_descripcion");
		view.setFormGrado(13);
		String response = mockMvc.perform(MockMvcRequestBuilders.post("/catalogo-formacion")
				.contentType(JSON)
				.content(MAPPER.writeValueAsString(view)))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andReturn().getResponse().getContentAsString();
		view = MAPPER.readValue(response, new TypeReference<CatFormacionView>() {});
		return view;
	}


}
