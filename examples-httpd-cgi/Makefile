.PHONY: build
build:
	docker build -t my-httpd .

.PHONY: up
up: build
	docker-compose up -d

.PHONY: down
down:
	docker-compose down --remove-orphans

