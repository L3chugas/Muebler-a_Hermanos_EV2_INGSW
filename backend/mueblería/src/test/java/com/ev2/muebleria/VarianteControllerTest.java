package com.ev2.muebleria;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ev2.muebleria.Controladores.VarianteController;
import com.ev2.muebleria.Modelos.Variante;
import com.ev2.muebleria.Repositorios.VarianteRepository;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = VarianteController.class, excludeAutoConfiguration = {
    DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class, JpaRepositoriesAutoConfiguration.class
})
public class VarianteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VarianteRepository varianteRepository;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void lista_variantes_devuelveListaOK() throws Exception {
        Variante v1 = new Variante();
        v1.setId_variante(1L);
        v1.setNombre_variante("Premium");
        v1.setPrecio_adicional(25.0);

        when(varianteRepository.findAll()).thenReturn(List.of(v1));

        mockMvc.perform(get("/api/variantes").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id_variante").value(1));
    }

    @Test
    void crear_variante_devuelveCreated() throws Exception {
        Variante v = new Variante();
        v.setNombre_variante("Premium");
        v.setPrecio_adicional(25.0);

        Variante saved = new Variante();
        saved.setId_variante(2L);
        saved.setNombre_variante("Premium");
        saved.setPrecio_adicional(25.0);

        when(varianteRepository.save(v)).thenReturn(saved);

        String json = mapper.writeValueAsString(v);

        mockMvc.perform(post("/api/variantes").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_variante").value(2));
    }

}
