package com.ropotdaniel.full_blog.service
import com.ropotdaniel.full_blog.dataaccessobject.CommentRepository
import com.ropotdaniel.full_blog.datatransferobject.CommentDTO
import com.ropotdaniel.full_blog.datatransferobject.ParentCommentDTO
import com.ropotdaniel.full_blog.domainobject.ArticleDO
import com.ropotdaniel.full_blog.domainobject.CommentDO
import com.ropotdaniel.full_blog.exceptions.WrongArticleException
import com.ropotdaniel.full_blog.mapper.CommentMapper
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.given

@ExtendWith(MockitoExtension::class)
class CommentServiceTest {

    private lateinit var commentRepository: CommentRepository
    private lateinit var commentMapper: CommentMapper
    private lateinit var commentService: CommentService

    @BeforeEach
    fun setUp() {
        commentRepository = mock(CommentRepository::class.java)
        commentMapper = mock(CommentMapper::class.java)
        commentService = CommentService(commentRepository, commentMapper)
    }

    @Test
    fun `should add a new comment`() {
        // given
        val commentDTO = givenCommentDTO()
        val commentDO = givenCommentDO()

        given(commentMapper.toCommentDO(commentDTO)).willReturn(commentDO)
        given(commentMapper.toCommentDTO(commentDO)).willReturn(commentDTO)
        given(commentRepository.save(commentDO)).willReturn(commentDO)

        // when
        val result = commentService.addComment(commentDTO)

        // then
        verify(commentRepository).save(commentDO)
        assertNotNull(result)
    }

    @Test
    fun `should throw WrongArticleException when parent comment article is not the same as the comment article`() {
        // given
        val commentDTO = givenCommentDTO(
            parentComment = ParentCommentDTO(id = 2L)
        )
        val parentComment = givenCommentDO(
            id = 2L,
            article = givenArticleDO(id = 2L)
        )
        val commentDO = givenCommentDO(
            parentComment = parentComment
        )

        given(commentMapper.toCommentDO(commentDTO)).willReturn(commentDO)

        // when
        assertThrows(WrongArticleException::class.java) {
            commentService.addComment(commentDTO)
        }

        // then
        verify(commentRepository, never()).save(commentDO)
    }

    private fun givenCommentDTO(
        id: Long = 1L,
        articleId: Long = 1L,
        parentComment: ParentCommentDTO? = null,
        content: String = "Test content",
        likes: Int = 10,
        dislikes: Int = 2,
        deleted: Boolean = false
    ): CommentDTO {
        return CommentDTO(
            id = id,
            articleId = articleId,
            parentComment = parentComment,
            content = content,
            likes = likes,
            dislikes = dislikes,
            deleted = deleted
        )
    }

    private fun givenArticleDO(
        id: Long = 1L,
        title: String = "Test Article",
        content: String = "Test Content",
        bannerImageUrl: String = "url"
    ): ArticleDO {
        return ArticleDO(
            id = id,
            title = title,
            content = content,
            bannerImageUrl = bannerImageUrl
        )
    }

    private fun givenCommentDO(
        id: Long = 1L,
        article: ArticleDO = givenArticleDO(),
        parentComment: CommentDO? = null,
        content: String = "Test content",
        likes: Int = 10,
        dislikes: Int = 2,
        deleted: Boolean = false
    ): CommentDO {
        return CommentDO(
            id = id,
            article = article,
            parentComment = parentComment,
            replies = mutableListOf(),
            content = content,
            likes = likes,
            dislikes = dislikes,
            deleted = deleted
        )
    }
}