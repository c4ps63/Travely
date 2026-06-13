package main

import (
	"log"
	"os"

	"github.com/c4ps63/travely/blog-service/config"
	"github.com/gin-gonic/gin"
)

func main() {
	config.ConnectDB()

	r := gin.Default()

	r.GET("/health", func(c *gin.Context) {
		c.JSON(200, gin.H{"status": "ok"})
	})

	port := os.Getenv("PORT")
	if port == "" {
		port = "8002"
	}

	log.Println("Blog servis pokrenut na portu", port)
	r.Run(":" + port)
}
