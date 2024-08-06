package com.ropotdaniel.full_blog.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.ropotdaniel.full_blog.datatransferobject.article.ArticleDTO
import com.ropotdaniel.full_blog.datatransferobject.article.CreateArticleDTO
import com.ropotdaniel.full_blog.datatransferobject.article.UpdateArticleDTO
import com.ropotdaniel.full_blog.datatransferobject.response.ArticleResponse
import com.ropotdaniel.full_blog.datatransferobject.user.UserDTO
import com.ropotdaniel.full_blog.domainobject.UserDO
import com.ropotdaniel.full_blog.service.ArticleService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.ZonedDateTime

@WebMvcTest(ArticleController::class)
class ArticleControllerIntegrationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var articleService: ArticleService

    private lateinit var user: UserDO
    private lateinit var updateArticleDTO: UpdateArticleDTO
    private lateinit var articleDTO: ArticleDTO
    private lateinit var createArticleDTO: CreateArticleDTO

    private lateinit var mapper: ObjectMapper

    @BeforeEach
    fun setup() {
        user = UserDO(
            1L,
            "Test User",
            "",
            "",
            "",
            "",
            mutableListOf()
        )

        articleDTO = ArticleDTO(
            1L,
            "Test Title",
            "Test Content",
            "",
            mutableListOf(),
            author = UserDTO(
                "Test User",
                "",
                "",
                "",
                "",
                mutableListOf(),
                mutableListOf(),
                false
            ),
            ZonedDateTime.now(),
            ZonedDateTime.now()
        )

        createArticleDTO = CreateArticleDTO(
            "Test Title",
            "Test Content",
            "",
            1L
        )

        updateArticleDTO = UpdateArticleDTO(
            title = "Updated Test Title",
            content = "Updated Test Content",
            bannerImageUrl = "",
            dateUpdated = ZonedDateTime.now(),
        )

        mapper = ObjectMapper()
        mapper.registerModule(JavaTimeModule())
    }

    @Test
    fun `should get article by id`() {
        `when`(articleService.getArticle(1L)).thenReturn(articleDTO)

        mockMvc.perform(get("/api/v1/articles/1"))
            .andExpect(status().isOk)
    }

    @Test
    fun `should get all articles`() {
        val response = ArticleResponse(
            listOf(articleDTO).toMutableList(),
            0,
            10,
            1,
            1,
            true
        )

        `when`(articleService.getAllArticles(0, 10, "id", "asc")).thenReturn(response)

        mockMvc.perform(get("/api/v1/articles"))
            .andExpect(status().isOk)
    }

    @Test
    fun `should create article`() {
        val newArticle = ArticleDTO(
            id = 1L,
            title = "New Test Title",
            content = "New Test Content",
            bannerImageUrl = "",
            author = UserDTO(
                "Test User",
                "",
                "",
                "",
                "",
                mutableListOf(),
                mutableListOf(),
                false
            ),
            dateUpdated = ZonedDateTime.now(),
            dateCreated = ZonedDateTime.now(),
            comments = mutableListOf()
        )

        `when`(articleService.createArticle(createArticleDTO)).thenReturn(newArticle)

        mockMvc.perform(
            post("/api/v1/article")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(newArticle))
        )
            .andExpect(status().isOk)
    }

    @Test
    fun `should update article`() {

        `when`(articleService.updateArticle(1, updateArticleDTO)).thenReturn(articleDTO)

        mockMvc.perform(
            put("/api/v1/article/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(articleDTO))
        )
            .andExpect(status().isOk)
    }

    @Test
    fun `should delete article`() {
        Mockito.doNothing().`when`(articleService).deleteArticle(Mockito.anyLong())

        mockMvc.perform(delete("/api/v1/article/1"))
            .andExpect(status().isOk)
    }
}