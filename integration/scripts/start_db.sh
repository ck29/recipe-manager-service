#!/bin/bash
docker run -p 9000:8000 amazon/dynamodb-local -jar DynamoDBLocal.jar -dbPath $PROJECT_DIR/data -sharedDb