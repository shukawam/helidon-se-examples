#!/bin/bash

# stop and delete container
docker-compose down -v

# start container
docker-compose up -d --build