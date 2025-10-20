# Notification Service API Documentation

## Overview
This API provides comprehensive notification services including email notifications via JavaMailSender and push notifications via Firebase Cloud Messaging (FCM). The service supports both synchronous and asynchronous operations.

## Base URL
```
/api/v1/notifications
```

## Features
- ✅ Email notifications with multiple recipients and CC support
- ✅ Push notifications to multiple devices via Firebase Cloud Messaging
- ✅ Topic-based push notifications via Firebase
- ✅ Asynchronous processing
- ✅ Combined email and push notifications
- ✅ Input validation and error handling
- ✅ Production-ready Firebase integration

## Configuration

### Email Configuration
Update `application.properties`:
```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=${MAIL_USERNAME:your-email@gmail.com}
spring.mail.password=${MAIL_PASSWORD:your-app-password}
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

### Firebase Configuration ✅ COMPLETED
Firebase Cloud Messaging is fully configured and ready to use:

- ✅ Service account credentials: `firebase-service-account.json`
- ✅ Project ID: `aigreentick-40943`
- ✅ Firebase Admin SDK integrated
- ✅ Real push notifications enabled

**No additional configuration needed!**

## API Endpoints

### 1. Send Email Notification
**POST** `/notifications/email`

Sends email notification to multiple recipients.

**Request Body:**
```json
{
  "to": ["user1@example.com", "user2@example.com"],
  "cc": ["manager@example.com"],
  "title": "Important Update",
  "body": "This is an important notification about system updates."
}
```

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Email notification sent. Success: 2, Failed: 0",
  "data": {
    "success": true,
    "message": "Email notification sent. Success: 2, Failed: 0",
    "successCount": 2,
    "failureCount": 0,
    "data": null
  }
}
```

### 2. Send Email Notification (Async)
**POST** `/notifications/email/async`

Sends email notification asynchronously.

**Request Body:** Same as above

**Response (202 Accepted):**
```json
{
  "success": true,
  "message": "Email notification is being processed asynchronously"
}
```

### 3. Send Push Notification
**POST** `/notifications/push`

Sends push notification to multiple devices.

**Request Body:**
```json
{
  "deviceIds": ["device_token_1", "device_token_2", "device_token_3"],
  "title": "New Message",
  "description": "You have received a new message from John Doe.",
  "imageUrl": "https://example.com/notification-image.jpg"
}
```

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Push notification sent. Success: 3, Failed: 0",
  "data": {
    "success": true,
    "message": "Push notification sent. Success: 3, Failed: 0",
    "successCount": 3,
    "failureCount": 0,
    "data": null
  }
}
```

### 4. Send Push Notification (Async)
**POST** `/notifications/push/async`

Sends push notification asynchronously.

**Request Body:** Same as above

**Response (202 Accepted):**
```json
{
  "success": true,
  "message": "Push notification is being processed asynchronously"
}
```

### 5. Send Push Notification to Topic
**POST** `/notifications/push/topic`

Sends push notification to all devices subscribed to a topic.

**Query Parameters:**
- `topic` (required): Topic name
- `title` (required): Notification title
- `description` (required): Notification description
- `imageUrl` (optional): Image URL

**Example:** `/notifications/push/topic?topic=news&title=Breaking News&description=Important news update&imageUrl=https://example.com/news.jpg`

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Push notification sent successfully to topic: news",
  "data": {
    "success": true,
    "message": "Push notification sent successfully to topic: news",
    "successCount": 0,
    "failureCount": 0,
    "data": null
  }
}
```

### 6. Send Push Notification to Topic (Async)
**POST** `/notifications/push/topic/async`

Sends push notification to topic asynchronously.

**Query Parameters:** Same as above

**Response (202 Accepted):**
```json
{
  "success": true,
  "message": "Push notification to topic is being processed asynchronously"
}
```

### 7. Send Both Email and Push Notifications
**POST** `/notifications/both`

Sends both email and push notifications simultaneously.

**Request Body:**
```json
{
  "emailRequest": {
    "to": ["user@example.com"],
    "cc": ["admin@example.com"],
    "title": "System Update",
    "body": "System will be updated tonight at 2 AM."
  },
  "pushRequest": {
    "deviceIds": ["device_token_1"],
    "title": "System Update",
    "description": "System will be updated tonight at 2 AM.",
    "imageUrl": "https://example.com/update.jpg"
  }
}
```

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Notifications sent. Email Success: 1, Email Failed: 0, Push Success: 1, Push Failed: 0",
  "data": {
    "success": true,
    "message": "Notifications sent. Email Success: 1, Email Failed: 0, Push Success: 1, Push Failed: 0",
    "successCount": 2,
    "failureCount": 0,
    "data": null
  }
}
```

### 8. Send Both Notifications (Async)
**POST** `/notifications/both/async`

Sends both email and push notifications asynchronously.

**Request Body:** Same as above

**Response (202 Accepted):**
```json
{
  "success": true,
  "message": "Both notifications are being processed asynchronously"
}
```

### 9. Validate Email Address
**GET** `/notifications/validate/email?email={email}`

Validates an email address format.

**Example:** `/notifications/validate/email?email=user@example.com`

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Email address is valid",
  "data": true
}
```

### 10. Validate Device Token
**GET** `/notifications/validate/device-token?token={token}`

Validates a device token format.

**Example:** `/notifications/validate/device-token?token=abc123def456`

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Device token is valid",
  "data": true
}
```

### 11. Health Check
**GET** `/notifications/health`

Health check endpoint for the notification service.

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Notification service is healthy"
}
```

## Data Models

### EmailNotificationRequest
```json
{
  "to": ["string"],           // Required: List of recipient email addresses
  "cc": ["string"],           // Optional: List of CC email addresses
  "title": "string",          // Required: Email subject
  "body": "string"            // Required: Email content
}
```

### PushNotificationRequest
```json
{
  "deviceIds": ["string"],    // Required: List of device tokens
  "title": "string",          // Required: Notification title
  "description": "string",    // Required: Notification description
  "imageUrl": "string"        // Optional: Image URL for notification
}
```

### NotificationResponse
```json
{
  "success": true,            // Boolean: Operation success status
  "message": "string",        // String: Response message
  "successCount": 0,          // Integer: Number of successful operations
  "failureCount": 0,          // Integer: Number of failed operations
  "data": null                // Object: Additional data (errors, etc.)
}
```

## Error Responses

### 400 Bad Request
```json
{
  "success": false,
  "message": "Validation failed",
  "data": {
    "to": "To recipients are required",
    "title": "Email title is required"
  }
}
```

### 500 Internal Server Error
```json
{
  "success": false,
  "message": "Failed to send email notification: SMTP connection failed"
}
```

## Validation Rules

### Email Notifications
- **to**: Required, non-empty list of valid email addresses
- **cc**: Optional, list of valid email addresses
- **title**: Required, non-blank string
- **body**: Required, non-blank string

### Push Notifications
- **deviceIds**: Required, non-empty list of device tokens
- **title**: Required, non-blank string
- **description**: Required, non-blank string
- **imageUrl**: Optional, valid URL format

## Usage Examples

### cURL Examples

#### Send Email Notification
```bash
curl -X POST "http://localhost:9090/api/v1/notifications/email" \
  -H "Content-Type: application/json" \
  -d '{
    "to": ["user1@example.com", "user2@example.com"],
    "cc": ["manager@example.com"],
    "title": "Important Update",
    "body": "This is an important notification about system updates."
  }'
```

#### Send Push Notification
```bash
curl -X POST "http://localhost:9090/api/v1/notifications/push" \
  -H "Content-Type: application/json" \
  -d '{
    "deviceIds": ["device_token_1", "device_token_2"],
    "title": "New Message",
    "description": "You have received a new message.",
    "imageUrl": "https://example.com/image.jpg"
  }'
```

#### Send to Topic
```bash
curl -X POST "http://localhost:9090/api/v1/notifications/push/topic?topic=news&title=Breaking News&description=Important update"
```

## Firebase Setup Instructions

To enable real Firebase push notifications:

1. **Create Firebase Project:**
   - Go to [Firebase Console](https://console.firebase.google.com/)
   - Create a new project or use existing one

2. **Generate Service Account Key:**
   - Go to Project Settings > Service Accounts
   - Click "Generate new private key"
   - Download the JSON file

3. **Add Service Account Key:**
   - Rename the downloaded file to `firebase-service-account.json`
   - Place it in `src/main/resources/`

4. **Set Environment Variables:**
   ```bash
   export FIREBASE_PROJECT_ID=your-project-id
   ```

5. **Enable Firebase Code:**
   - Uncomment Firebase-related code in `FirebaseConfig.java`
   - Uncomment Firebase-related code in `NotificationService.java`
   - Uncomment Firebase dependency in `pom.xml`

## Notes

1. **Firebase Integration**: Fully integrated with Firebase Cloud Messaging for production-ready push notifications.
2. **Async Processing**: Asynchronous endpoints return immediately with processing status.
3. **Error Handling**: Comprehensive error handling with detailed error messages.
4. **Validation**: Input validation for all request parameters.
5. **Logging**: Detailed logging for debugging and monitoring.
6. **Thread Safety**: Thread-safe implementation with proper concurrency handling.
7. **Scalability**: Designed to handle high-volume notification sending.
8. **Production Ready**: Service is ready for production deployment with real Firebase push notifications.
