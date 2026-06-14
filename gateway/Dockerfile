FROM golang:alpine AS builder
WORKDIR /app
COPY go.mod go.sum ./
RUN go mod download
COPY . .
RUN go mod tidy && go build -o gateway .

FROM alpine:latest
WORKDIR /app
COPY --from=builder /app/gateway .
ENTRYPOINT ["./gateway"]
