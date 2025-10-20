@echo off
REM AI Green Tick Notification Service - Test Script (Windows)
REM This script tests both email and push notification functionality

set BASE_URL=http://localhost:9090/api/v1/notifications

echo 🧪 AI Green Tick Notification Service - Test Script
echo ==================================================
echo.

echo 🔍 Step 1: Health Check
echo ======================
curl -s -X GET "%BASE_URL%/health"
echo.
echo.

echo 📧 Step 2: Email Notification Tests
echo ===================================

echo Test 2.1: Basic Email Notification
curl -s -X POST "%BASE_URL%/email" ^
  -H "Content-Type: application/json" ^
  -d "{\"to\": [\"test@example.com\"], \"title\": \"Test Email from AI Green Tick\", \"body\": \"This is a test email notification. If you receive this, the email functionality is working!\"}"
echo.
echo.

echo Test 2.2: Email with CC
curl -s -X POST "%BASE_URL%/email" ^
  -H "Content-Type: application/json" ^
  -d "{\"to\": [\"user1@example.com\", \"user2@example.com\"], \"cc\": [\"manager@example.com\"], \"title\": \"Email with CC Test\", \"body\": \"This email includes CC recipients.\"}"
echo.
echo.

echo Test 2.3: Email Validation Test (Should Fail)
curl -s -X POST "%BASE_URL%/email" ^
  -H "Content-Type: application/json" ^
  -d "{\"to\": [\"invalid-email\"], \"title\": \"\", \"body\": \"This should fail validation\"}"
echo.
echo.

echo Test 2.4: Async Email Notification
curl -s -X POST "%BASE_URL%/email/async" ^
  -H "Content-Type: application/json" ^
  -d "{\"to\": [\"test@example.com\"], \"title\": \"Async Email Test\", \"body\": \"This email is being sent asynchronously.\"}"
echo.
echo.

echo 📱 Step 3: Push Notification Tests
echo ================================

echo Test 3.1: Push Notification with Dummy Token
curl -s -X POST "%BASE_URL%/push" ^
  -H "Content-Type: application/json" ^
  -d "{\"deviceIds\": [\"dummy_token_123\", \"dummy_token_456\"], \"title\": \"Test Push Notification\", \"description\": \"This is a test push notification from Firebase!\", \"imageUrl\": \"https://via.placeholder.com/300x200.png\"}"
echo.
echo.

echo Test 3.2: Topic-Based Push Notification
curl -s -X POST "%BASE_URL%/push/topic?topic=test_topic&title=Topic Test&description=Testing topic notifications&imageUrl=https://via.placeholder.com/300x200.png"
echo.
echo.

echo Test 3.3: Async Push Notification
curl -s -X POST "%BASE_URL%/push/async" ^
  -H "Content-Type: application/json" ^
  -d "{\"deviceIds\": [\"dummy_token_123\"], \"title\": \"Async Push Test\", \"description\": \"This push notification is being sent asynchronously.\", \"imageUrl\": \"https://via.placeholder.com/300x200.png\"}"
echo.
echo.

echo 🔄 Step 4: Combined Notification Tests
echo ======================================

echo Test 4.1: Both Email and Push Notifications
curl -s -X POST "%BASE_URL%/both" ^
  -H "Content-Type: application/json" ^
  -d "{\"emailRequest\": {\"to\": [\"test@example.com\"], \"title\": \"Combined Test Email\", \"body\": \"This email is sent along with a push notification.\"}, \"pushRequest\": {\"deviceIds\": [\"dummy_token_123\"], \"title\": \"Combined Test Push\", \"description\": \"This push notification is sent along with an email.\", \"imageUrl\": \"https://via.placeholder.com/300x200.png\"}}"
echo.
echo.

echo Test 4.2: Async Combined Notifications
curl -s -X POST "%BASE_URL%/both/async" ^
  -H "Content-Type: application/json" ^
  -d "{\"emailRequest\": {\"to\": [\"test@example.com\"], \"title\": \"Async Combined Email\", \"body\": \"This email is sent asynchronously with a push notification.\"}, \"pushRequest\": {\"deviceIds\": [\"dummy_token_123\"], \"title\": \"Async Combined Push\", \"description\": \"This push notification is sent asynchronously with an email.\", \"imageUrl\": \"https://via.placeholder.com/300x200.png\"}}"
echo.
echo.

echo 🔍 Step 5: Validation Tests
echo ===========================

echo Test 5.1: Email Validation
curl -s -X GET "%BASE_URL%/validate/email?email=test@example.com"
echo.
echo.

echo Test 5.2: Device Token Validation
curl -s -X GET "%BASE_URL%/validate/device-token?token=abc123def456"
echo.
echo.

echo ✅ Testing Complete!
echo ===================
echo.
echo 📝 Test Summary:
echo - Health check: Verify service is running
echo - Email tests: Test email sending functionality
echo - Push tests: Test Firebase push notifications
echo - Combined tests: Test both email and push together
echo - Validation tests: Test input validation
echo.
echo 🔍 Next Steps:
echo 1. Check application logs for detailed information
echo 2. For real email testing: Update email credentials in application.properties
echo 3. For real push testing: Get actual FCM tokens from mobile apps
echo 4. Monitor Firebase Console for push notification statistics
echo.
echo 📚 For more details, see TESTING_GUIDE.md
echo.
pause
