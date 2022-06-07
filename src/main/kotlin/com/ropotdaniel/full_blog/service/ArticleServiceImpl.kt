package com.ropotdaniel.full_blog.service

import com.ropotdaniel.full_blog.dataaccessobject.ArticleRepository
import com.ropotdaniel.full_blog.domainobject.Article
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ArticleServiceImpl @Autowired constructor(private val articleRepository: ArticleRepository) : ArticleService {

    override fun getArticle(id: Long): Article {
        return articleRepository.getById(id)
    }

    override fun getAllArticles(): MutableList<Article> {
        return articleRepository.findAll()
    }

    override fun createArticle(article: Article): Article {
        return articleRepository.save(article)
    }

    @Transactional
    override fun updateArticle(id: Long, modifiedArticle: Article): Article {
        val article = articleRepository.getById(id)
        article.bannerImage = modifiedArticle.bannerImage
        article.content = modifiedArticle.content
        article.dateCreated = modifiedArticle.dateCreated

        return article
    }

    override fun deleteArticle(id: Long) {
        articleRepository.deleteById(id)
    }
}