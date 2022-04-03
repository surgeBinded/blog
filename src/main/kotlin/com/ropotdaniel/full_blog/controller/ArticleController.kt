package com.ropotdaniel.full_blog.controller

import com.ropotdaniel.full_blog.dataaccessobject.ArticleRepository
import com.ropotdaniel.full_blog.domainobject.Article
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["http://127.0.0.1:5500"])
@RestController
@RequestMapping("/api/v1")
class ArticleController @Autowired constructor(private val articleRepository: ArticleRepository) {

    @GetMapping("/articles")
    fun getArticles(): List<Article> = articleRepository.findAll()

    @PostMapping("/article")
    fun createArticle(
        @RequestBody article: Article
    ): Article {
        articleRepository.save(article)
        return article
    }
}