package com.ropotdaniel.full_blog.service

import com.ropotdaniel.full_blog.dataaccessobject.ArticleRepository
import com.ropotdaniel.full_blog.dataaccessobject.UserRepository
import com.ropotdaniel.full_blog.datatransferobject.CreateArticleDTO
import com.ropotdaniel.full_blog.datatransferobject.ArticleDTO
import com.ropotdaniel.full_blog.datatransferobject.UserDTO
import com.ropotdaniel.full_blog.domainobject.ArticleDO
import com.ropotdaniel.full_blog.domainobject.UserDO
import com.ropotdaniel.full_blog.mapper.ArticleMapper
import com.ropotdaniel.full_blog.service.impl.ArticleServiceImpl
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import java.time.ZonedDateTime

class ArticleServiceImplTest {

    private val articleRepository: ArticleRepository = mock(ArticleRepository::class.java)
    private val userRepository: UserRepository = mock(UserRepository::class.java)
    private lateinit var articleService: ArticleService
    private lateinit var createArticleDTO: CreateArticleDTO

    private lateinit var articleDO: ArticleDO
    private lateinit var userDO: UserDO
    private lateinit var articleDTO: ArticleDTO

    @BeforeEach
    fun setup() {
        // Ensure ArticleMapper has access to userRepository
        ArticleMapper.userRepository = userRepository

        articleService = ArticleServiceImpl(articleRepository)

        userDO = UserDO(
            1L,
            "Test User",
            "",
            "",
            "",
            "",
            mutableListOf()
        )

        articleDO = ArticleDO(
            1L,
            "Test Title",
            "Test Content",
            "",
            mutableListOf(),
            userDO
        )

        articleDTO = ArticleDTO(
            1L,
            "Test Title",
            "Test Content",
            "",
            mutableListOf(),
            UserDTO(
                "Test User",
                "",
                "",
                "",
                null,
                mutableListOf(),
                mutableListOf(),
                false
            ),
            ZonedDateTime.now(),
        )

        createArticleDTO = CreateArticleDTO(
            title = "Test Title",
            content = "Test Content",
            authorId = 1L,
            bannerImageUrl = null
        )
    }

    @Test
    fun `should return article by id`() {
        // given
        val articleId = 1L
        given(articleRepository.getReferenceById(articleId)).willReturn(articleDO)

        // when
        val actualArticle = articleService.getArticle(articleId)

        // then
        assertThat("Returned article should be the same as the mocked one", actualArticle, `is`(articleDTO))
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
        assertThat("Should return the correct id of the first article", actualArticles.content[0].id, `is`(1L))
        assertThat("Should return the correct number of pages", actualArticles.totalPages, `is`(0))
        assertThat("Should return the correct amount of articles", actualArticles.totalElements, `is`(0))
        assertThat("Should return the correct page number", actualArticles.pageNo, `is`(0))
        assertThat("Should return the correct page size number", actualArticles.pageSize, `is`(0))
    }

    private fun listOfArticles(): List<ArticleDO> {
        val listOfArticleDO = mutableListOf<ArticleDO>()

        val user = UserDO(
            1L,
            "Test User",
            "",
            "",
            "",
            "",
            mutableListOf()
        )

        for (i in 1..100) {
            val articleDO = ArticleDO(
                i.toLong(),
                "Test Title $i",
                "Test Content $i",
                "",
                mutableListOf(),
                user
            )
            listOfArticleDO.add(articleDO)
        }
        return listOfArticleDO
    }

    @Test
    fun `should create article successfully`() {
        // given
        val createArticleDTO = CreateArticleDTO(
            title = "New Article",
            content = "This is a new article.",
            authorId = 1L,
            bannerImageUrl = "https://example.com/image.jpg"
        )

        val userDO = UserDO(
            1L,
            "Test User",
            "",
            "",
            "",
            "",
            mutableListOf()
        )

        val articleDO = ArticleDO(
            1L,
            "New Article",
            "This is a new article.",
            "https://example.com/image.jpg",
            mutableListOf(),
            userDO
        )

        val articleDTO = ArticleDTO(
            1L,
            "New Article",
            "This is a new article.",
            "https://example.com/image.jpg",
            mutableListOf(),
            UserDTO(
                "Test User",
                "",
                "",
                "",
                null,
                mutableListOf(),
                mutableListOf(),
                false
            ),
            ZonedDateTime.now()
        )

        given(userRepository.getReferenceById(1L)).willReturn(userDO)
        given(articleRepository.save(any(ArticleDO::class.java))).willReturn(articleDO)

        // when
        val actualArticle = articleService.createArticle(createArticleDTO)

        // then
        assertThat("Returned article should be the same as the mocked one", actualArticle, `is`(articleDTO))
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