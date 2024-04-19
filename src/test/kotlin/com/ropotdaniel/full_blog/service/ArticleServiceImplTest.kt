package com.ropotdaniel.full_blog.service

import com.ropotdaniel.full_blog.dataaccessobject.ArticleRepository
import com.ropotdaniel.full_blog.domainobject.Article
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock

class ArticleServiceImplTest {

    private val articleRepository: ArticleRepository = mock(ArticleRepository::class.java)
    private val articleService: ArticleService = ArticleServiceImpl(articleRepository)
    private val article: Article = mock(Article::class.java)

    @Test
    fun `should return article by id`() {
        // given
        val articleId = 1L
        given(articleRepository.getReferenceById(articleId)).willReturn(article)

        // when
        val actualArticle = articleService.getArticle(articleId)

        // then
        assertThat("Returned article should be the same as the mocked one", actualArticle, `is`(article))
    }

    @Test
    fun `should return all articles`() {
        // given
        val articles = mutableListOf(article)
        given(articleRepository.findAll()).willReturn(articles)

        // when
        val actualArticles = articleService.getAllArticles()

        // then
        assertThat("Returned articles should be the same as the mocked ones", actualArticles, `is`(articles))
    }

    @Test
    fun `should create article`() {
        // given
        given(articleRepository.save(article)).willReturn(article)

        // when
        val actualArticle = articleService.createArticle(article)

        // then
        assertThat("Returned article should be the same as the mocked one", actualArticle, `is`(article))
    }

    @Test
    fun `should update article`() {
        // given
        val modifiedArticle = mock(Article::class.java)
        val articleId = 1L
        given(articleRepository.getReferenceById(articleId)).willReturn(article)
        given(modifiedArticle.bannerImageUrl).willReturn("new banner image url")
        given(modifiedArticle.content).willReturn("new content")
        given(modifiedArticle.dateCreated).willReturn(null)

        // when
        val actualArticle = articleService.updateArticle(articleId, modifiedArticle)

        // then
        assertThat("Returned article should be the same as the mocked one", actualArticle, `is`(article))
    }
}