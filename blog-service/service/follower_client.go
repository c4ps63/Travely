package service

import (
	"encoding/json"
	"fmt"
	"net/http"
	"os"
)

func getFollowerServiceURL() string {
	if url := os.Getenv("FOLLOWER_SERVICE_URL"); url != "" {
		return url
	}
	return "http://localhost:8005"
}

func GetFollowing(username string) ([]string, error) {
	url := fmt.Sprintf("%s/following", getFollowerServiceURL())
	req, err := http.NewRequest("GET", url, nil)
	if err != nil {
		return nil, err
	}
	req.Header.Set("X-Username", username)

	resp, err := http.DefaultClient.Do(req)
	if err != nil {
		return nil, err
	}
	defer resp.Body.Close()

	var following []string
	if err := json.NewDecoder(resp.Body).Decode(&following); err != nil {
		return nil, err
	}
	return following, nil
}
