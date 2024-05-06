package com.ropotdaniel.full_blog.service

import com.ropotdaniel.full_blog.dataaccessobject.ArticleRepository
import com.ropotdaniel.full_blog.domainobject.ArticleDO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ArticleServiceImpl @Autowired constructor(private val articleRepository: ArticleRepository) : ArticleService {

    override fun getArticle(id: Long): ArticleDO {
        return articleRepository.getReferenceById(id)
    }

    override fun getAllArticles(): MutableList<ArticleDO> {
        return articleRepository.findAll()
    }

    override fun createArticle(articleDO: ArticleDO): ArticleDO {
        return articleRepository.save(articleDO)
    }

    @Transactional
    override fun updateArticle(id: Long, modifiedArticleDO: ArticleDO): ArticleDO {
        val article = articleRepository.getReferenceById(id)
        article.bannerImageUrl = modifiedArticleDO.bannerImageUrl
        article.content = modifiedArticleDO.content
        article.dateCreated = modifiedArticleDO.dateCreated

        return article
    }

    override fun deleteArticle(id: Long) {
        articleRepository.deleteById(id)
    }
}