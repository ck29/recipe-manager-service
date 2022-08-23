@echo off
echo "Setting up database.."
start cmd /k docker run --name dynamodb-local -p 8000:8000 amazon/dynamodb-local -jar DynamoDBLocal.jar -dbPath . -sharedDb

echo "Building application.."
call mvn clean install

echo "Starting application.."
start cmd /k java -jar target/recipe-manager-service-0.0.1-SNAPSHOT.jar


aws dynamodb create-table --cli-input-json file://data/data_model/recipes.json --endpoint-url http://localhost:8000
REM curl -d "@/samples/recipie_soup.json" -X POST http://localhost:8000/

