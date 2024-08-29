package com.ropotdaniel.full_blog.service.impl

import com.ropotdaniel.full_blog.dataaccessobject.ArticleRepository
import com.ropotdaniel.full_blog.datatransferobject.article.ArticleDTO
import com.ropotdaniel.full_blog.datatransferobject.article.CreateArticleDTO
import com.ropotdaniel.full_blog.datatransferobject.article.UpdateArticleDTO
import com.ropotdaniel.full_blog.datatransferobject.response.ArticleResponse
import com.ropotdaniel.full_blog.exceptions.ArticleNotFoundException
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
        return ArticleMapper.toDTO(articleRepository.getReferenceById(id))
    }

    override fun getAllArticles(pageNo: Int, pageSize: Int, sortBy: String, sortDir: String): ArticleResponse {
        val sort = Sort.by(sortBy).let {
            if (sortDir.equals(Sort.Direction.ASC.name, ignoreCase = true)) it.ascending() else it.descending()
        }

        val pageable = PageRequest.of(pageNo, pageSize, sort)
        val articles = articleRepository.findAll(pageable)
        val content = ArticleMapper.toDTOList(articles.content)

        return ArticleResponse(
            content,
            articles.number,
            articles.size,
            articles.totalElements,
            articles.totalPages,
            articles.isLast
        )
    }

    @Transactional
    override fun createArticle(createArticleDTO: CreateArticleDTO): ArticleDTO {
        logger.info("Creating new article with title: ${createArticleDTO.title}")

        val savedArticle = articleRepository.save(ArticleMapper.toDO(createArticleDTO))

        return ArticleMapper.toDTO(savedArticle)
    }

    @Transactional
    override fun updateArticle(id: Long, modifiedArticleDO: UpdateArticleDTO): ArticleDTO {
        val repoArticle =
            articleRepository.findById(id).orElseThrow { ArticleNotFoundException("Article not found with id = $id") }

        modifiedArticleDO.title?.let { repoArticle.title = it }
        modifiedArticleDO.content?.let { repoArticle.content = it }
        modifiedArticleDO.bannerImageUrl?.let { repoArticle.bannerImageUrl = it }
        modifiedArticleDO.dateUpdated.let { repoArticle.dateUpdated = it }

        val updatedArticle = articleRepository.save(repoArticle)

        return ArticleMapper.toDTO(updatedArticle)
    }

    @Transactional
    override fun deleteArticle(id: Long) {
        logger.info("Deleting article with id: $id")
        articleRepository.deleteById(id).let { throw ArticleNotFoundException("Article not found with id = $id") }
    }
}