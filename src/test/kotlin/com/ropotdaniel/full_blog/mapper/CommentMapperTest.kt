package com.ropotdaniel.full_blog.mapper

import com.ropotdaniel.full_blog.dataaccessobject.ArticleRepository
import com.ropotdaniel.full_blog.dataaccessobject.CommentRepository
import com.ropotdaniel.full_blog.datatransferobject.CommentDTO
import com.ropotdaniel.full_blog.datatransferobject.ParentCommentDTO
import com.ropotdaniel.full_blog.datatransferobject.ReplyDTO
import com.ropotdaniel.full_blog.domainobject.ArticleDO
import com.ropotdaniel.full_blog.domainobject.CommentDO
import com.ropotdaniel.full_blog.domainobject.UserDO
import com.ropotdaniel.full_blog.exceptions.CommentNotFoundException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.`when`
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@ExtendWith(SpringExtension::class)
@SpringBootTest
internal class CommentMapperTest {

    @MockBean
    private lateinit var commentRepository: CommentRepository

    @MockBean
    private lateinit var articleRepository: ArticleRepository

    private lateinit var user: UserDO
    private lateinit var commentDO: CommentDO
    private lateinit var commentDTO: CommentDTO

    @BeforeEach
    fun setUp() {
        user = UserDO(
            1L,
            "Test User",
            "",
            "",
            "",
            "",
            mutableListOf()
        )

        val article = ArticleDO(
            id = 1L,
            title = "Test Article",
            content = "Test Content",
            bannerImageUrl = "url",
            mutableListOf(),
            user = user
        )

        commentDO = CommentDO(
            id = 1L,
            article = article,
            parentComment = null,
            replies = mutableListOf(),
            content = "Test content",
            likes = 10,
            dislikes = 2,
            deleted = false,
            user = user
        )

        commentDTO = CommentDTO(
            id = 1L,
            articleId = 1L,
            parentComment = null,
            replies = mutableListOf(),
            content = "Test content",
            likes = 10,
            dislikes = 2,
            deleted = false
        )
    }

    @Test
    fun toCommentDTO_Positive() {
        val dto = CommentMapper.toDTO(commentDO)

        assertNotNull(dto)
        assertEquals(commentDO.id, dto.id)
        assertEquals(commentDO.article.id, dto.articleId)
        assertEquals(commentDO.content, dto.content)
        assertEquals(commentDO.likes, dto.likes)
        assertEquals(commentDO.dislikes, dto.dislikes)
        assertEquals(commentDO.deleted, dto.deleted)
        assertTrue(dto.replies.isEmpty())
    }

    @Test
    fun toCommentDO_Positive() {
        val article = ArticleDO(
            id = 1L,
            title = "Test Article",
            content = "Test Content",
            bannerImageUrl = "url",
            mutableListOf(),
            user = user
        )
        `when`(articleRepository.getReferenceById(commentDTO.articleId)).thenReturn(article)

        val comment = CommentMapper.toDO(commentDTO)

        assertNotNull(comment)
        assertEquals(commentDTO.id, comment.id)
        assertEquals(commentDTO.articleId, comment.article.id)
        assertEquals(commentDTO.content, comment.content)
        assertEquals(commentDTO.likes, comment.likes)
        assertEquals(commentDTO.dislikes, comment.dislikes)
        assertEquals(commentDTO.deleted, comment.deleted)
        assertTrue(comment.replies.isEmpty())
    }

    @Test
    fun toCommentDO_Negative_ParentNotFound() {
        val dtoWithParent = CommentDTO(
            id = 1L,
            articleId = 1L,
            parentComment = ParentCommentDTO(2L),
            replies = mutableListOf(),
            content = "Test content",
            likes = 10,
            dislikes = 2,
            deleted = false
        )

        val article = ArticleDO(
            id = 1L,
            title = "Test Article",
            content = "Test Content",
            bannerImageUrl = "url",
            mutableListOf(),
            user = user
        )
        `when`(articleRepository.getReferenceById(dtoWithParent.articleId)).thenReturn(article)
        `when`(commentRepository.findById(2L)).thenReturn(Optional.empty())

        val exception = assertThrows(CommentNotFoundException::class.java) {
            CommentMapper.toDO(dtoWithParent)
        }

        assertEquals("Parent comment not found", exception.message)
    }

    @Test
    fun toCommentDO_Negative_ReplyNotFound() {
        val dtoWithReplies = CommentDTO(
            id = 1L,
            articleId = 1L,
            parentComment = null,
            replies = mutableListOf(ReplyDTO(2L)),
            content = "Test content",
            likes = 10,
            dislikes = 2,
            deleted = false
        )

        val article = ArticleDO(
            id = 1L,
            title = "Test Article",
            content = "Test Content",
            bannerImageUrl = "url",
            mutableListOf(),
            user = user
        )
        `when`(articleRepository.getReferenceById(dtoWithReplies.articleId)).thenReturn(article)
        `when`(commentRepository.findById(2L)).thenReturn(Optional.empty())

        val exception = assertThrows(CommentNotFoundException::class.java) {
            CommentMapper.toDO(dtoWithReplies)
        }

        assertEquals("Reply not found", exception.message)
    }
}
