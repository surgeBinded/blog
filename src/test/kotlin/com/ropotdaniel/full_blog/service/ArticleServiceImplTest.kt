package com.ropotdaniel.full_blog.service

import com.ropotdaniel.full_blog.dataaccessobject.ArticleRepository
import com.ropotdaniel.full_blog.datatransferobject.ArticleDTO
import com.ropotdaniel.full_blog.domainobject.ArticleDO
import com.ropotdaniel.full_blog.mapper.CommentMapper
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import java.time.ZonedDateTime

class ArticleServiceImplTest {

    private val articleRepository: ArticleRepository = mock(ArticleRepository::class.java)
    private val commentMapper: CommentMapper = mock(CommentMapper::class.java)
    private val articleService: ArticleService = ArticleServiceImpl(articleRepository, commentMapper)
    private val articleDO: ArticleDO = mock(ArticleDO::class.java)
    private val articleDTO: ArticleDTO = mock(ArticleDTO::class.java)

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
    fun `should return all articles paginated and sorted by id`() {
        // given
        val articles = mock(Page::class.java) as Page<ArticleDO>
        given(articles.content).willReturn(listOfArticles())
        given(articleRepository.findAll(PageRequest.of(0, 10, Sort.by("id").ascending()))).willReturn(articles)

        // when
        val actualArticles = articleService.getAllArticles(0, 10, "id", "asc")

        // then
        assertThat("Should return the correct id of the first article", actualArticles.content.get(0).id, `is`(1L))
        assertThat("Should return the correct number of pages", actualArticles.totalPages, `is`(0))
        assertThat("Should return the correct amount of articles", actualArticles.totalElements, `is`(0))
        assertThat("Should return the correct page number", actualArticles.pageNo, `is`(0))
        assertThat("Should return the correct page size number", actualArticles.pageSize, `is`(0))
    }

    private fun listOfArticles() : List<ArticleDO> {
        val listOfArticleDO = mutableListOf<ArticleDO>()
        for (i in 1..100) {
            val articleDO = ArticleDO(
                i.toLong(),
                "Test Title $i",
                "Test Content $i",
                ""
            )
            listOfArticleDO.add(articleDO)
        }
        return listOfArticleDO
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