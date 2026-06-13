package model

import "go.mongodb.org/mongo-driver/v2/bson"

type Blog struct {
	ID             bson.ObjectID `bson:"_id,omitempty" json:"id"`
	AuthorUsername string        `bson:"author_username" json:"authorUsername"`
	Title          string        `bson:"title" json:"title"`
	Description    string        `bson:"description" json:"description"`
	CreatedAt      bson.DateTime `bson:"created_at" json:"createdAt"`
	Images         []string      `bson:"images" json:"images"`
}
