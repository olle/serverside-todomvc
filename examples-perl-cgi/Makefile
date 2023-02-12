.PHONY: build
build:
	docker build -t my-httpd .

.PHONY: fg
fg: build
	docker-compose up

.PHONY: up
up: build
	docker-compose up -d

.PHONY: down
down:
	docker-compose down --remove-orphans
