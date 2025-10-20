# Testing Email and Push Notifications - Complete Guide

## 🧪 Testing Setup

### Prerequisites
1. **Application Running**: Ensure your Spring Boot application is running on `http://localhost:9090`
2. **Email Configuration**: Configure your email settings in `application.properties`
3. **Firebase Integration**: Firebase is already configured and ready

## 📧 Testing Email Notifications

### Step 1: Configure Email Settings

Update your `application.properties` with real email credentials:

```properties
# Replace with your actual email settings
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

**For Gmail:**
- Use your Gmail address as username
- Generate an "App Password" (not your regular password)
- Go to Google Account → Security → 2-Step Verification → App passwords

### Step 2: Test Email Notification

#### Test 1: Basic Email Notification
```bash
curl -X POST "http://localhost:9090/api/v1/notifications/email" \
  -H "Content-Type: application/json" \
  -d '{
    "to": ["test@example.com", "user@example.com"],
    "cc": ["manager@example.com"],
    "title": "Test Email Notification",
    "body": "This is a test email notification from AI Green Tick Notification Service. If you receive this, the email functionality is working correctly!"
  }'
```

**Expected Response:**
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

#### Test 2: Email with Validation Errors
```bash
curl -X POST "http://localhost:9090/api/v1/notifications/email" \
  -H "Content-Type: application/json" \
  -d '{
    "to": ["invalid-email"],
    "title": "",
    "body": "Test"
  }'
```

**Expected Response (400 Bad Request):**
```json
{
  "success": false,
  "message": "Validation failed",
  "data": {
    "to": "Invalid email format for 'to' field",
    "title": "Email title is required"
  }
}
```

#### Test 3: Async Email Notification
```bash
curl -X POST "http://localhost:9090/api/v1/notifications/email/async" \
  -H "Content-Type: application/json" \
  -d '{
    "to": ["test@example.com"],
    "title": "Async Email Test",
    "body": "This email is being sent asynchronously."
  }'
```

**Expected Response (202 Accepted):**
```json
{
  "success": true,
  "message": "Email notification is being processed asynchronously"
}
```

## 📱 Testing Push Notifications

### Step 1: Get Device Tokens

For testing, you can use dummy device tokens or get real ones from mobile apps:

#### Option A: Use Dummy Tokens (for testing Firebase connection)
```bash
# These are dummy tokens - Firebase will return "invalid token" errors
# but you can verify the service is working
curl -X POST "http://localhost:9090/api/v1/notifications/push" \
  -H "Content-Type: application/json" \
  -d '{
    "deviceIds": ["dummy_token_123", "dummy_token_456"],
    "title": "Test Push Notification",
    "description": "This is a test push notification from Firebase!",
    "imageUrl": "https://via.placeholder.com/300x200.png"
  }'
```

#### Option B: Get Real Device Tokens
To get real device tokens, you need a mobile app. Here's how:

**Android (Kotlin):**
```kotlin
FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
    if (!task.isSuccessful) {
        Log.w(TAG, "Fetching FCM registration token failed", task.exception)
        return@addOnCompleteListener
    }
    
    // Get new FCM registration token
    val token = task.result
    Log.d(TAG, "FCM Token: $token")
    
    // Send this token to your server
    sendTokenToServer(token)
}
```

**iOS (Swift):**
```swift
Messaging.messaging().token { token, error in
    if let error = error {
        print("Error fetching FCM registration token: \(error)")
    } else if let token = token {
        print("FCM registration token: \(token)")
        // Send this token to your server
        sendTokenToServer(token)
    }
}
```

### Step 2: Test Push Notifications

#### Test 1: Basic Push Notification
```bash
curl -X POST "http://localhost:9090/api/v1/notifications/push" \
  -H "Content-Type: application/json" \
  -d '{
    "deviceIds": ["REAL_DEVICE_TOKEN_HERE"],
    "title": "Test Push Notification",
    "description": "This is a test push notification from Firebase Cloud Messaging!",
    "imageUrl": "https://via.placeholder.com/300x200.png"
  }'
```

**Expected Response (Success):**
```json
{
  "success": true,
  "message": "Push notification sent. Success: 1, Failed: 0",
  "data": {
    "success": true,
    "message": "Push notification sent. Success: 1, Failed: 0",
    "successCount": 1,
    "failureCount": 0,
    "data": null
  }
}
```

**Expected Response (Invalid Token):**
```json
{
  "success": true,
  "message": "Push notification sent. Success: 0, Failed: 1",
  "data": {
    "success": true,
    "message": "Push notification sent. Success: 0, Failed: 1",
    "successCount": 0,
    "failureCount": 1,
    "data": [
      "Failed to send push notification to device dummy_token_123: Requested entity was not found."
    ]
  }
}
```

#### Test 2: Topic-Based Push Notification
```bash
curl -X POST "http://localhost:9090/api/v1/notifications/push/topic?topic=news&title=Breaking News&description=Important news update&imageUrl=https://via.placeholder.com/300x200.png"
```

**Expected Response:**
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

#### Test 3: Async Push Notification
```bash
curl -X POST "http://localhost:9090/api/v1/notifications/push/async" \
  -H "Content-Type: application/json" \
  -d '{
    "deviceIds": ["REAL_DEVICE_TOKEN_HERE"],
    "title": "Async Push Test",
    "description": "This push notification is being sent asynchronously.",
    "imageUrl": "https://via.placeholder.com/300x200.png"
  }'
```

## 🔄 Testing Combined Notifications

### Test Both Email and Push Together
```bash
curl -X POST "http://localhost:9090/api/v1/notifications/both" \
  -H "Content-Type: application/json" \
  -d '{
    "emailRequest": {
      "to": ["test@example.com"],
      "cc": ["admin@example.com"],
      "title": "Combined Notification Test",
      "body": "This is a test email sent along with a push notification."
    },
    "pushRequest": {
      "deviceIds": ["REAL_DEVICE_TOKEN_HERE"],
      "title": "Combined Notification Test",
      "description": "This is a test push notification sent along with an email.",
      "imageUrl": "https://via.placeholder.com/300x200.png"
    }
  }'
```

**Expected Response:**
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

## 🔍 Monitoring and Debugging

### Check Application Logs
Look for these log messages:

**Email Success:**
```
INFO  - Sending email notification to 2 recipients
INFO  - Email sent successfully to: test@example.com
INFO  - Email sent successfully to: user@example.com
```

**Push Notification Success:**
```
INFO  - Sending push notification to 1 devices
INFO  - Push notification sent successfully to device: REAL_TOKEN, response: projects/aigreentick-40943/messages/0:1234567890
```

**Firebase Initialization:**
```
INFO  - Firebase initialized successfully for project: aigreentick-40943
```

### Health Check
```bash
curl -X GET "http://localhost:9090/api/v1/notifications/health"
```

### Validate Email Address
```bash
curl -X GET "http://localhost:9090/api/v1/notifications/validate/email?email=test@example.com"
```

### Validate Device Token
```bash
curl -X GET "http://localhost:9090/api/v1/notifications/validate/device-token?token=abc123def456"
```

## 🐛 Common Issues and Solutions

### Email Issues

#### Issue: "Authentication failed"
**Solution:** 
- Check email credentials in `application.properties`
- For Gmail, use App Password instead of regular password
- Ensure 2-Factor Authentication is enabled

#### Issue: "Connection refused"
**Solution:**
- Check SMTP host and port settings
- Verify firewall settings
- Try different SMTP providers (Outlook, Yahoo, etc.)

### Push Notification Issues

#### Issue: "Invalid device token"
**Solution:**
- Use real FCM tokens from mobile apps
- Ensure tokens are not expired
- Check if the app is properly configured with Firebase

#### Issue: "Firebase not initialized"
**Solution:**
- Check that `firebase-service-account.json` exists in `src/main/resources/`
- Verify project ID matches in `application.properties`
- Check application logs for Firebase initialization messages

## 📊 Testing Checklist

### Email Testing ✅
- [ ] Configure email credentials
- [ ] Test basic email sending
- [ ] Test email with CC
- [ ] Test email validation
- [ ] Test async email sending
- [ ] Check email delivery

### Push Notification Testing ✅
- [ ] Verify Firebase initialization
- [ ] Test with dummy tokens (should show errors)
- [ ] Get real device tokens from mobile app
- [ ] Test push notification to devices
- [ ] Test topic-based notifications
- [ ] Test async push notifications
- [ ] Check push notification delivery

### Combined Testing ✅
- [ ] Test both email and push together
- [ ] Test async combined notifications
- [ ] Verify both notifications are sent
- [ ] Check error handling for both types

## 🚀 Production Testing

For production testing:

1. **Use real email addresses** you can access
2. **Use real device tokens** from actual mobile apps
3. **Test with different email providers** (Gmail, Outlook, etc.)
4. **Test with different mobile devices** (Android, iOS)
5. **Monitor Firebase Console** for delivery statistics
6. **Check email delivery** in inbox/spam folders

## 📱 Mobile App Testing

To test with real mobile apps:

1. **Create a simple mobile app** with Firebase integration
2. **Get FCM tokens** from the app
3. **Send tokens to your notification service**
4. **Test push notifications** from your service
5. **Verify notifications appear** on the device

Your notification service is now ready for comprehensive testing with both email and push notifications!
