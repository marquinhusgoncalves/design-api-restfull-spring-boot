package com.marquinhus.libraryapi.api.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marquinhus.libraryapi.api.dto.BookDto;
import com.marquinhus.libraryapi.api.exception.BusinessException;
import com.marquinhus.libraryapi.model.entiy.Book;
import com.marquinhus.libraryapi.service.BookService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest
@AutoConfigureMockMvc
public class BookControllerTest {

    static String API_BOOK = "/api/books";

    @Autowired
    MockMvc mvc;

    @MockBean
    BookService service;

    @Test
    @DisplayName("Deve criar umm livro com sucesso")
    public void createBookTest() throws Exception {

        BookDto dto = createNewBook();
        Book saveBook = Book.builder().id(101L).author("Arthur").title("As aventuras").isbn("001").build();

        BDDMockito.given(service.save(Mockito.any(Book.class))).willReturn(saveBook);
        String json = new ObjectMapper().writeValueAsString(dto);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API_BOOK)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").value(101))
                .andExpect(jsonPath("title").value(dto.getTitle()))
                .andExpect(jsonPath("author").value(dto.getAuthor()))
                .andExpect(jsonPath("isbn").value(dto.getIsbn()));

    }

    @Test
    @DisplayName("Deve lançar erro de validação não houver dados suficientes para criação do livro")
    public void createInvalidBookTest() throws Exception {

        String json = new ObjectMapper().writeValueAsString(new BookDto());
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API_BOOK)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors", hasSize(3)));
    }

    @Test
    @DisplayName("Deve lançar erro ao tentar cadastrar um livro com isbn já utilizado por outro")
    public void createBookWithDuplicatedIsbn() throws Exception {

        BookDto dto = createNewBook();
        String json = new ObjectMapper().writeValueAsString(dto);
        String messageError = "Isbn já cadastrado";
        BDDMockito.given(service.save(Mockito.any(Book.class)))
                .willThrow(new BusinessException(messageError));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API_BOOK)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors", hasSize(1)))
                .andExpect(jsonPath("errors[0]").value(messageError));
    }

    @Test
    @DisplayName("Deve obter informações de um livro")
    public void getBookDetailsTest() throws Exception {
        // cenario
        Long id = 1L;

        Book book = Book.builder()
                .id(id)
                .title(createNewBook().getTitle())
                .author(createNewBook().getAuthor())
                .isbn(createNewBook().getIsbn())
                .build();

        BDDMockito.given(service.getById(id)).willReturn(Optional.of(book));

        // execucao
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(API_BOOK.concat("/" + id))
                .accept(MediaType.APPLICATION_JSON);

        // verificacao
        mvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(id))
                .andExpect(jsonPath("title").value(createNewBook().getTitle()))
                .andExpect(jsonPath("author").value(createNewBook().getAuthor()))
                .andExpect(jsonPath("isbn").value(createNewBook().getIsbn()));
    }

    @Test
    @DisplayName("Deve retornar resource not found quando o livro procurado não existir")
    public void bookNotFound() throws Exception {
        // cenario
        BDDMockito.given(service.getById(Mockito.anyLong())).willReturn(Optional.empty());

        // execucao
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(API_BOOK.concat("/" + 1))
                .accept(MediaType.APPLICATION_JSON);

        // verificação
        mvc
                .perform(request)
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve deletar um livro")
    public void deleteBookTest() throws Exception{
        // cenario
        BDDMockito.given(service.getById(Mockito.anyLong())).willReturn(Optional.of(Book.builder().id(1L).build()));

        // execucao
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(API_BOOK.concat("/" + 1));

        mvc.perform(request)
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Deve retornar resource not found quando não encontrar o livro para deletar")
    public void deleteInexistentBookTest() throws Exception{
        // cenario
        BDDMockito.given(service.getById(Mockito.anyLong())).willReturn(Optional.empty());

        // execucao
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(API_BOOK.concat("/" + 1));

        mvc.perform(request)
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve atualizar um livro")
    public void updateBookTest() throws Exception {
        Long id = 1l;
        String json = new ObjectMapper().writeValueAsString(createNewBook());

        Book updatingBook = Book.builder().id(1l).title("some title").author("some author").isbn("321").build();
        BDDMockito.given( service.getById(id) ).willReturn( Optional.of(updatingBook) );
        Book updatedBook = Book.builder().id(id).author("Arthur").title("As aventuras").isbn("321").build();
        BDDMockito.given(service.update(updatingBook)).willReturn(updatedBook);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(API_BOOK.concat("/" + 1))
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform( request )
                .andExpect( status().isOk() )
                .andExpect( jsonPath("id").value(id) )
                .andExpect( jsonPath("title").value(createNewBook().getTitle()) )
                .andExpect( jsonPath("author").value(createNewBook().getAuthor()) )
                .andExpect( jsonPath("isbn").value("321") );
    }

    @Test
    @DisplayName("Deve retornar 404 ao tentar atualizar um livro inexistente")
    public void updateInexistentBookTest() throws Exception {

        String json = new ObjectMapper().writeValueAsString(createNewBook());
        BDDMockito.given( service.getById(Mockito.anyLong()) )
                .willReturn( Optional.empty() );

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(API_BOOK.concat("/" + 1))
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform( request )
                .andExpect( status().isNotFound() );
    }

    @Test
    @DisplayName("Deve filtrar livros")
    public void findBooksTest() throws Exception{

        Long id = 1l;

        Book book = Book.builder()
                .id(id)
                .title(createNewBook().getTitle())
                .author(createNewBook().getAuthor())
                .isbn(createNewBook().getIsbn())
                .build();

        BDDMockito.given( service.find(Mockito.any(Book.class), Mockito.any(Pageable.class)) )
                .willReturn( new PageImpl<Book>( Arrays.asList(book), PageRequest.of(0,100), 1 )   );

        String queryString = String.format("?title=%s&author=%s&page=0&size=100",
                book.getTitle(), book.getAuthor());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(API_BOOK.concat(queryString))
                .accept(MediaType.APPLICATION_JSON);

        mvc
                .perform( request )
                .andExpect( status().isOk() )
                .andExpect( jsonPath("content", Matchers.hasSize(1)))
                .andExpect( jsonPath("totalElements").value(1) )
                .andExpect( jsonPath("pageable.pageSize").value(100) )
                .andExpect( jsonPath("pageable.pageNumber").value(0));
    }


    private BookDto createNewBook() {
        return BookDto.builder().author("Arthur").title("As aventuras").isbn("001").build();
    }
}