FROM golang:alpine AS builder
WORKDIR /app
COPY go.mod go.sum ./
RUN go mod download
COPY . .
RUN go mod tidy && go build -o blog-service .

FROM alpine:latest
WORKDIR /app
COPY --from=builder /app/blog-service .
ENTRYPOINT ["./blog-service"]
