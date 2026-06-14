package controller

import (
	"net/http"

	"github.com/c4ps63/travely/blog-service/service"
	"github.com/gin-gonic/gin"
)

func CreateBlog(c *gin.Context) {
	var req service.CreateBlogRequest
	if err := c.ShouldBindJSON(&req); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	username := c.GetString("username")
	blog, err := service.CreateBlog(username, req)
	if err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": "Greška pri kreiranju bloga"})
		return
	}

	c.JSON(http.StatusCreated, blog)
}

func GetAllBlogs(c *gin.Context) {
	username := c.GetString("username")
	blogs, err := service.GetAllBlogs(username)
	if err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": "Greška pri dohvatanju blogova"})
		return
	}
	c.JSON(http.StatusOK, blogs)
}

func GetBlogByID(c *gin.Context) {
	id := c.Param("id")
	blog, err := service.GetBlogByID(id)
	if err != nil {
		c.JSON(http.StatusNotFound, gin.H{"error": "Blog nije pronađen"})
		return
	}
	c.JSON(http.StatusOK, blog)
}
