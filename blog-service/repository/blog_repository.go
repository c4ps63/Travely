package repository

import (
	"context"
	"time"

	"github.com/c4ps63/travely/blog-service/config"
	"github.com/c4ps63/travely/blog-service/model"
	"go.mongodb.org/mongo-driver/v2/bson"
	"go.mongodb.org/mongo-driver/v2/mongo"
)

func collection() *mongo.Collection {
	return config.DB.Collection("blogs")
}

func Create(blog *model.Blog) error {
	ctx, cancel := context.WithTimeout(context.Background(), 5*time.Second)
	defer cancel()

	blog.ID = bson.NewObjectID()
	_, err := collection().InsertOne(ctx, blog)
	return err
}

func FindAll() ([]model.Blog, error) {
	ctx, cancel := context.WithTimeout(context.Background(), 5*time.Second)
	defer cancel()

	cursor, err := collection().Find(ctx, bson.M{})
	if err != nil {
		return nil, err
	}
	defer cursor.Close(ctx)

	var blogs []model.Blog
	if err := cursor.All(ctx, &blogs); err != nil {
		return nil, err
	}
	return blogs, nil
}

func FindByAuthors(authors []string) ([]model.Blog, error) {
	ctx, cancel := context.WithTimeout(context.Background(), 5*time.Second)
	defer cancel()

	cursor, err := collection().Find(ctx, bson.M{"author_username": bson.M{"$in": authors}})
	if err != nil {
		return nil, err
	}
	defer cursor.Close(ctx)

	var blogs []model.Blog
	if err := cursor.All(ctx, &blogs); err != nil {
		return nil, err
	}
	return blogs, nil
}

func FindByID(id string) (*model.Blog, error) {
	ctx, cancel := context.WithTimeout(context.Background(), 5*time.Second)
	defer cancel()

	objID, err := bson.ObjectIDFromHex(id)
	if err != nil {
		return nil, err
	}

	var blog model.Blog
	err = collection().FindOne(ctx, bson.M{"_id": objID}).Decode(&blog)
	if err != nil {
		return nil, err
	}
	return &blog, nil
}
