package com.ropotdaniel.full_blog.service

import com.ropotdaniel.full_blog.datatransferobject.article.ArticleDTO
import com.ropotdaniel.full_blog.datatransferobject.article.CreateArticleDTO
import com.ropotdaniel.full_blog.datatransferobject.article.UpdateArticleDTO
import com.ropotdaniel.full_blog.datatransferobject.response.ArticleResponse

interface ArticleService {

    fun getArticle(id: Long): ArticleDTO

    fun getAllArticles(pageNo: Int, pageSize: Int, sortBy: String, sortDir: String): ArticleResponse

    fun createArticle(createArticleDTO: CreateArticleDTO): ArticleDTO

    fun updateArticle(id: Long, modifiedArticleDO: UpdateArticleDTO): ArticleDTO

    fun deleteArticle(id: Long)
}