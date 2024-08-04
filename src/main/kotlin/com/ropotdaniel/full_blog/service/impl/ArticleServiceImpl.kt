package com.ropotdaniel.full_blog.service.impl

import com.ropotdaniel.full_blog.dataaccessobject.ArticleRepository
import com.ropotdaniel.full_blog.datatransferobject.article.CreateArticleDTO
import com.ropotdaniel.full_blog.datatransferobject.article.ArticleDTO
import com.ropotdaniel.full_blog.datatransferobject.response.ArticleResponse
import com.ropotdaniel.full_blog.domainobject.ArticleDO
import com.ropotdaniel.full_blog.mapper.ArticleMapper
import com.ropotdaniel.full_blog.service.ArticleService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ArticleServiceImpl @Autowired constructor(private val articleRepository: ArticleRepository) :
    ArticleService {

    private val logger = LoggerFactory.getLogger(ArticleServiceImpl::class.java)

    override fun getArticle(id: Long): ArticleDTO {
        logger.info("Fetching article with id: $id")
        return ArticleMapper.toDTO(articleRepository.getReferenceById(id))
    }

    override fun getAllArticles(pageNo: Int, pageSize: Int, sortBy:String, sortDir:String): ArticleResponse {
        logger.info("Fetching all articles")

        val sort = Sort.by(sortBy).let {
            if (sortDir.equals(Sort.Direction.ASC.name, ignoreCase = true)) it.ascending() else it.descending()
        }

        val pageable = PageRequest.of(pageNo, pageSize, sort)
        val articles = articleRepository.findAll(pageable)
        val content = ArticleMapper.toDTOList(articles.content)

        return ArticleResponse(content, articles.number, articles.size, articles.totalElements, articles.totalPages, articles.isLast)
    }

    @Transactional
    override fun createArticle(createArticleDTO: CreateArticleDTO): ArticleDTO {
        logger.info("Creating new article with title: ${createArticleDTO.title}")

        val savedArticle = articleRepository.save(ArticleMapper.toDO(createArticleDTO))

        return ArticleMapper.toDTO(savedArticle)
    }

    @Transactional
    override fun updateArticle(id: Long, modifiedArticleDO: ArticleDO): ArticleDO {
        logger.info("Updating article with id: $id")
        val article = articleRepository.getReferenceById(id)
        article.bannerImageUrl = modifiedArticleDO.bannerImageUrl
        article.content = modifiedArticleDO.content
        return article
    }

    @Transactional
    override fun deleteArticle(id: Long) {
        logger.info("Deleting article with id: $id")
        articleRepository.deleteById(id)
    }
}