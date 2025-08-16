# Word Indexer API

Spring Boot application for **processing text files, storing words in a database, and querying them**. Designed for production-ready usage with **Liquibase**, **Swagger/OpenAPI**, and JSON-based REST APIs.

---

## Features

- Process text files and persist non-blank words to the database.
- Count words starting with 'M' or 'm'.
- Retrieve words longer than 5 characters.
- Case-insensitive word search.
- All APIs consume and produce **JSON**.
- Liquibase-managed database migrations.
- Swagger/OpenAPI documentation.

---

## API Endpoints

| Endpoint | Method | Request | Response | Description |
|----------|--------|---------|----------|-------------|
| `/words/process` | POST | `path` (query param) | `{"message":"File processed & indexed"}` | Process a text file and persist words. |
| `/words/count-m` | GET | — | `{"wordList":[Word], "count":long}` | Count words starting with 'M' or 'm'. |
| `/words/longer-than-five` | GET | — | `[Word]` | Get words longer than 5 characters. |
| `/words/search` | GET | `keyword` (query param) | `[Word]` | Search for exact/partial words case-insensitive. |

---

# Word Indexer API

Spring Boot application for **processing text files, storing words in a database, and querying them**. Designed for production-ready usage with **Liquibase**, **Swagger/OpenAPI**, and JSON-based REST APIs.

---

## Features

- Process text files and persist non-blank words to the database.
- Count words starting with 'M' or 'm'.
- Retrieve words longer than 5 characters.
- Case-insensitive word search.
- All APIs consume and produce **JSON**.
- Liquibase-managed database migrations.
- Swagger/OpenAPI documentation.

---

## Postman Collection

- File location: `postman/WordIndexer.postman_collection.json`
- Import into Postman to test all API endpoints.
- Pre-configured requests for:
    - Process file
    - Count words starting with M
    - Get words longer than five characters
    - Search exact words

---
## Build & Run (Local)

```bash
./gradlew clean build -x test
./gradlew bootRun
```

# Build docker image
docker build -t word-indexing-app:1.0 .

# Run locally
docker run -p 8080:8080 word-indexing-app:1.0

# Push to registry
docker tag word-indexing-app:1.0 <DOCKER_USER>/word-indexing-app:1.0
docker push <DOCKER_USER>/word-indexing-app:1.0

# Deploy to Kubernetes
kubectl apply -f word-indexing-service.yaml

# Check resources
kubectl get pods
kubectl get svc