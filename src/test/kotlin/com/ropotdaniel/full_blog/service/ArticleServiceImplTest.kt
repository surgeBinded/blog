package com.ropotdaniel.full_blog.service

import com.ropotdaniel.full_blog.dataaccessobject.ArticleRepository
import com.ropotdaniel.full_blog.domainobject.ArticleDO
import com.ropotdaniel.full_blog.mapper.CommentMapper
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock

class ArticleServiceImplTest {

    private val articleRepository: ArticleRepository = mock(ArticleRepository::class.java)
    private val commentMapper: CommentMapper = mock(CommentMapper::class.java)
    private val articleService: ArticleService = ArticleServiceImpl(articleRepository, commentMapper)
    private val articleDO: ArticleDO = mock(ArticleDO::class.java)

    @Test
    fun `should return article by id`() {
        // given
        val articleId = 1L
        given(articleRepository.getReferenceById(articleId)).willReturn(articleDO)

        // when
        val actualArticle = articleService.getArticle(articleId)

        // then
        assertThat("Returned article should be the same as the mocked one", actualArticle, `is`(articleDO))
    }

    @Test
    fun `should return all articles`() {
        // given
        val articles = mutableListOf(articleDO)
        given(articleRepository.findAll()).willReturn(articles)

        // when
        val actualArticles = articleService.getAllArticles(0, 10, "id", "asc")

        // then
        assertThat("Returned articles should be the same as the mocked ones", actualArticles, `is`(articles))
    }

    @Test
    fun `should create article`() {
        // given
        given(articleRepository.save(articleDO)).willReturn(articleDO)

        // when
        val actualArticle = articleService.createArticle(articleDO)

        // then
        assertThat("Returned article should be the same as the mocked one", actualArticle, `is`(articleDO))
    }

    @Test
    fun `should update article`() {
        // given
        val modifiedArticleDO = mock(ArticleDO::class.java)
        val articleId = 1L
        given(articleRepository.getReferenceById(articleId)).willReturn(articleDO)
        given(modifiedArticleDO.bannerImageUrl).willReturn("new banner image url")
        given(modifiedArticleDO.content).willReturn("new content")
        given(modifiedArticleDO.dateCreated).willReturn(null)

        // when
        val actualArticle = articleService.updateArticle(articleId, modifiedArticleDO)

        // then
        assertThat("Returned article should be the same as the mocked one", actualArticle, `is`(articleDO))
    }
}