package main

import (
	"log"
	"os"

	"github.com/c4ps63/travely/blog-service/config"
	"github.com/c4ps63/travely/blog-service/controller"
	"github.com/c4ps63/travely/blog-service/middleware"
	"github.com/gin-gonic/gin"
)

func main() {
	config.ConnectDB()

	r := gin.Default()

	r.GET("/health", func(c *gin.Context) {
		c.JSON(200, gin.H{"status": "ok"})
	})

	blogs := r.Group("/blogs", middleware.JwtAuth())
	{
		blogs.POST("", controller.CreateBlog)
		blogs.GET("", controller.GetAllBlogs)
		blogs.GET("/:id", controller.GetBlogByID)
	}

	port := os.Getenv("PORT")
	if port == "" {
		port = "8002"
	}

	log.Println("Blog servis pokrenut na portu", port)
	r.Run(":" + port)
}
