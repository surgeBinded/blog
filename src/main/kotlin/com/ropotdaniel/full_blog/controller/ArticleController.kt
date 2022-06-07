package com.ropotdaniel.full_blog.controller

import com.ropotdaniel.full_blog.domainobject.Article
import com.ropotdaniel.full_blog.service.ArticleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["http://127.0.0.1:5500", "http://127.0.0.1:5501", "http://127.0.0.1:5502"])
@RestController
@RequestMapping("/api/v1")
class ArticleController @Autowired constructor(private val articleService: ArticleService) {

    @GetMapping("/articles")
    fun getArticles(): List<Article> = articleService.getAllArticles()

    @PostMapping("/article")
    fun createArticle(@RequestBody article: Article): Article = articleService.createArticle(article)

    @PutMapping("/article/{id}")
    fun updateArticle(@PathVariable id: Long, @RequestBody article: Article): Article = articleService.updateArticle(id, article)

    @DeleteMapping("/article/{id}")
    fun deleteArticle(@PathVariable id: Long) = articleService.deleteArticle(id)
}