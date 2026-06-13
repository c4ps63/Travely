package service

import (
	"time"

	"github.com/c4ps63/travely/blog-service/model"
	"github.com/c4ps63/travely/blog-service/repository"
	"go.mongodb.org/mongo-driver/v2/bson/primitive"
)

type CreateBlogRequest struct {
	Title       string   `json:"title" binding:"required"`
	Description string   `json:"description" binding:"required"`
	Images      []string `json:"images"`
}

func CreateBlog(authorUsername string, req CreateBlogRequest) (*model.Blog, error) {
	blog := &model.Blog{
		AuthorUsername: authorUsername,
		Title:          req.Title,
		Description:    req.Description,
		CreatedAt:      primitive.NewDateTimeFromTime(time.Now()),
		Images:         req.Images,
	}
	if blog.Images == nil {
		blog.Images = []string{}
	}

	if err := repository.Create(blog); err != nil {
		return nil, err
	}
	return blog, nil
}

func GetAllBlogs() ([]model.Blog, error) {
	return repository.FindAll()
}

func GetBlogByID(id string) (*model.Blog, error) {
	return repository.FindByID(id)
}
