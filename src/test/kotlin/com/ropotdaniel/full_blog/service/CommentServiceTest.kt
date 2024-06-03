package com.ropotdaniel.full_blog.service
import com.ropotdaniel.full_blog.dataaccessobject.ArticleRepository
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
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.given
import java.util.*

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
         val commentDTO = CommentDTO(
              id = 1L,
              articleId = 1L,
              parentComment = null,
              content = "Test content",
              likes = 10,
              dislikes = 2,
              deleted = false
         )

       val commentDO = CommentDO(
           id = 1L,
           article = ArticleDO(
               id = 1L,
               title = "Test Article",
               content = "Test Content",
               bannerImageUrl = "url",
           ),
           parentComment = null,
           replies = mutableListOf(),
           content = "Test content",
           likes = 10,
           dislikes = 2,
           deleted = false
       )

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
        val commentDTO = CommentDTO(
            id = 1L,
            articleId = 1L,
            parentComment = ParentCommentDTO(
                id = 2L
            ),
            content = "Test content",
            likes = 10,
            dislikes = 2,
            deleted = false
        )

        val parentComment = CommentDO(
            id = 2L,
            article = ArticleDO(
                id = 2L,
                title = "Test Article",
                content = "Test Content",
                bannerImageUrl = "url",
            ),
            parentComment = null,
            replies = mutableListOf(),
            content = "Test content",
            likes = 10,
            dislikes = 2,
            deleted = false
        )

        val commentDO = CommentDO(
            id = 1L,
            article = ArticleDO(
                id = 1L,
                title = "Test Article",
                content = "Test Content",
                bannerImageUrl = "url",
            ),
            parentComment = parentComment,
            replies = mutableListOf(),
            content = "Test content",
            likes = 10,
            dislikes = 2,
            deleted = false
        )

        given(commentMapper.toCommentDO(commentDTO)).willReturn(commentDO)

        // when
        assertThrows(WrongArticleException::class.java) {
            commentService.addComment(commentDTO)
        }

        // then
        verify(commentRepository, never()).save(commentDO)
    }
}