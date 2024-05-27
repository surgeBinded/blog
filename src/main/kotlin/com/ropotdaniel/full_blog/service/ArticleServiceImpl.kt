package com.ropotdaniel.full_blog.service

import com.ropotdaniel.full_blog.dataaccessobject.ArticleRepository
import com.ropotdaniel.full_blog.domainobject.ArticleDO
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.ZonedDateTime

@Service
class ArticleServiceImpl @Autowired constructor(private val articleRepository: ArticleRepository) : ArticleService {

    private val logger = LoggerFactory.getLogger(ArticleServiceImpl::class.java)

    override fun getArticle(id: Long): ArticleDO {
        logger.info("Fetching article with id: $id")
        return articleRepository.getReferenceById(id)
    }

    override fun getAllArticles(): MutableList<ArticleDO> {
        logger.info("Fetching all articles")
        return articleRepository.findAll()
    }

    @Transactional
    override fun createArticle(articleDO: ArticleDO): ArticleDO {
        logger.info("Creating new article with title: ${articleDO.title}")

        articleDO.dateCreated = ZonedDateTime.now()
        return articleRepository.save(articleDO)
    }

    @Transactional
    override fun updateArticle(id: Long, modifiedArticleDO: ArticleDO): ArticleDO {
        logger.info("Updating article with id: $id")
        val article = articleRepository.getReferenceById(id)
        article.bannerImageUrl = modifiedArticleDO.bannerImageUrl
        article.content = modifiedArticleDO.content
        article.dateCreated = modifiedArticleDO.dateCreated
        return article
    }

    @Transactional
    override fun deleteArticle(id: Long) {
        logger.info("Deleting article with id: $id")
        articleRepository.deleteById(id)
    }
}