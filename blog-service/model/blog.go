package model

import "go.mongodb.org/mongo-driver/v2/bson/primitive"

type Blog struct {
	ID              primitive.ObjectID `bson:"_id,omitempty" json:"id"`
	AuthorID        string             `bson:"author_id" json:"authorId"`
	AuthorUsername  string             `bson:"author_username" json:"authorUsername"`
	Title           string             `bson:"title" json:"title"`
	Description     string             `bson:"description" json:"description"`
	CreatedAt       primitive.DateTime `bson:"created_at" json:"createdAt"`
	Images          []string           `bson:"images" json:"images"`
}
