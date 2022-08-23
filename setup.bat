@echo off
echo "Setting up database.."
set _cmd=docker ps -q -f "name=dynamodb-local"
for /F "tokens=1" %%A in ('%_cmd%') DO docker rm -f %%A

start cmd /k docker run --name dynamodb-local -p 8000:8000 amazon/dynamodb-local -jar DynamoDBLocal.jar -dbPath . -sharedDb

echo "Building application.."
call mvn clean install

echo "Starting application.."
start cmd /k java -jar target/recipe-manager-service-0.0.1-SNAPSHOT.jar
echo "Waiting for application to start.."
sleep 10

echo "Creating table.."
aws dynamodb create-table --cli-input-json file://data/data_model/recipes.json --endpoint-url http://localhost:8000

echo "Adding data.."
curl -H "Content-Type: application/json" -d "@data/samples/recipe_pancake.json" -X POST http://localhost:8080/recipe
curl -H "Content-Type: application/json" -d "@data/samples/recipe_lemonade.json" -X POST http://localhost:8080/recipe

