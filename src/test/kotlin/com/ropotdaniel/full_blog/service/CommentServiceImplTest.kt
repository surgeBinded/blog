package com.ropotdaniel.full_blog.service
import com.ropotdaniel.full_blog.dataaccessobject.CommentRepository
import com.ropotdaniel.full_blog.datatransferobject.comment.CommentDTO
import com.ropotdaniel.full_blog.datatransferobject.comment.ParentCommentDTO
import com.ropotdaniel.full_blog.domainobject.ArticleDO
import com.ropotdaniel.full_blog.domainobject.CommentDO
import com.ropotdaniel.full_blog.domainobject.UserDO
import com.ropotdaniel.full_blog.exceptions.WrongArticleException
import com.ropotdaniel.full_blog.mapper.CommentMapper
import com.ropotdaniel.full_blog.service.impl.CommentServiceImpl
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.given
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort

@ExtendWith(MockitoExtension::class)
class CommentServiceImplTest {

    private lateinit var commentRepository: CommentRepository
    private lateinit var commentMapper: CommentMapper
    private lateinit var commentService: CommentService

    @BeforeEach
    fun setUp() {
        commentRepository = mock(CommentRepository::class.java)
        commentMapper = mock(CommentMapper::class.java)
        commentService = CommentServiceImpl(commentRepository, commentMapper)
    }

    @Test
    fun `should get a list of articles`() {
        // given
        val articleId = 1L
        val pageNo = 0
        val pageSize = 10
        val sortBy = "createdAt"
        val sortDir = "asc"
        val commentDTO = givenCommentDTO(articleId = articleId)
        val commentDO = givenCommentDO(article = givenArticleDO(id = articleId))
        val commentDO1 = givenCommentDO(article = givenArticleDO(id = articleId), deleted = true)

        val pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending())
        val commentsPage = PageImpl(listOf(commentDO, commentDO1), pageable, 2)

        given(commentRepository.findByArticleId(articleId, pageable)).willReturn(commentsPage)
        given(commentMapper.toDTO(commentDO)).willReturn(commentDTO)
        given(commentMapper.toDTO(commentDO1)).willReturn(commentDTO) // Adjust as needed

        // when
        val result = commentService.getCommentsByArticleId(articleId, pageNo, pageSize, sortBy, sortDir)

        // then
        verify(commentRepository).findByArticleId(articleId, pageable)
        assertNotNull(result)
        assertEquals(2, result.content.size)
        assertEquals(2, result.totalElements)
        assertEquals(1, result.totalPages)
    }

    @Test
    fun `should add a new comment`() {
        // given
        val commentDTO = givenCommentDTO()
        val commentDO = givenCommentDO()

        given(commentMapper.toDO(commentDTO)).willReturn(commentDO)
        given(commentMapper.toDTO(commentDO)).willReturn(commentDTO)
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

        given(commentMapper.toDO(commentDTO)).willReturn(commentDO)

        // when
        assertThrows(WrongArticleException::class.java) {
            commentService.addComment(commentDTO)
        }

        // then
        verify(commentRepository, never()).save(commentDO)
    }

    @Test
    fun `should add like to the comment`() {
        // given
        val commentId = 1L
        val commentDO = givenCommentDO()
        val commentDTO = givenCommentDTO(likes = 11)

        given(commentRepository.findById(commentId)).willReturn(java.util.Optional.of(commentDO))
        given(commentRepository.save(commentDO)).willReturn(commentDO)
        given(commentMapper.toDTO(commentDO)).willReturn(commentDTO)

        // when
        val result = commentService.likeComment(commentId)

        // then
        verify(commentRepository).findById(commentId)
        verify(commentRepository).save(commentDO)
        assertNotNull(result)
        assertEquals(11, result.likes)
    }

    @Test
    fun `should add dislike to the comment`() {
        // given
        val commentId = 1L
        val commentDO = givenCommentDO()
        val commentDTO = givenCommentDTO(dislikes = 3)

        given(commentRepository.findById(commentId)).willReturn(java.util.Optional.of(commentDO))
        given(commentRepository.save(commentDO)).willReturn(commentDO)
        given(commentMapper.toDTO(commentDO)).willReturn(commentDTO)

        // when
        val result = commentService.dislikeComment(commentId)

        // then
        verify(commentRepository).findById(commentId)
        verify(commentRepository).save(commentDO)
        assertNotNull(result)
        assertEquals(3, result.dislikes)
    }

    @Test
    fun `should edit the comment content`() {
        // given
        val commentId = 1L
        val newContent = "New content"
        val commentDO = givenCommentDO()
        val commentDTO = givenCommentDTO(content = newContent)

        given(commentRepository.findById(commentId)).willReturn(java.util.Optional.of(commentDO))
        given(commentRepository.save(commentDO)).willReturn(commentDO)
        given(commentMapper.toDTO(commentDO)).willReturn(commentDTO)

        // when
        val result = commentService.editCommentContent(commentId, newContent)

        // then
        verify(commentRepository).findById(commentId)
        verify(commentRepository).save(commentDO)
        assertNotNull(result)
        assertEquals(newContent, result.content)
    }

    @Test
    fun `should delete the comment`() {
        // given
        val commentId = 1L
        val content = "This comment has been deleted."
        val commentDO = givenCommentDO()
        val commentDTO = givenCommentDTO(content = content, deleted = true)

        given(commentRepository.findById(commentId)).willReturn(java.util.Optional.of(commentDO))
        given(commentRepository.save(commentDO)).willReturn(commentDO)
        given(commentMapper.toDTO(commentDO)).willReturn(commentDTO)

        // when
        val result = commentService.deleteComment(commentId)

        // then
        verify(commentRepository).findById(commentId)
        verify(commentRepository).save(commentDO)
        assertNotNull(result)
        assertTrue(result.deleted)
        assertEquals(content, result.content)
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
            bannerImageUrl = bannerImageUrl,
            comments = mutableListOf(),
            user = mock(UserDO::class.java)
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
            deleted = deleted,
            user = mock(UserDO::class.java)
        )
    }
}