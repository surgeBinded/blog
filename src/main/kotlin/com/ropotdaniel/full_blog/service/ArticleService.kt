package com.ropotdaniel.full_blog.service

import com.ropotdaniel.full_blog.datatransferobject.CreateArticleDTO
import com.ropotdaniel.full_blog.datatransferobject.ArticleDTO
import com.ropotdaniel.full_blog.datatransferobject.response.ArticleResponse
import com.ropotdaniel.full_blog.domainobject.ArticleDO

interface ArticleService {

    fun getArticle(id: Long): ArticleDTO

    fun getAllArticles(pageNo: Int, pageSize: Int, sortBy:String, sortDir:String): ArticleResponse

    fun createArticle(createArticleDTO: CreateArticleDTO): ArticleDTO

    fun updateArticle(id: Long, modifiedArticleDO: ArticleDO): ArticleDO

    fun deleteArticle(id: Long)
}