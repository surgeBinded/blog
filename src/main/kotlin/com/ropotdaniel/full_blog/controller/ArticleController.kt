package com.ropotdaniel.full_blog.controller

import com.ropotdaniel.full_blog.domainobject.ArticleDO
import com.ropotdaniel.full_blog.service.ArticleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import jakarta.validation.Valid;

@CrossOrigin(origins = ["*"])
@RestController
@RequestMapping("/api/v1")
class ArticleController @Autowired constructor(private val articleService: ArticleService) {

    @GetMapping("/articles/{id}")
    fun getArticle(@PathVariable id: Long): ArticleDO = articleService.getArticle(id)

    @GetMapping("/articles")
    fun getArticles(): List<ArticleDO> = articleService.getAllArticles()

    /*
    * POST /api/v1/article
    * {
    *   "title": "Title",
    *   "content": "Content",
    *   "bannerImageUrl": "https://example.com/image.jpg"
    * }
    */
    @PostMapping("/article")
    fun createArticle(@Valid @RequestBody articleDO: ArticleDO): ArticleDO = articleService.createArticle(articleDO)

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
    @PutMapping("/article/{id}")
    fun updateArticle(@PathVariable id: Long, @Valid @RequestBody articleDO: ArticleDO): ArticleDO = articleService.updateArticle(id, articleDO)

    @DeleteMapping("/article/{id}")
    fun deleteArticle(@PathVariable id: Long) = articleService.deleteArticle(id)
}