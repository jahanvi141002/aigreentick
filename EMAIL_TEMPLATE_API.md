# Email Template API Documentation

## Overview
This API provides comprehensive CRUD operations for managing email templates in the AI Green Tick Notification Service. The email template system allows you to create, read, update, and delete email templates with full audit tracking.

## Database Schema

### EmailTemplate Entity
- **id** (Long): Primary key, auto-generated
- **name** (String): Template name (unique, required)
- **title** (String): Email subject/title (required)
- **body** (String): Email body content (required, TEXT type)
- **isActive** (Boolean): Template status (required, default: true)
- **createdBy** (String): User who created the template (required)
- **modifiedBy** (String): User who last modified the template
- **createdAt** (LocalDateTime): Creation timestamp (auto-generated)
- **modifiedAt** (LocalDateTime): Last modification timestamp (auto-generated)
- **version** (Long): Optimistic locking version (auto-generated)

## API Endpoints

### Base URL
```
http://localhost:8080/api/v1/email-templates
```

### 1. Create Email Template
**POST** `/email-templates`

Creates a new email template.

**Request Body:**
```json
{
  "name": "welcome-email",
  "title": "Welcome to AI Green Tick!",
  "body": "Dear {{name}}, welcome to our platform!",
  "isActive": true,
  "createdBy": "admin@aigreentick.com",
  "modifiedBy": "admin@aigreentick.com"
}
```

**Response:**
```json
{
  "success": true,
  "message": "Email template created successfully",
  "data": {
    "id": 1,
    "name": "welcome-email",
    "title": "Welcome to AI Green Tick!",
    "body": "Dear {{name}}, welcome to our platform!",
    "isActive": true,
    "createdBy": "admin@aigreentick.com",
    "modifiedBy": "admin@aigreentick.com",
    "createdAt": "2024-01-15T10:30:00",
    "modifiedAt": "2024-01-15T10:30:00",
    "version": 0
  },
  "timestamp": "2024-01-15T10:30:00"
}
```

### 2. Get All Templates (Paginated)
**GET** `/email-templates`

Retrieves all email templates with pagination.

**Query Parameters:**
- `page` (int, default: 0): Page number
- `size` (int, default: 10): Page size
- `sortBy` (String, default: "name"): Sort field
- `sortDir` (String, default: "asc"): Sort direction (asc/desc)

**Example:** `GET /email-templates?page=0&size=5&sortBy=createdAt&sortDir=desc`

### 3. Get All Templates (No Pagination)
**GET** `/email-templates/all`

Retrieves all email templates without pagination.

### 4. Get Template by ID
**GET** `/email-templates/{id}`

Retrieves a specific email template by its ID.

### 5. Get Template by Name
**GET** `/email-templates/name/{name}`

Retrieves a specific email template by its name.

### 6. Get Active Templates
**GET** `/email-templates/active`

Retrieves all active email templates.

### 7. Get Inactive Templates
**GET** `/email-templates/inactive`

Retrieves all inactive email templates.

### 8. Search Templates by Name
**GET** `/email-templates/search/name?q={query}`

Searches for templates by name (case-insensitive partial match).

### 9. Search Templates by Title
**GET** `/email-templates/search/title?q={query}`

Searches for templates by title (case-insensitive partial match).

### 10. Update Template
**PUT** `/email-templates/{id}`

Updates an existing email template.

**Request Body:**
```json
{
  "name": "welcome-email-v2",
  "title": "Welcome to AI Green Tick - Updated!",
  "body": "Dear {{name}}, welcome to our enhanced platform!",
  "isActive": true,
  "modifiedBy": "admin@aigreentick.com"
}
```

### 11. Activate Template
**PATCH** `/email-templates/{id}/activate?modifiedBy={user}`

Activates a specific email template.

### 12. Deactivate Template
**PATCH** `/email-templates/{id}/deactivate?modifiedBy={user}`

Deactivates a specific email template.

### 13. Delete Template
**DELETE** `/email-templates/{id}`

Permanently deletes an email template.

### 14. Get Template Statistics
**GET** `/email-templates/statistics`

Retrieves template statistics.

**Response:**
```json
{
  "success": true,
  "message": "Template statistics retrieved successfully",
  "data": {
    "totalTemplates": 25,
    "activeTemplates": 20,
    "inactiveTemplates": 5
  },
  "timestamp": "2024-01-15T10:30:00"
}
```

### 15. Health Check
**GET** `/email-templates/health`

Checks the health of the email template service.

## Error Handling

The API returns appropriate HTTP status codes and error messages:

- **200 OK**: Successful operation
- **201 Created**: Resource created successfully
- **400 Bad Request**: Validation errors or invalid input
- **404 Not Found**: Resource not found
- **409 Conflict**: Resource already exists (duplicate name)
- **500 Internal Server Error**: Unexpected server error

**Error Response Format:**
```json
{
  "success": false,
  "message": "Error description",
  "data": null,
  "timestamp": "2024-01-15T10:30:00"
}
```

**Validation Error Response:**
```json
{
  "success": false,
  "message": "Validation failed",
  "data": {
    "name": "Template name is required",
    "title": "Template title is required"
  },
  "timestamp": "2024-01-15T10:30:00"
}
```

## Features

### 1. Full CRUD Operations
- ✅ Create new email templates
- ✅ Read templates (single, multiple, paginated)
- ✅ Update existing templates
- ✅ Delete templates

### 2. Advanced Search & Filtering
- Search by name (partial, case-insensitive)
- Search by title (partial, case-insensitive)
- Filter by active/inactive status
- Pagination support

### 3. Audit Trail
- Created by tracking
- Modified by tracking
- Creation timestamp
- Last modification timestamp
- Version control (optimistic locking)

### 4. Validation
- Required field validation
- Unique name constraint
- Data type validation
- Business rule validation

### 5. Error Handling
- Global exception handling
- Custom exception types
- Detailed error messages
- Proper HTTP status codes

## Usage Examples

### Creating a Welcome Email Template
```bash
curl -X POST http://localhost:8080/api/v1/email-templates \
  -H "Content-Type: application/json" \
  -d '{
    "name": "welcome-email",
    "title": "Welcome to AI Green Tick!",
    "body": "Dear {{name}}, welcome to our platform! We are excited to have you on board.",
    "isActive": true,
    "createdBy": "admin@aigreentick.com"
  }'
```

### Updating a Template
```bash
curl -X PUT http://localhost:8080/api/v1/email-templates/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "welcome-email-v2",
    "title": "Welcome to AI Green Tick - Enhanced!",
    "body": "Dear {{name}}, welcome to our enhanced platform!",
    "isActive": true,
    "modifiedBy": "admin@aigreentick.com"
  }'
```

### Searching Templates
```bash
curl "http://localhost:8080/api/v1/email-templates/search/name?q=welcome"
```

### Getting Active Templates
```bash
curl "http://localhost:8080/api/v1/email-templates/active"
```

## Database Console

Access the H2 database console at: `http://localhost:8080/api/v1/h2-console`

- **JDBC URL**: `jdbc:h2:mem:notificationdb`
- **Username**: `sa`
- **Password**: `password`

## Testing

The application includes comprehensive error handling and validation. Test the API using:

1. **Postman Collection**: Import the provided endpoints
2. **cURL Commands**: Use the examples above
3. **H2 Console**: Verify data persistence
4. **Unit Tests**: Run `mvn test` for automated testing

## Security Considerations

- Input validation on all endpoints
- SQL injection prevention through JPA
- XSS protection through proper encoding
- CORS enabled for cross-origin requests
- Audit logging for all operations

## Performance Features

- Pagination for large datasets
- Database indexing on frequently queried fields
- Optimistic locking to prevent concurrent modification issues
- Efficient search with case-insensitive queries
