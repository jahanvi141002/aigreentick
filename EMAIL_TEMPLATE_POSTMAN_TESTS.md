# 📧 Email Template API - Postman Test Collection

## Base URL
```
http://localhost:9091/api/v1/email-templates
```

## 📋 Complete Postman Test Collection

### 1. Health Check
**Method:** `GET`  
**URL:** `http://localhost:9091/api/v1/email-templates/health`

**Expected Response:**
```json
{
  "success": true,
  "message": "Email template service is healthy"
}
```

---

### 2. Create Email Template
**Method:** `POST`  
**URL:** `http://localhost:9091/api/v1/email-templates`  
**Headers:** `Content-Type: application/json`

**Request Body:**
```json
{
  "name": "welcome-email",
  "title": "Welcome to AI Green Tick!",
  "body": "Dear {{name}}, welcome to our platform! We are excited to have you on board. Your account has been successfully created.",
  "isActive": true,
  "createdBy": "admin@aigreentick.com",
  "modifiedBy": "admin@aigreentick.com"
}
```

**Expected Response (201 Created):**
```json
{
  "success": true,
  "message": "Email template created successfully",
  "data": {
    "id": 1,
    "name": "welcome-email",
    "title": "Welcome to AI Green Tick!",
    "body": "Dear {{name}}, welcome to our platform! We are excited to have you on board. Your account has been successfully created.",
    "isActive": true,
    "createdBy": "admin@aigreentick.com",
    "modifiedBy": "admin@aigreentick.com",
    "createdAt": "2024-01-15T10:30:00",
    "modifiedAt": "2024-01-15T10:30:00",
    "version": 0
  }
}
```

---

### 3. Create Multiple Templates (Test Data)

#### Template 2: Password Reset
**Method:** `POST`  
**URL:** `http://localhost:9091/api/v1/email-templates`

```json
{
  "name": "password-reset",
  "title": "Reset Your Password - AI Green Tick",
  "body": "Dear {{name}}, you requested a password reset. Click the link below to reset your password: {{resetLink}} This link will expire in 24 hours.",
  "isActive": true,
  "createdBy": "admin@aigreentick.com",
  "modifiedBy": "admin@aigreentick.com"
}
```

#### Template 3: Order Confirmation
**Method:** `POST`  
**URL:** `http://localhost:9091/api/v1/email-templates`

```json
{
  "name": "order-confirmation",
  "title": "Order Confirmation - Order #{{orderNumber}}",
  "body": "Dear {{customerName}}, thank you for your order! Order Details: Order Number: {{orderNumber}} Total Amount: ${{totalAmount}} Expected Delivery: {{deliveryDate}}",
  "isActive": true,
  "createdBy": "admin@aigreentick.com",
  "modifiedBy": "admin@aigreentick.com"
}
```

#### Template 4: Newsletter
**Method:** `POST`  
**URL:** `http://localhost:9091/api/v1/email-templates`

```json
{
  "name": "newsletter-monthly",
  "title": "Monthly Newsletter - {{month}} {{year}}",
  "body": "Dear {{subscriberName}}, here's your monthly newsletter with the latest updates from AI Green Tick. Featured Articles: {{featuredArticles}}",
  "isActive": true,
  "createdBy": "admin@aigreentick.com",
  "modifiedBy": "admin@aigreentick.com"
}
```

#### Template 5: Account Deactivation
**Method:** `POST`  
**URL:** `http://localhost:9091/api/v1/email-templates`

```json
{
  "name": "account-deactivation",
  "title": "Account Deactivation Notice",
  "body": "Dear {{name}}, your account has been deactivated due to inactivity. If you wish to reactivate your account, please contact our support team.",
  "isActive": false,
  "createdBy": "admin@aigreentick.com",
  "modifiedBy": "admin@aigreentick.com"
}
```

---

### 4. Get All Templates (Paginated)
**Method:** `GET`  
**URL:** `http://localhost:9091/api/v1/email-templates?page=0&size=5&sortBy=name&sortDir=asc`

**Expected Response:**
```json
{
  "success": true,
  "message": "Email templates retrieved successfully",
  "data": {
    "content": [
      {
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
    "totalElements": 5,
    "totalPages": 1,
    "first": true,
    "last": true,
    "numberOfElements": 5
  }
}
```

---

### 5. Get All Templates (No Pagination)
**Method:** `GET`  
**URL:** `http://localhost:9091/api/v1/email-templates/all`

---

### 6. Get Template by ID
**Method:** `GET`  
**URL:** `http://localhost:9091/api/v1/email-templates/1`

---

### 7. Get Template by Name
**Method:** `GET`  
**URL:** `http://localhost:9091/api/v1/email-templates/name/welcome-email`

---

### 8. Get Active Templates
**Method:** `GET`  
**URL:** `http://localhost:9091/api/v1/email-templates/active`

---

### 9. Get Inactive Templates
**Method:** `GET`  
**URL:** `http://localhost:9091/api/v1/email-templates/inactive`

---

### 10. Search Templates by Name
**Method:** `GET`  
**URL:** `http://localhost:9091/api/v1/email-templates/search/name?q=welcome`

**Expected Response:**
```json
{
  "success": true,
  "message": "Search results retrieved successfully",
  "data": [
    {
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
    }
  ]
}
```

---

### 11. Search Templates by Title
**Method:** `GET`  
**URL:** `http://localhost:9091/api/v1/email-templates/search/title?q=password`

---

### 12. Update Template
**Method:** `PUT`  
**URL:** `http://localhost:9091/api/v1/email-templates/1`  
**Headers:** `Content-Type: application/json`

**Request Body:**
```json
{
  "name": "welcome-email-v2",
  "title": "Welcome to AI Green Tick - Enhanced!",
  "body": "Dear {{name}}, welcome to our enhanced platform! We've added new features just for you. Your account is ready to use.",
  "isActive": true,
  "modifiedBy": "admin@aigreentick.com"
}
```

**Expected Response:**
```json
{
  "success": true,
  "message": "Email template updated successfully",
  "data": {
    "id": 1,
    "name": "welcome-email-v2",
    "title": "Welcome to AI Green Tick - Enhanced!",
    "body": "Dear {{name}}, welcome to our enhanced platform! We've added new features just for you. Your account is ready to use.",
    "isActive": true,
    "createdBy": "admin@aigreentick.com",
    "modifiedBy": "admin@aigreentick.com",
    "createdAt": "2024-01-15T10:30:00",
    "modifiedAt": "2024-01-15T11:45:00",
    "version": 1
  }
}
```

---

### 13. Activate Template
**Method:** `PATCH`  
**URL:** `http://localhost:9091/api/v1/email-templates/5/activate?modifiedBy=admin@aigreentick.com`

**Expected Response:**
```json
{
  "success": true,
  "message": "Email template activated successfully",
  "data": {
    "id": 5,
    "name": "account-deactivation",
    "title": "Account Deactivation Notice",
    "body": "Dear {{name}}, your account has been deactivated due to inactivity.",
    "isActive": true,
    "createdBy": "admin@aigreentick.com",
    "modifiedBy": "admin@aigreentick.com",
    "createdAt": "2024-01-15T10:30:00",
    "modifiedAt": "2024-01-15T12:00:00",
    "version": 1
  }
}
```

---

### 14. Deactivate Template
**Method:** `PATCH`  
**URL:** `http://localhost:9091/api/v1/email-templates/3/deactivate?modifiedBy=admin@aigreentick.com`

---

### 15. Get Template Statistics
**Method:** `GET`  
**URL:** `http://localhost:9091/api/v1/email-templates/statistics`

**Expected Response:**
```json
{
  "success": true,
  "message": "Template statistics retrieved successfully",
  "data": {
    "totalTemplates": 5,
    "activeTemplates": 4,
    "inactiveTemplates": 1
  }
}
```

---

### 16. Delete Template
**Method:** `DELETE`  
**URL:** `http://localhost:9091/api/v1/email-templates/5`

**Expected Response:**
```json
{
  "success": true,
  "message": "Email template deleted successfully"
}
```

---

## 🧪 Error Testing Examples

### 1. Create Template with Missing Required Fields
**Method:** `POST`  
**URL:** `http://localhost:9091/api/v1/email-templates`

```json
{
  "name": "",
  "title": "",
  "body": "",
  "isActive": true,
  "createdBy": ""
}
```

**Expected Response (400 Bad Request):**
```json
{
  "success": false,
  "message": "Validation failed",
  "data": {
    "name": "Template name is required",
    "title": "Template title is required",
    "body": "Template body is required",
    "createdBy": "Created by is required"
  }
}
```

### 2. Create Duplicate Template
**Method:** `POST`  
**URL:** `http://localhost:9091/api/v1/email-templates`

```json
{
  "name": "welcome-email",
  "title": "Another Welcome Email",
  "body": "This should fail because name already exists",
  "isActive": true,
  "createdBy": "admin@aigreentick.com"
}
```

**Expected Response (409 Conflict):**
```json
{
  "success": false,
  "message": "Email template with name 'welcome-email' already exists"
}
```

### 3. Get Non-existent Template
**Method:** `GET`  
**URL:** `http://localhost:9091/api/v1/email-templates/999`

**Expected Response (404 Not Found):**
```json
{
  "success": false,
  "message": "Email template with ID 999 not found"
}
```

---

## 📊 Postman Collection Import

### Collection JSON for Import:
```json
{
  "info": {
    "name": "Email Template API Tests",
    "description": "Complete test collection for Email Template API",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "item": [
    {
      "name": "Health Check",
      "request": {
        "method": "GET",
        "header": [],
        "url": {
          "raw": "http://localhost:9091/api/v1/email-templates/health",
          "protocol": "http",
          "host": ["localhost"],
          "port": "9091",
          "path": ["api", "v1", "email-templates", "health"]
        }
      }
    },
    {
      "name": "Create Welcome Template",
      "request": {
        "method": "POST",
        "header": [
          {
            "key": "Content-Type",
            "value": "application/json"
          }
        ],
        "body": {
          "mode": "raw",
          "raw": "{\n  \"name\": \"welcome-email\",\n  \"title\": \"Welcome to AI Green Tick!\",\n  \"body\": \"Dear {{name}}, welcome to our platform!\",\n  \"isActive\": true,\n  \"createdBy\": \"admin@aigreentick.com\"\n}"
        },
        "url": {
          "raw": "http://localhost:9091/api/v1/email-templates",
          "protocol": "http",
          "host": ["localhost"],
          "port": "9091",
          "path": ["api", "v1", "email-templates"]
        }
      }
    }
  ]
}
```

---

## 🚀 Quick Test Sequence

1. **Health Check** → Verify service is running
2. **Create Template** → Create welcome-email template
3. **Get All Templates** → Verify template was created
4. **Get by ID** → Get specific template
5. **Search by Name** → Test search functionality
6. **Update Template** → Modify existing template
7. **Get Statistics** → Check template counts
8. **Activate/Deactivate** → Test status changes
9. **Delete Template** → Test deletion

Your Email Template API is ready for comprehensive testing! 🎯
