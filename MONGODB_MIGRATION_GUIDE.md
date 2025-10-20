# MongoDB Migration Guide

## Overview
The Email Template API has been successfully migrated from H2 database to MongoDB. This guide provides information about the changes made and how to use the new MongoDB-based system.

## Changes Made

### 1. Dependencies Updated
- **Removed**: `spring-boot-starter-data-jpa` and `h2` database
- **Added**: `spring-boot-starter-data-mongodb`

### 2. Database Configuration
Updated `application.properties`:
```properties
# MongoDB Configuration
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=notificationdb
spring.data.mongodb.authentication-database=admin
spring.data.mongodb.username=
spring.data.mongodb.password=

# MongoDB Connection String (alternative configuration)
# spring.data.mongodb.uri=mongodb://localhost:27017/notificationdb

# MongoDB Logging
logging.level.org.springframework.data.mongodb.core.MongoTemplate=DEBUG
```

### 3. Entity Changes
- **EmailTemplate** entity converted from JPA to MongoDB document
- Changed ID type from `Long` to `String` (MongoDB ObjectId)
- Updated annotations:
  - `@Entity` → `@Document(collection = "email_templates")`
  - `@Id` with `@GeneratedValue` → `@Id` (MongoDB auto-generates ObjectId)
  - `@Column` → removed (MongoDB doesn't need column mapping)
  - `@Indexed(unique = true)` for unique constraints
  - `@CreatedDate` and `@LastModifiedDate` for auditing

### 4. Repository Changes
- **EmailTemplateRepository** now extends `MongoRepository<EmailTemplate, String>`
- Updated custom queries to use MongoDB query syntax
- Example: `@Query("{ 'isActive': true, 'name': { $regex: ?0, $options: 'i' } }")`

### 5. Service Layer Updates
- All methods updated to use `String` IDs instead of `Long`
- MongoDB-specific operations and queries

### 6. Controller Updates
- All endpoints now accept `String` IDs in path variables
- No changes to API contract (still RESTful)

### 7. DTO Updates
- **EmailTemplateResponse** now uses `String` ID
- All other DTOs remain the same

## MongoDB Setup

### Option 1: Local MongoDB Installation
1. Install MongoDB Community Server
2. Start MongoDB service
3. The application will connect to `mongodb://localhost:27017/notificationdb`

### Option 2: Docker MongoDB
```bash
docker run -d --name mongodb -p 27017:27017 mongo:latest
```

### Option 3: MongoDB Atlas (Cloud)
Update `application.properties`:
```properties
spring.data.mongodb.uri=mongodb+srv://username:password@cluster.mongodb.net/notificationdb
```

## Database Schema

### MongoDB Collection: `email_templates`
```json
{
  "_id": "ObjectId",
  "name": "string (unique, indexed)",
  "title": "string",
  "body": "string",
  "isActive": "boolean",
  "createdBy": "string",
  "modifiedBy": "string",
  "createdAt": "ISODate",
  "modifiedAt": "ISODate",
  "version": "number"
}
```

### Indexes
- `name`: Unique index for fast lookups and uniqueness constraint

## API Endpoints (Unchanged)

All API endpoints remain the same, but now use String IDs:

### Create Template
```bash
POST /api/v1/email-templates
```

### Get Template by ID
```bash
GET /api/v1/email-templates/{id}
# Example: GET /api/v1/email-templates/507f1f77bcf86cd799439011
```

### Update Template
```bash
PUT /api/v1/email-templates/{id}
```

### Delete Template
```bash
DELETE /api/v1/email-templates/{id}
```

## Testing the Application

### 1. Start MongoDB
Make sure MongoDB is running on `localhost:27017`

### 2. Start the Application
```bash
mvn spring-boot:run
```

### 3. Test API Endpoints

#### Create a Template
```bash
curl -X POST http://localhost:9090/api/v1/email-templates \
  -H "Content-Type: application/json" \
  -d '{
    "name": "welcome-email",
    "title": "Welcome to AI Green Tick!",
    "body": "Dear {{name}}, welcome to our platform!",
    "isActive": true,
    "createdBy": "admin@aigreentick.com"
  }'
```

#### Get All Templates
```bash
curl http://localhost:9090/api/v1/email-templates
```

#### Get Template by ID
```bash
curl http://localhost:9090/api/v1/email-templates/{mongo-object-id}
```

## MongoDB Features

### 1. Automatic ID Generation
MongoDB automatically generates unique ObjectIds for each document.

### 2. Flexible Schema
MongoDB allows for flexible document structure, making it easy to add new fields.

### 3. Built-in Auditing
Spring Data MongoDB provides automatic timestamp management with `@CreatedDate` and `@LastModifiedDate`.

### 4. Indexing
- Unique index on `name` field for fast lookups
- Automatic indexing on `_id` field

### 5. Query Capabilities
- Rich query language with MongoDB operators
- Text search capabilities
- Aggregation pipeline support

## Migration Benefits

### 1. Scalability
- MongoDB scales horizontally better than relational databases
- Better performance for read-heavy workloads

### 2. Flexibility
- Schema-less design allows for easy evolution
- JSON-like document structure matches API responses

### 3. Developer Experience
- No need for complex ORM mappings
- Direct JSON serialization/deserialization

### 4. Cloud Integration
- Easy integration with MongoDB Atlas
- Built-in replication and sharding

## Monitoring and Management

### MongoDB Compass
Use MongoDB Compass to visualize and manage your data:
1. Download from https://www.mongodb.com/products/compass
2. Connect to `mongodb://localhost:27017`
3. Navigate to `notificationdb` database
4. View `email_templates` collection

### Application Logs
Enable MongoDB query logging:
```properties
logging.level.org.springframework.data.mongodb.core.MongoTemplate=DEBUG
```

## Troubleshooting

### Common Issues

1. **Connection Refused**
   - Ensure MongoDB is running
   - Check if port 27017 is available
   - Verify connection string in `application.properties`

2. **Authentication Failed**
   - Check username/password in configuration
   - Ensure user has proper permissions

3. **Index Creation Issues**
   - MongoDB will create indexes automatically
   - Check MongoDB logs for any errors

### Health Check
The application provides health check endpoints:
- `GET /api/v1/email-templates/health`
- `GET /api/v1/actuator/health`

## Performance Considerations

### 1. Indexing
- Unique index on `name` field for fast lookups
- Consider adding indexes on frequently queried fields

### 2. Connection Pooling
- MongoDB driver handles connection pooling automatically
- Configure pool size in `application.properties` if needed

### 3. Query Optimization
- Use MongoDB query operators efficiently
- Consider using aggregation pipeline for complex queries

## Security

### 1. Authentication
- Configure MongoDB authentication in `application.properties`
- Use strong passwords and proper user roles

### 2. Network Security
- Use MongoDB's built-in security features
- Consider using SSL/TLS for production

### 3. Data Validation
- Spring Data MongoDB provides validation support
- Use `@Valid` annotations for input validation

## Conclusion

The migration to MongoDB provides:
- Better scalability and performance
- Flexible schema design
- Easy cloud integration
- Rich query capabilities
- Modern NoSQL features

The API remains fully compatible with existing clients while gaining the benefits of MongoDB's document-based architecture.
