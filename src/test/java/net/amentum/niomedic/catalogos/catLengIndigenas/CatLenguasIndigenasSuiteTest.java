package net.amentum.niomedic.catalogos.catLengIndigenas;

import java.util.Random;

import org.junit.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.core.type.TypeReference;

import net.amentum.niomedic.catalogos.ConfigurationTest;
import net.amentum.niomedic.catalogos.views.CatLenguasIndigenasView;

public class CatLenguasIndigenasSuiteTest extends ConfigurationTest {
	Random r = new Random();
	@Test
	public void createlenguaIndigenasBadRequest() throws Exception {
		CatLenguasIndigenasView view = new CatLenguasIndigenasView();
		mockMvc.perform(MockMvcRequestBuilders.post("/catalogo-lenguas-indigenas")
				.contentType(JSON)
				.content(MAPPER.writeValueAsString(view)))
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andDo(MockMvcResultHandlers.print());
		//texto muy grande
		view = new CatLenguasIndigenasView();
		view.setLengDescripcion("kasjfhklajsdfhksdfklasdfjkshfjkasdkfaskldhfsdfkl{sdfaskldjfsdjfasdfjsdjf{asdkf{asdfk}ask{dkfa}s");
		mockMvc.perform(MockMvcRequestBuilders.post("/catalogo-lenguas-indigenas")
				.contentType(JSON)
				.content(MAPPER.writeValueAsString(view)))
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void createLenguaIndigenas() throws Exception{
		CatLenguasIndigenasView view = new CatLenguasIndigenasView();
		view.setLengDescripcion("ejemplo"+r.nextInt(9999999));
		view.setLengCatalogKey(r.nextInt(9999999));
		mockMvc.perform(MockMvcRequestBuilders.post("/catalogo-lenguas-indigenas")
				.contentType(JSON)
				.content(MAPPER.writeValueAsString(view)))
		.andExpect(MockMvcResultMatchers.status().isCreated())
		.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void updateLegunasIndigenasOK() throws Exception {
		CatLenguasIndigenasView Rview = lenguaIndigenasParaTest();
		Rview.setLengDescripcion("tomalaQueTeDoy"+r.nextInt(9999999));
		Rview.setLengCatalogKey(r.nextInt(9999999));
		Rview.setActivo(false);
		mockMvc.perform(MockMvcRequestBuilders.put("/catalogo-lenguas-indigenas/{idLenguasIndigenas}",Rview.getIdCatLenguasIndigenas())
				.contentType(JSON)
				.content(MAPPER.writeValueAsString(Rview)))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());

		Rview = lenguaIndigenasParaTest();
		Rview.setLengDescripcion("tomalaQueTeDoy"+r.nextInt(9999999));
		Rview.setLengCatalogKey(r.nextInt(9999999));
		Rview.setActivo(null);
		mockMvc.perform(MockMvcRequestBuilders.put("/catalogo-lenguas-indigenas/{idLenguasIndigenas}",Rview.getIdCatLenguasIndigenas())
				.contentType(JSON)
				.content(MAPPER.writeValueAsString(Rview)))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());
	}
	@Test
	public void updateLegunasIndigenasNotFound()throws Exception  {
		CatLenguasIndigenasView Rview = new CatLenguasIndigenasView();
		Rview.setLengDescripcion("tomalaQueTeDoy"+r.nextInt(9999999));
		Rview.setLengCatalogKey(r.nextInt(9999999));
		Rview.setActivo(false);
		mockMvc.perform(MockMvcRequestBuilders.put("/catalogo-lenguas-indigenas/{idLenguasIndigenas}",99999999)
				.contentType(JSON)
				.content(MAPPER.writeValueAsString(Rview)))
		.andExpect(MockMvcResultMatchers.status().isNotFound())
		.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void updateLegunasIndigenasBadRequest()throws Exception  {
		mockMvc.perform(MockMvcRequestBuilders.put("/catalogo-lenguas-indigenas/{idLenguasIndigenas}",999)
				.contentType(JSON)
				.content(MAPPER.writeValueAsString(new CatLenguasIndigenasView())))
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void getLegunasIndigenasFindAllOk()throws Exception  {
		CatLenguasIndigenasView Rview = new CatLenguasIndigenasView();
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-lenguas-indigenas/findAll")
				.contentType(JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void getLegunasIndigenasByIdOk()throws Exception  {
		CatLenguasIndigenasView Rview = lenguaIndigenasParaTest();
		Integer id= Rview.getIdCatLenguasIndigenas();
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-lenguas-indigenas/{idLenguasIndigenas}", id)
				.contentType(JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());
	}


	@Test
	public void getLegunasIndigenasByIdNotFound()throws Exception  {
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-lenguas-indigenas/{idLenguasIndigenas}", 99999999)
				.contentType(JSON))
		.andExpect(MockMvcResultMatchers.status().isNotFound())
		.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void searchLenguasIndigenas() throws Exception {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-lenguas-indigenas/search")
				.contentType(JSON)
				.params(params))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());

		params = new LinkedMultiValueMap<>();
		params.add("activo", "false");
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-lenguas-indigenas/search")
				.contentType(JSON)
				.params(params))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());

		params = new LinkedMultiValueMap<>();
		params.add("datosBusqueda", "9");
		params.add("activo", "false");
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-lenguas-indigenas/search")
				.contentType(JSON)
				.params(params))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());

		params = new LinkedMultiValueMap<>();
		params.add("datosBusqueda", "9");
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-lenguas-indigenas/search")
				.contentType(JSON)
				.params(params))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());

		//order column		
		params = new LinkedMultiValueMap<>();
		params.add("datosBusqueda", "9");
		params.add("orderColumn", "idCatLenguasIndigenas");
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-lenguas-indigenas/search")
				.contentType(JSON)
				.params(params))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());

		params = new LinkedMultiValueMap<>();
		params.add("datosBusqueda", "9");
		params.add("orderColumn", "lengCatalogKey");
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-lenguas-indigenas/search")
				.contentType(JSON)
				.params(params))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());


		params = new LinkedMultiValueMap<>();
		params.add("datosBusqueda", "9");
		params.add("orderColumn", "lengDescripcion");
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-lenguas-indigenas/search")
				.contentType(JSON)
				.params(params))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());
		params = new LinkedMultiValueMap<>();

		params.add("datosBusqueda", "9");
		params.add("orderColumn", "activo");
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-lenguas-indigenas/search")
				.contentType(JSON)
				.params(params))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());

		//order column no existente
		params = new LinkedMultiValueMap<>();
		params.add("datosBusqueda", "9");
		params.add("orderColumn", "dkfñflaskdjñlaskdf");
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-lenguas-indigenas/search")
				.contentType(JSON)
				.params(params))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());

		params = new LinkedMultiValueMap<>();
		params.add("page", "-10");
		params.add("size", "-10");
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-lenguas-indigenas/search")
				.contentType(JSON)
				.params(params))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());


		params = new LinkedMultiValueMap<>();
		params.add("datosBusqueda", "9");
		params.add("orderColumn", "false");
		params.add("page", "-10");
		params.add("size", "-10");
		params.add("orderType", "desc");
		mockMvc.perform(MockMvcRequestBuilders.get("/catalogo-lenguas-indigenas/search")
				.contentType(JSON)
				.params(params))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());
	}

	public CatLenguasIndigenasView lenguaIndigenasParaTest() throws Exception {
		CatLenguasIndigenasView view = new CatLenguasIndigenasView();
		view.setLengDescripcion("ejemplo"+r.nextInt(9999999));
		view.setLengCatalogKey(r.nextInt(9999999));
		view.setActivo(true);

		String response = mockMvc.perform(MockMvcRequestBuilders.post("/catalogo-lenguas-indigenas")
				.contentType(JSON)
				.content(MAPPER.writeValueAsString(view)))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andReturn().getResponse().getContentAsString();
		view = MAPPER.readValue(response,new TypeReference<CatLenguasIndigenasView>() {});
		return view;

	}
}
