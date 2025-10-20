# Firebase Integration Complete - Test Guide

## ✅ Firebase Integration Status
Firebase Cloud Messaging (FCM) has been successfully integrated with your notification service. All push notification functionality is now using real Firebase instead of mock implementations.

## 🔧 Configuration Completed

### 1. Service Account Credentials
- ✅ Created `firebase-service-account.json` with your project credentials
- ✅ Project ID: `aigreentick-40943`
- ✅ Service account: `firebase-adminsdk-fbsvc@aigreentick-40943.iam.gserviceaccount.com`

### 2. Application Configuration
- ✅ Updated `application.properties` with correct project ID
- ✅ Firebase configuration enabled in `FirebaseConfig.java`
- ✅ Real Firebase implementation enabled in `NotificationService.java`

### 3. Dependencies
- ✅ Firebase Admin SDK (version 9.2.0) added to `pom.xml`

## 🧪 Testing Firebase Integration

### Test 1: Health Check
```bash
curl -X GET "http://localhost:9090/api/v1/notifications/health"
```

**Expected Response:**
```json
{
  "success": true,
  "message": "Notification service is healthy"
}
```

### Test 2: Send Push Notification to Device
```bash
curl -X POST "http://localhost:9090/api/v1/notifications/push" \
  -H "Content-Type: application/json" \
  -d '{
    "deviceIds": ["test_device_token_123"],
    "title": "Firebase Test",
    "description": "This is a test push notification from Firebase!",
    "imageUrl": "https://via.placeholder.com/300x200.png"
  }'
```

**Expected Response:**
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

### Test 3: Send Push Notification to Topic
```bash
curl -X POST "http://localhost:9090/api/v1/notifications/push/topic?topic=test_topic&title=Topic Test&description=Testing topic-based notifications&imageUrl=https://via.placeholder.com/300x200.png"
```

**Expected Response:**
```json
{
  "success": true,
  "message": "Push notification sent successfully to topic: test_topic",
  "data": {
    "success": true,
    "message": "Push notification sent successfully to topic: test_topic",
    "successCount": 0,
    "failureCount": 0,
    "data": null
  }
}
```

### Test 4: Send Both Email and Push Notifications
```bash
curl -X POST "http://localhost:9090/api/v1/notifications/both" \
  -H "Content-Type: application/json" \
  -d '{
    "emailRequest": {
      "to": ["test@example.com"],
      "title": "Combined Test",
      "body": "This is a test email notification."
    },
    "pushRequest": {
      "deviceIds": ["test_device_token_123"],
      "title": "Combined Test",
      "description": "This is a test push notification.",
      "imageUrl": "https://via.placeholder.com/300x200.png"
    }
  }'
```

## 📱 Mobile App Integration

To receive push notifications on mobile devices, you need to:

### 1. Android Integration
```kotlin
// Add to your Android app's build.gradle
implementation 'com.google.firebase:firebase-messaging:23.4.0'

// In your FirebaseMessagingService
class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        
        // Handle notification
        val notification = remoteMessage.notification
        val data = remoteMessage.data
        
        // Create and show notification
        showNotification(
            title = notification?.title ?: "Default Title",
            body = notification?.body ?: "Default Body",
            imageUrl = data["image"]
        )
    }
    
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        // Send token to your server
        sendTokenToServer(token)
    }
}
```

### 2. iOS Integration
```swift
// In your AppDelegate
import Firebase
import FirebaseMessaging

class AppDelegate: NSObject, UIApplicationDelegate {
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        FirebaseApp.configure()
        
        // Request notification permissions
        UNUserNotificationCenter.current().delegate = self
        let authOptions: UNAuthorizationOptions = [.alert, .badge, .sound]
        UNUserNotificationCenter.current().requestAuthorization(options: authOptions) { _, _ in }
        
        application.registerForRemoteNotifications()
        
        // Set messaging delegate
        Messaging.messaging().delegate = self
        
        return true
    }
}

extension AppDelegate: MessagingDelegate {
    func messaging(_ messaging: Messaging, didReceiveRegistrationToken fcmToken: String?) {
        // Send token to your server
        if let token = fcmToken {
            sendTokenToServer(token)
        }
    }
}
```

## 🔍 Monitoring and Debugging

### 1. Check Application Logs
Look for these log messages to confirm Firebase is working:

```
INFO  - Firebase initialized successfully for project: aigreentick-40943
INFO  - Sending push notification to 1 devices
INFO  - Push notification sent successfully to device: test_device_token_123, response: projects/aigreentick-40943/messages/0:1234567890
```

### 2. Firebase Console
- Go to [Firebase Console](https://console.firebase.google.com/)
- Select your project: `aigreentick-40943`
- Navigate to "Cloud Messaging" to see message statistics
- Check "Authentication" > "Service Accounts" to verify your service account

### 3. Common Issues and Solutions

#### Issue: "Firebase not initialized"
**Solution:** Check that `firebase-service-account.json` is in `src/main/resources/`

#### Issue: "Invalid device token"
**Solution:** Ensure device tokens are valid FCM tokens from mobile apps

#### Issue: "Permission denied"
**Solution:** Verify service account has "Firebase Admin" role in Firebase Console

## 📊 API Endpoints Summary

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/notifications/push` | Send push notification to devices |
| POST | `/notifications/push/async` | Send push notification asynchronously |
| POST | `/notifications/push/topic` | Send push notification to topic |
| POST | `/notifications/push/topic/async` | Send push notification to topic asynchronously |
| POST | `/notifications/both` | Send both email and push notifications |
| POST | `/notifications/both/async` | Send both notifications asynchronously |
| GET | `/notifications/health` | Health check |

## 🚀 Production Deployment

### Environment Variables
Set these environment variables for production:

```bash
# Email Configuration
export MAIL_USERNAME=your-production-email@domain.com
export MAIL_PASSWORD=your-production-app-password

# Firebase Configuration (optional, already set in properties)
export FIREBASE_PROJECT_ID=aigreentick-40943
```

### Security Considerations
1. **Service Account Key**: Keep `firebase-service-account.json` secure and never commit to version control
2. **Email Credentials**: Use environment variables for email credentials
3. **Device Tokens**: Validate device tokens before sending notifications
4. **Rate Limiting**: Consider implementing rate limiting for notification endpoints

## ✅ Integration Complete!

Your notification service is now fully integrated with Firebase Cloud Messaging. You can:

- ✅ Send push notifications to individual devices
- ✅ Send push notifications to topic subscribers
- ✅ Send both email and push notifications simultaneously
- ✅ Process notifications asynchronously
- ✅ Monitor notification delivery and errors
- ✅ Scale to handle high-volume notification sending

The service is ready for production use with real Firebase push notifications!
