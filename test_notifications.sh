#!/bin/bash

# AI Green Tick Notification Service - Test Script
# This script tests both email and push notification functionality

BASE_URL="http://localhost:9090/api/v1/notifications"

echo "🧪 AI Green Tick Notification Service - Test Script"
echo "=================================================="
echo ""

# Function to make HTTP requests
make_request() {
    local method=$1
    local endpoint=$2
    local data=$3
    
    if [ -n "$data" ]; then
        curl -s -X $method "$BASE_URL$endpoint" \
            -H "Content-Type: application/json" \
            -d "$data"
    else
        curl -s -X $method "$BASE_URL$endpoint"
    fi
}

# Function to print test result
print_test_result() {
    local test_name=$1
    local response=$2
    
    echo "📋 Test: $test_name"
    echo "Response: $response"
    echo "---"
    echo ""
}

echo "🔍 Step 1: Health Check"
echo "======================"
health_response=$(make_request "GET" "/health")
print_test_result "Service Health Check" "$health_response"

echo "📧 Step 2: Email Notification Tests"
echo "==================================="

echo "Test 2.1: Basic Email Notification"
email_data='{
  "to": ["test@example.com"],
  "title": "Test Email from AI Green Tick",
  "body": "This is a test email notification. If you receive this, the email functionality is working!"
}'
email_response=$(make_request "POST" "/email" "$email_data")
print_test_result "Basic Email Notification" "$email_response"

echo "Test 2.2: Email with CC"
email_cc_data='{
  "to": ["user1@example.com", "user2@example.com"],
  "cc": ["manager@example.com"],
  "title": "Email with CC Test",
  "body": "This email includes CC recipients."
}'
email_cc_response=$(make_request "POST" "/email" "$email_cc_data")
print_test_result "Email with CC" "$email_cc_response"

echo "Test 2.3: Email Validation Test (Should Fail)"
invalid_email_data='{
  "to": ["invalid-email"],
  "title": "",
  "body": "This should fail validation"
}'
invalid_email_response=$(make_request "POST" "/email" "$invalid_email_data")
print_test_result "Email Validation Test" "$invalid_email_response"

echo "Test 2.4: Async Email Notification"
async_email_response=$(make_request "POST" "/email/async" "$email_data")
print_test_result "Async Email Notification" "$async_email_response"

echo "📱 Step 3: Push Notification Tests"
echo "================================"

echo "Test 3.1: Push Notification with Dummy Token (Will show error but test connection)"
push_data='{
  "deviceIds": ["dummy_token_123", "dummy_token_456"],
  "title": "Test Push Notification",
  "description": "This is a test push notification from Firebase!",
  "imageUrl": "https://via.placeholder.com/300x200.png"
}'
push_response=$(make_request "POST" "/push" "$push_data")
print_test_result "Push Notification (Dummy Tokens)" "$push_response"

echo "Test 3.2: Topic-Based Push Notification"
topic_response=$(make_request "POST" "/push/topic?topic=test_topic&title=Topic Test&description=Testing topic notifications&imageUrl=https://via.placeholder.com/300x200.png")
print_test_result "Topic-Based Push Notification" "$topic_response"

echo "Test 3.3: Async Push Notification"
async_push_response=$(make_request "POST" "/push/async" "$push_data")
print_test_result "Async Push Notification" "$async_push_response"

echo "🔄 Step 4: Combined Notification Tests"
echo "======================================"

echo "Test 4.1: Both Email and Push Notifications"
combined_data='{
  "emailRequest": {
    "to": ["test@example.com"],
    "title": "Combined Test Email",
    "body": "This email is sent along with a push notification."
  },
  "pushRequest": {
    "deviceIds": ["dummy_token_123"],
    "title": "Combined Test Push",
    "description": "This push notification is sent along with an email.",
    "imageUrl": "https://via.placeholder.com/300x200.png"
  }
}'
combined_response=$(make_request "POST" "/both" "$combined_data")
print_test_result "Combined Email and Push" "$combined_response"

echo "Test 4.2: Async Combined Notifications"
async_combined_response=$(make_request "POST" "/both/async" "$combined_data")
print_test_result "Async Combined Notifications" "$async_combined_response"

echo "🔍 Step 5: Validation Tests"
echo "==========================="

echo "Test 5.1: Email Validation"
email_validation=$(make_request "GET" "/validate/email?email=test@example.com")
print_test_result "Email Validation" "$email_validation"

echo "Test 5.2: Device Token Validation"
token_validation=$(make_request "GET" "/validate/device-token?token=abc123def456")
print_test_result "Device Token Validation" "$token_validation"

echo "✅ Testing Complete!"
echo "==================="
echo ""
echo "📝 Test Summary:"
echo "- Health check: Verify service is running"
echo "- Email tests: Test email sending functionality"
echo "- Push tests: Test Firebase push notifications"
echo "- Combined tests: Test both email and push together"
echo "- Validation tests: Test input validation"
echo ""
echo "🔍 Next Steps:"
echo "1. Check application logs for detailed information"
echo "2. For real email testing: Update email credentials in application.properties"
echo "3. For real push testing: Get actual FCM tokens from mobile apps"
echo "4. Monitor Firebase Console for push notification statistics"
echo ""
echo "📚 For more details, see TESTING_GUIDE.md"
