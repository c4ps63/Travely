package main

import (
	"log"

	"github.com/c4ps63/travely/gateway/middleware"
	"github.com/c4ps63/travely/gateway/proxy"
	"github.com/gin-gonic/gin"
)

func main() {
	r := gin.Default()

	r.Use(middleware.JwtAuth())

	r.Any("/auth/*path", proxy.ForwardTo("http://localhost:8001"))
	r.Any("/users/*path", proxy.ForwardTo("http://localhost:8001"))
	r.Any("/blogs/*path", proxy.ForwardTo("http://localhost:8002"))
	r.Any("/tours/*path", proxy.ForwardTo("http://localhost:8003"))
	r.Any("/position/*path", proxy.ForwardTo("http://localhost:8003"))
	r.Any("/follow/*path", proxy.ForwardTo("http://localhost:8005"))

	log.Println("Gateway pokrenut na portu 8000")
	r.Run(":8000")
}
