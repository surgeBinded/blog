package com.ropotdaniel.full_blog.mapper

import com.ropotdaniel.full_blog.dataaccessobject.ArticleRepository
import com.ropotdaniel.full_blog.dataaccessobject.CommentRepository
import com.ropotdaniel.full_blog.datatransferobject.CommentDTO
import com.ropotdaniel.full_blog.datatransferobject.ParentCommentDTO
import com.ropotdaniel.full_blog.datatransferobject.ReplyDTO
import com.ropotdaniel.full_blog.domainobject.ArticleDO
import com.ropotdaniel.full_blog.domainobject.CommentDO
import com.ropotdaniel.full_blog.exceptions.CommentNotFoundException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*


@ExtendWith(MockitoExtension::class)
internal class CommentMapperTest {

    @Mock
    private lateinit var commentRepository: CommentRepository

    @Mock
    private lateinit var articleRepository: ArticleRepository

    @InjectMocks
    private lateinit var commentMapper: CommentMapper

    private lateinit var commentDO: CommentDO
    private lateinit var commentDTO: CommentDTO

    @BeforeEach
    fun setUp() {
        val article = ArticleDO(
            id = 1L,
            title = "Test Article",
            content = "Test Content",
            bannerImageUrl = "url",
        )

        commentDO = CommentDO(
            id = 1L,
            article = article,
            parentComment = null,
            replies = mutableListOf(),
            content = "Test content",
            likes = 10,
            dislikes = 2,
            deleted = false
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
        val dto = commentMapper.toCommentDTO(commentDO)

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
        )
        `when`(articleRepository.getReferenceById(commentDTO.articleId)).thenReturn(article)

        val comment = commentMapper.toCommentDO(commentDTO)

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
    fun toCommentDO_Negative_ParentCommentNotFound() {
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
        )
        `when`(articleRepository.getReferenceById(dtoWithParent.articleId)).thenReturn(article)
        `when`(commentRepository.findById(2L)).thenReturn(Optional.empty())

        val exception = assertThrows(CommentNotFoundException::class.java) {
            commentMapper.toCommentDO(dtoWithParent)
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
        )
        `when`(articleRepository.getReferenceById(dtoWithReplies.articleId)).thenReturn(article)
        `when`(commentRepository.findById(2L)).thenReturn(Optional.empty())

        val exception = assertThrows(CommentNotFoundException::class.java) {
            commentMapper.toCommentDO(dtoWithReplies)
        }

        assertEquals("Reply not found", exception.message)
    }
}