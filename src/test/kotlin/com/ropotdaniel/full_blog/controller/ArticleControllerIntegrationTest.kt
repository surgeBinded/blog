package com.ropotdaniel.full_blog.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.ropotdaniel.full_blog.domainobject.ArticleDO
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

    private lateinit var articleDO: ArticleDO

    private lateinit var mapper: ObjectMapper

    @BeforeEach
    fun setup() {
        articleDO = ArticleDO(1L,
            "Test Title",
            "Test Content",
            "",
        )

        mapper = ObjectMapper()
        mapper.registerModule(JavaTimeModule())
    }

    @Test
    fun `should get article by id`() {
        `when`(articleService.getArticle(1L)).thenReturn(articleDO)

        mockMvc.perform(get("/api/v1/articles/1"))
            .andExpect(status().isOk)
    }

    @Test
    fun `should get all articles`() {
        `when`(articleService.getAllArticles()).thenReturn(listOf(articleDO).toMutableList())

        mockMvc.perform(get("/api/v1/articles"))
            .andExpect(status().isOk)
    }

    @Test
    fun `should create article`() {
        val newArticle = ArticleDO(
            id = 2L,
            title = "New Test Title",
            content = "New Test Content",
            bannerImageUrl = ""
        )

        `when`(articleService.createArticle(articleDO)).thenReturn(newArticle)

        mockMvc.perform(post("/api/v1/article")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(newArticle)))
            .andExpect(status().isOk)
    }

    @Test
    fun `should update article`() {
        val updatedArticle = ArticleDO(
            id = 1L,
            title = "Updated Test Title",
            content = "Updated Test Content",
            bannerImageUrl = ""
        )

        `when`(articleService.updateArticle(1, articleDO)).thenReturn(updatedArticle)

        mockMvc.perform(put("/api/v1/article/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(updatedArticle)))
            .andExpect(status().isOk)
    }

    @Test
    fun `should delete article`() {
        Mockito.doNothing().`when`(articleService).deleteArticle(Mockito.anyLong())

        mockMvc.perform(delete("/api/v1/article/1"))
            .andExpect(status().isOk)
    }
}