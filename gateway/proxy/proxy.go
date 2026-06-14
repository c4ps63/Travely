package proxy

import (
	"net/http"
	"net/http/httputil"
	"net/url"

	"github.com/gin-gonic/gin"
)

func ForwardTo(target string) gin.HandlerFunc {
	targetURL, _ := url.Parse(target)
	reverseProxy := httputil.NewSingleHostReverseProxy(targetURL)

	reverseProxy.Director = func(req *http.Request) {
		req.URL.Scheme = targetURL.Scheme
		req.URL.Host = targetURL.Host
		req.Host = targetURL.Host
	}

	return func(c *gin.Context) {
		c.Request.URL.Path = c.Param("path")
		reverseProxy.ServeHTTP(c.Writer, c.Request)
	}
}
