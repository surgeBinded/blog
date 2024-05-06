package com.ropotdaniel.full_blog.service

import com.ropotdaniel.full_blog.domainobject.ArticleDO

interface ArticleService {

    fun getArticle(id: Long): ArticleDO

    fun getAllArticles(): MutableList<ArticleDO>

    fun createArticle(articleDO: ArticleDO): ArticleDO

    fun updateArticle(id: Long, modifiedArticleDO: ArticleDO): ArticleDO

    fun deleteArticle(id: Long)
}