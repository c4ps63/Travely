package main

import (
	"log"
	"os"

	"github.com/c4ps63/travely/gateway/middleware"
	"github.com/c4ps63/travely/gateway/proxy"
	"github.com/gin-gonic/gin"
)

func getenv(key, fallback string) string {
	if v := os.Getenv(key); v != "" {
		return v
	}
	return fallback
}

func main() {
	r := gin.Default()

	r.Use(middleware.JwtAuth())

	stakeholders := getenv("STAKEHOLDERS_URL", "http://localhost:8001")
	blog := getenv("BLOG_URL", "http://localhost:8002")
	tour := getenv("TOUR_URL", "http://localhost:8003")
	follower := getenv("FOLLOWER_URL", "http://localhost:8005")

	r.Any("/auth/*path", proxy.ForwardTo(stakeholders))
	r.Any("/users/*path", proxy.ForwardTo(stakeholders))
	r.Any("/blogs/*path", proxy.ForwardTo(blog))
	r.Any("/tours/*path", proxy.ForwardTo(tour))
	r.Any("/position/*path", proxy.ForwardTo(tour))
	r.Any("/follow/*path", proxy.ForwardTo(follower))

	log.Println("Gateway pokrenut na portu 8000")
	r.Run(":8000")
}
