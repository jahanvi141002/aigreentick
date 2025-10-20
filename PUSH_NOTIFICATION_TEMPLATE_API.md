# Push Notification Template API Documentation

## Overview
This API provides comprehensive CRUD operations for managing push notification templates. Each template contains a title, description, image URL, active status, and audit fields for tracking creation and modification.

## Base URL
```
/api/v1/push-notification-templates
```

## Data Model

### PushNotificationTemplate Entity
```json
{
  "id": "string",
  "title": "string (required, unique)",
  "description": "string (required)",
  "imageUrl": "string (optional)",
  "isActive": "boolean (default: true)",
  "createdBy": "string (required)",
  "modifiedBy": "string (optional)",
  "createdAt": "datetime",
  "modifiedAt": "datetime",
  "version": "long"
}
```

## API Endpoints

### 1. Create Push Notification Template
**POST** `/push-notification-templates`

Creates a new push notification template.

**Request Body:**
```json
{
  "title": "Welcome Notification",
  "description": "Welcome to our app! Get started with these features.",
  "imageUrl": "https://example.com/welcome-image.jpg",
  "isActive": true,
  "createdBy": "admin@example.com",
  "modifiedBy": "admin@example.com"
}
```

**Response (201 Created):**
```json
{
  "success": true,
  "message": "Push notification template created successfully",
  "data": {
    "id": "64f8a1b2c3d4e5f6a7b8c9d0",
    "title": "Welcome Notification",
    "description": "Welcome to our app! Get started with these features.",
    "imageUrl": "https://example.com/welcome-image.jpg",
    "isActive": true,
    "createdBy": "admin@example.com",
    "modifiedBy": "admin@example.com",
    "createdAt": "2023-09-05T10:30:00Z",
    "modifiedAt": "2023-09-05T10:30:00Z",
    "version": 0
  }
}
```

### 2. Get All Templates (Paginated)
**GET** `/push-notification-templates`

Retrieves all push notification templates with pagination.

**Query Parameters:**
- `page` (default: 0): Page number
- `size` (default: 10): Number of items per page
- `sortBy` (default: "title"): Field to sort by
- `sortDir` (default: "asc"): Sort direction (asc/desc)

**Example:** `/push-notification-templates?page=0&size=5&sortBy=createdAt&sortDir=desc`

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Push notification templates retrieved successfully",
  "data": {
    "content": [
      {
        "id": "64f8a1b2c3d4e5f6a7b8c9d0",
        "title": "Welcome Notification",
        "description": "Welcome to our app!",
        "imageUrl": "https://example.com/welcome.jpg",
        "isActive": true,
        "createdBy": "admin@example.com",
        "modifiedBy": null,
        "createdAt": "2023-09-05T10:30:00Z",
        "modifiedAt": "2023-09-05T10:30:00Z",
        "version": 0
      }
    ],
    "pageable": {
      "pageNumber": 0,
      "pageSize": 5,
      "sort": {
        "sorted": true,
        "unsorted": false
      }
    },
    "totalElements": 1,
    "totalPages": 1,
    "first": true,
    "last": true,
    "numberOfElements": 1
  }
}
```

### 3. Get All Templates (No Pagination)
**GET** `/push-notification-templates/all`

Retrieves all push notification templates without pagination.

**Response (200 OK):**
```json
{
  "success": true,
  "message": "All push notification templates retrieved successfully",
  "data": [
    {
      "id": "64f8a1b2c3d4e5f6a7b8c9d0",
      "title": "Welcome Notification",
      "description": "Welcome to our app!",
      "imageUrl": "https://example.com/welcome.jpg",
      "isActive": true,
      "createdBy": "admin@example.com",
      "modifiedBy": null,
      "createdAt": "2023-09-05T10:30:00Z",
      "modifiedAt": "2023-09-05T10:30:00Z",
      "version": 0
    }
  ]
}
```

### 4. Get Template by ID
**GET** `/push-notification-templates/{id}`

Retrieves a specific push notification template by its ID.

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Push notification template retrieved successfully",
  "data": {
    "id": "64f8a1b2c3d4e5f6a7b8c9d0",
    "title": "Welcome Notification",
    "description": "Welcome to our app!",
    "imageUrl": "https://example.com/welcome.jpg",
    "isActive": true,
    "createdBy": "admin@example.com",
    "modifiedBy": null,
    "createdAt": "2023-09-05T10:30:00Z",
    "modifiedAt": "2023-09-05T10:30:00Z",
    "version": 0
  }
}
```

### 5. Get Template by Title
**GET** `/push-notification-templates/title/{title}`

Retrieves a specific push notification template by its title.

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Push notification template retrieved successfully",
  "data": {
    "id": "64f8a1b2c3d4e5f6a7b8c9d0",
    "title": "Welcome Notification",
    "description": "Welcome to our app!",
    "imageUrl": "https://example.com/welcome.jpg",
    "isActive": true,
    "createdBy": "admin@example.com",
    "modifiedBy": null,
    "createdAt": "2023-09-05T10:30:00Z",
    "modifiedAt": "2023-09-05T10:30:00Z",
    "version": 0
  }
}
```

### 6. Get Active Templates
**GET** `/push-notification-templates/active`

Retrieves all active push notification templates.

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Active push notification templates retrieved successfully",
  "data": [
    {
      "id": "64f8a1b2c3d4e5f6a7b8c9d0",
      "title": "Welcome Notification",
      "description": "Welcome to our app!",
      "imageUrl": "https://example.com/welcome.jpg",
      "isActive": true,
      "createdBy": "admin@example.com",
      "modifiedBy": null,
      "createdAt": "2023-09-05T10:30:00Z",
      "modifiedAt": "2023-09-05T10:30:00Z",
      "version": 0
    }
  ]
}
```

### 7. Get Inactive Templates
**GET** `/push-notification-templates/inactive`

Retrieves all inactive push notification templates.

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Inactive push notification templates retrieved successfully",
  "data": []
}
```

### 8. Search Templates by Title
**GET** `/push-notification-templates/search/title?q={query}`

Searches for push notification templates by title (case-insensitive).

**Example:** `/push-notification-templates/search/title?q=welcome`

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Search results retrieved successfully",
  "data": [
    {
      "id": "64f8a1b2c3d4e5f6a7b8c9d0",
      "title": "Welcome Notification",
      "description": "Welcome to our app!",
      "imageUrl": "https://example.com/welcome.jpg",
      "isActive": true,
      "createdBy": "admin@example.com",
      "modifiedBy": null,
      "createdAt": "2023-09-05T10:30:00Z",
      "modifiedAt": "2023-09-05T10:30:00Z",
      "version": 0
    }
  ]
}
```

### 9. Search Templates by Description
**GET** `/push-notification-templates/search/description?q={query}`

Searches for push notification templates by description (case-insensitive).

**Example:** `/push-notification-templates/search/description?q=features`

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Search results retrieved successfully",
  "data": [
    {
      "id": "64f8a1b2c3d4e5f6a7b8c9d0",
      "title": "Welcome Notification",
      "description": "Welcome to our app! Get started with these features.",
      "imageUrl": "https://example.com/welcome.jpg",
      "isActive": true,
      "createdBy": "admin@example.com",
      "modifiedBy": null,
      "createdAt": "2023-09-05T10:30:00Z",
      "modifiedAt": "2023-09-05T10:30:00Z",
      "version": 0
    }
  ]
}
```

### 10. Get Templates with Image
**GET** `/push-notification-templates/with-image`

Retrieves all push notification templates that have an image URL.

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Templates with image retrieved successfully",
  "data": [
    {
      "id": "64f8a1b2c3d4e5f6a7b8c9d0",
      "title": "Welcome Notification",
      "description": "Welcome to our app!",
      "imageUrl": "https://example.com/welcome.jpg",
      "isActive": true,
      "createdBy": "admin@example.com",
      "modifiedBy": null,
      "createdAt": "2023-09-05T10:30:00Z",
      "modifiedAt": "2023-09-05T10:30:00Z",
      "version": 0
    }
  ]
}
```

### 11. Get Templates without Image
**GET** `/push-notification-templates/without-image`

Retrieves all push notification templates that don't have an image URL.

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Templates without image retrieved successfully",
  "data": []
}
```

### 12. Update Template
**PUT** `/push-notification-templates/{id}`

Updates an existing push notification template.

**Request Body:**
```json
{
  "title": "Updated Welcome Notification",
  "description": "Updated welcome message with new features!",
  "imageUrl": "https://example.com/new-welcome-image.jpg",
  "isActive": true,
  "modifiedBy": "admin@example.com"
}
```

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Push notification template updated successfully",
  "data": {
    "id": "64f8a1b2c3d4e5f6a7b8c9d0",
    "title": "Updated Welcome Notification",
    "description": "Updated welcome message with new features!",
    "imageUrl": "https://example.com/new-welcome-image.jpg",
    "isActive": true,
    "createdBy": "admin@example.com",
    "modifiedBy": "admin@example.com",
    "createdAt": "2023-09-05T10:30:00Z",
    "modifiedAt": "2023-09-05T11:45:00Z",
    "version": 1
  }
}
```

### 13. Activate Template
**PATCH** `/push-notification-templates/{id}/activate?modifiedBy={user}`

Activates a push notification template.

**Example:** `/push-notification-templates/64f8a1b2c3d4e5f6a7b8c9d0/activate?modifiedBy=admin@example.com`

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Push notification template activated successfully",
  "data": {
    "id": "64f8a1b2c3d4e5f6a7b8c9d0",
    "title": "Welcome Notification",
    "description": "Welcome to our app!",
    "imageUrl": "https://example.com/welcome.jpg",
    "isActive": true,
    "createdBy": "admin@example.com",
    "modifiedBy": "admin@example.com",
    "createdAt": "2023-09-05T10:30:00Z",
    "modifiedAt": "2023-09-05T12:00:00Z",
    "version": 1
  }
}
```

### 14. Deactivate Template
**PATCH** `/push-notification-templates/{id}/deactivate?modifiedBy={user}`

Deactivates a push notification template.

**Example:** `/push-notification-templates/64f8a1b2c3d4e5f6a7b8c9d0/deactivate?modifiedBy=admin@example.com`

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Push notification template deactivated successfully",
  "data": {
    "id": "64f8a1b2c3d4e5f6a7b8c9d0",
    "title": "Welcome Notification",
    "description": "Welcome to our app!",
    "imageUrl": "https://example.com/welcome.jpg",
    "isActive": false,
    "createdBy": "admin@example.com",
    "modifiedBy": "admin@example.com",
    "createdAt": "2023-09-05T10:30:00Z",
    "modifiedAt": "2023-09-05T12:15:00Z",
    "version": 2
  }
}
```

### 15. Delete Template
**DELETE** `/push-notification-templates/{id}`

Deletes a push notification template.

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Push notification template deleted successfully"
}
```

### 16. Get Template Statistics
**GET** `/push-notification-templates/statistics`

Retrieves statistics about push notification templates.

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Template statistics retrieved successfully",
  "data": {
    "totalTemplates": 10,
    "activeTemplates": 8,
    "inactiveTemplates": 2
  }
}
```

### 17. Health Check
**GET** `/push-notification-templates/health`

Health check endpoint for the push notification template service.

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Push notification template service is healthy"
}
```

## Error Responses

### 400 Bad Request
```json
{
  "success": false,
  "message": "Validation failed",
  "data": {
    "title": "Template title is required",
    "description": "Template description is required"
  }
}
```

### 404 Not Found
```json
{
  "success": false,
  "message": "Push notification template with ID 64f8a1b2c3d4e5f6a7b8c9d0 not found"
}
```

### 409 Conflict
```json
{
  "success": false,
  "message": "Push notification template with title 'Welcome Notification' already exists"
}
```

### 500 Internal Server Error
```json
{
  "success": false,
  "message": "An unexpected error occurred. Please try again later."
}
```

## Validation Rules

- **title**: Required, must be unique (case-insensitive)
- **description**: Required
- **imageUrl**: Optional, should be a valid URL format
- **isActive**: Required, boolean value
- **createdBy**: Required
- **modifiedBy**: Required for updates

## Notes

1. All timestamps are in ISO 8601 format (UTC)
2. The `version` field is automatically managed for optimistic locking
3. Title uniqueness is case-insensitive
4. Search operations are case-insensitive
5. All endpoints support CORS with wildcard origin
6. The API follows RESTful conventions
7. All responses are wrapped in a consistent `ApiResponse` format
