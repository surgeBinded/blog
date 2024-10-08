package com.ropotdaniel.full_blog.controller

import com.ropotdaniel.full_blog.datatransferobject.article.CreateArticleDTO
import com.ropotdaniel.full_blog.datatransferobject.article.ArticleDTO
import com.ropotdaniel.full_blog.datatransferobject.article.UpdateArticleDTO
import com.ropotdaniel.full_blog.datatransferobject.response.ArticleResponse
import com.ropotdaniel.full_blog.service.ArticleService
import com.ropotdaniel.full_blog.util.Constants.Companion.DEFAULT_PAGE_NUMBER
import com.ropotdaniel.full_blog.util.Constants.Companion.DEFAULT_PAGE_SIZE
import com.ropotdaniel.full_blog.util.Constants.Companion.DEFAULT_SORT_BY
import com.ropotdaniel.full_blog.util.Constants.Companion.DEFAULT_SORT_DIRECTION
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize

@RestController
@RequestMapping("/api/v1")
class ArticleController @Autowired constructor(private val articleService: ArticleService) {

    @GetMapping("/articles/{id}")
    fun getArticle(@PathVariable id: Long): ResponseEntity<ArticleDTO> {
        return ResponseEntity.ok(articleService.getArticle(id))
    }

/*
    * GET /api/v1/articles?pageNo=0&pageSize=10&sortBy=id&sortDir=asc
    *
    * Returns a paginated list of articles
    *
    * pageNo: the page number to fetch
    * pageSize: the number of articles to fetch per page
    * sortBy: the field to sort by
    * sortDir: the direction to sort by (asc or desc)
    *
    * Returns: ArticleResponse
    *
    * Example response:
    * {
    *  "content": [
    *       {
    *       "id": 1,
    *       "title": "Title",
    *       "content": "Content",
    *       "bannerImageUrl": "https://example.com/image.jpg",
    *       "dateCreated": "2021-08-01T00:00:00Z"
    *       }
    *   ],
    *   "pageNo": 0,
    *   "pageSize": 10,
    *   "totalElements": 1,
    *   "totalPages": 1,
    *   "last": true
    * }
 */
    @GetMapping("/articles")
    fun getArticles(@RequestParam(value = "pageNo", defaultValue = DEFAULT_PAGE_NUMBER.toString(), required = false) pageNo: Int,
                    @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE.toString(), required = false) pageSize: Int,
                    @RequestParam(value = "sortBy", defaultValue = DEFAULT_SORT_BY, required = false) sortBy: String,
                    @RequestParam(value = "sortDir", defaultValue = DEFAULT_SORT_DIRECTION, required = false) sortDir: String): ResponseEntity<ArticleResponse>
    {
        return ResponseEntity.ok(articleService.getAllArticles(pageNo, pageSize, sortBy, sortDir))
    }

    /*
    * POST /api/v1/article
    * {
    *   "title": "Title",
    *   "content": "Content",
    *   "bannerImageUrl": "https://example.com/image.jpg"
    * }
    */
    @PostMapping("/article")
    fun createArticle(@Valid @RequestBody createArticleDTO: CreateArticleDTO): ResponseEntity<ArticleDTO> {
        val createdArticle = articleService.createArticle(createArticleDTO)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(createdArticle)
    }

    /*
    * updates the article with the given id
    * NOTE: the dateCreated field is not updated
    *
    * PUT /api/v1/article/1
    * {
    *   "title": "Title",
    *   "content": "Content",
    *   "bannerImageUrl": "https://example.com/image.jpg"
    * }
    * */
    @PreAuthorize("hasPermission(#id, 'ArticleDO', 'edit')")
    @PutMapping("/article/{id}")
    fun updateArticle(@PathVariable id: Long,
                      @Valid @RequestBody updateArticleDTO: UpdateArticleDTO): ResponseEntity<ArticleDTO>{
        return ResponseEntity.ok(articleService.updateArticle(id, updateArticleDTO))
    }

    @PreAuthorize("hasPermission(#id, 'ArticleDO', 'delete')")
    @DeleteMapping("/article/{id}")
    fun deleteArticle(@PathVariable id: Long): ResponseEntity<String> {
        articleService.deleteArticle(id)
        return ResponseEntity.ok("Resource successfully deleted")
    }
}