# 🌐 Chrome FCM Token Guide - Get Real Tokens Instead of Dummy Tokens

## Overview
This guide shows you how to get real FCM tokens from Chrome browsers instead of using `dummy_token_123` for testing push notifications.

## 🔥 Step-by-Step Setup

### Step 1: Get Firebase Configuration

1. **Go to Firebase Console:**
   - Visit: https://console.firebase.google.com/project/aigreentick-40943
   - Sign in with your Google account

2. **Add Web App:**
   - Click ⚙️ Settings → Project Settings
   - Scroll down to "Your apps" section
   - Click "Add app" → Web (</>)
   - Enter app nickname: "Chrome FCM Test"
   - Click "Register app"

3. **Copy Configuration:**
   ```javascript
   const firebaseConfig = {
     apiKey: "AIzaSyXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX",
     authDomain: "aigreentick-40943.firebaseapp.com",
     projectId: "aigreentick-40943",
     storageBucket: "aigreentick-40943.appspot.com",
     messagingSenderId: "123456789012",
     appId: "1:123456789012:web:abcdef1234567890abcdef"
   };
   ```

### Step 2: Get VAPID Key

1. **Navigate to Cloud Messaging:**
   - In Firebase Console, go to Project Settings
   - Click "Cloud Messaging" tab

2. **Generate VAPID Key:**
   - Scroll to "Web Push certificates" section
   - Click "Generate Key Pair"
   - Copy the generated public key

3. **VAPID Key Example:**
   ```
   BEl62iUYgUivxIkv69yViEuiBIa40HI0F2hPqVCL9b7bqQzPfjBM0y8uJBGX0ypD1I_gH0Vzp5FXWkfQYL7gK88
   ```

### Step 3: Update HTML File

Replace these values in `test-web-push.html`:

```javascript
// Replace with your actual Firebase config
const firebaseConfig = {
    apiKey: "YOUR_API_KEY_HERE",
    authDomain: "aigreentick-40943.firebaseapp.com",
    projectId: "aigreentick-40943",
    storageBucket: "aigreentick-40943.appspot.com",
    messagingSenderId: "YOUR_SENDER_ID_HERE",
    appId: "YOUR_APP_ID_HERE"
};

// Replace with your actual VAPID key
const VAPID_KEY = "YOUR_VAPID_KEY_HERE";
```

### Step 4: Test the Setup

1. **Open HTML File:**
   - Open `test-web-push.html` in Chrome
   - Make sure you're serving it from a web server (not file://)

2. **Generate Token:**
   - Click "Initialize Firebase"
   - Click "Request Notification Permission"
   - Click "Get FCM Token"
   - Copy the generated token

3. **Test with Your API:**
   ```bash
   curl -X POST "http://localhost:9091/api/v1/notifications/push" \
     -H "Content-Type: application/json" \
     -d '{
       "deviceIds": ["YOUR_REAL_FCM_TOKEN_HERE"],
       "title": "Test from Chrome",
       "description": "This notification was sent from Chrome browser!",
       "imageUrl": "https://via.placeholder.com/300x200.png"
     }'
   ```

## 📱 Real FCM Token Example

A real FCM token looks like this:
```
fGHjKlMnOpQrStUvWxYz1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890abcdefghijklmnopqrstuvwxyz
```

## 🧪 Testing with Postman

### Request:
```json
{
  "deviceIds": ["fGHjKlMnOpQrStUvWxYz1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890abcdefghijklmnopqrstuvwxyz"],
  "title": "Chrome Push Test",
  "description": "Testing push notifications from Chrome browser",
  "imageUrl": "https://via.placeholder.com/300x200.png"
}
```

### Expected Response (Success):
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

## 🔧 Troubleshooting

### Issue: "Firebase not initialized"
**Solution:** Check that you've updated the Firebase config with real values

### Issue: "VAPID key is invalid"
**Solution:** Make sure you've generated and copied the VAPID key correctly

### Issue: "Permission denied"
**Solution:** Make sure Chrome has notification permissions enabled

### Issue: "Token generation failed"
**Solution:** Check browser console for detailed error messages

## 🌐 Serving the HTML File

### Option 1: Simple HTTP Server
```bash
# Python 3
python -m http.server 8000

# Python 2
python -m SimpleHTTPServer 8000

# Node.js
npx http-server
```

### Option 2: Live Server (VS Code Extension)
- Install "Live Server" extension in VS Code
- Right-click on `test-web-push.html`
- Select "Open with Live Server"

## 📊 Monitoring

### Check Firebase Console:
1. Go to Cloud Messaging
2. View message statistics
3. Check delivery reports

### Check Application Logs:
Look for these messages:
```
INFO - Push notification sent successfully to device: fGHjKlMnOpQrStUvWxYz..., response: projects/aigreentick-40943/messages/0:1234567890
```

## ✅ Success Checklist

- [ ] Firebase project configured
- [ ] Web app added to Firebase project
- [ ] VAPID key generated and copied
- [ ] HTML file updated with real config
- [ ] Chrome notification permission granted
- [ ] FCM token generated successfully
- [ ] Token used in API call
- [ ] Push notification received in Chrome

## 🚀 Next Steps

1. **Test with Multiple Browsers:** Chrome, Firefox, Safari
2. **Test with Different Devices:** Desktop, Mobile
3. **Implement in Your App:** Integrate FCM token generation
4. **Monitor Performance:** Track delivery rates and errors

Your Chrome FCM token setup is now complete! You can use real tokens instead of dummy tokens for testing.
