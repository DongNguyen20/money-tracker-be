# Money Tracker API Design Document

## Base URL
```
http://localhost:8081/api/v1
```

## Authentication
All endpoints require API key authentication via header:
```
X-API-Key: {your-api-key}
```

---

## API Endpoints

### 1. Transactions API

#### Get All Transactions
```
GET /api/v1/transactions
```

**Query Parameters:**
- `type` (optional): Filter by type (income/expense/saving)
- `categoryId` (optional): Filter by category ID
- `dateFrom` (optional): Start date (YYYY-MM-DD)
- `dateTo` (optional): End date (YYYY-MM-DD)
- `page` (optional): Page number (default: 1)
- `size` (optional): Page size (default: 10)
- `sort` (optional): Sort field (default: date)
- `direction` (optional): Sort direction (asc/desc, default: desc)

**Response:**
```json
{
  "content": [
    {
      "id": "txn_1234567890",
      "amount": 150000,
      "type": "expense",
      "categoryId": "cat_1",
      "categoryName": "Ăn uống",
      "categoryIcon": "🍜",
      "note": "Cơm trưa",
      "date": "2024-05-31",
      "createdAt": "2024-05-31T10:30:00Z"
    }
  ],
  "totalElements": 100,
  "totalPages": 10,
  "currentPage": 1,
  "pageSize": 10
}
```

#### Get Transaction by ID
```
GET /api/v1/transactions/{id}
```

**Response:**
```json
{
  "id": "txn_1234567890",
  "amount": 150000,
  "type": "expense",
  "categoryId": "cat_1",
  "note": "Cơm trưa",
  "date": "2024-05-31",
  "createdAt": "2024-05-31T10:30:00Z"
}
```

#### Create Transaction
```
POST /api/v1/transactions
```

**Request Body:**
```json
{
  "amount": 150000,
  "type": "expense",
  "categoryId": "cat_1",
  "note": "Cơm trưa",
  "date": "2024-05-31"
}
```

**Response:** 201 Created
```json
{
  "id": "txn_1234567890",
  "amount": 150000,
  "type": "expense",
  "categoryId": "cat_1",
  "note": "Cơm trưa",
  "date": "2024-05-31",
  "createdAt": "2024-05-31T10:30:00Z"
}
```

#### Update Transaction
```
PUT /api/v1/transactions/{id}
```

**Request Body:** Same as create

**Response:** 200 OK
```json
{
  "id": "txn_1234567890",
  "amount": 200000,
  "type": "expense",
  "categoryId": "cat_1",
  "note": "Cơm trưa",
  "date": "2024-05-31",
  "updatedAt": "2024-05-31T11:00:00Z"
}
```

#### Delete Transaction
```
DELETE /api/v1/transactions/{id}
```

**Response:** 204 No Content

#### Get Transaction Statistics
```
GET /api/v1/transactions/statistics
```

**Query Parameters:**
- `month` (optional): Month (1-12)
- `year` (optional): Year

**Response:**
```json
{
  "totalIncome": 15000000,
  "totalExpense": 8000000,
  "totalSaving": 2000000,
  "balance": 5000000,
  "transactionCount": 45
}
```

---

### 2. Categories API

#### Get All Categories
```
GET /api/v1/categories
```

**Query Parameters:**
- `type` (optional): Filter by type (income/expense/saving)

**Response:**
```json
[
  {
    "id": "cat_1",
    "name": "Ăn uống",
    "icon": "🍜",
    "type": "expense",
    "color": "#ef4444",
    "transactionCount": 25
  }
]
```

#### Get Category by ID
```
GET /api/v1/categories/{id}
```

**Response:**
```json
{
  "id": "cat_1",
  "name": "Ăn uống",
  "icon": "🍜",
  "type": "expense",
  "color": "#ef4444",
  "transactionCount": 25
}
```

#### Create Category
```
POST /api/v1/categories
```

**Request Body:**
```json
{
  "name": "Ăn uống",
  "icon": "🍜",
  "type": "expense",
  "color": "#ef4444"
}
```

**Response:** 201 Created

#### Update Category
```
PUT /api/v1/categories/{id}
```

**Request Body:** Same as create

**Response:** 200 OK

#### Delete Category
```
DELETE /api/v1/categories/{id}
```

**Response:** 204 No Content

**Error Response** (if category has transactions):
```json
{
  "error": "Cannot delete category with existing transactions",
  "code": "CATEGORY_HAS_TRANSACTIONS"
}
```

---

### 3. Dashboard API

#### Get Dashboard Overview
```
GET /api/v1/dashboard/overview
```

**Query Parameters:**
- `month` (optional): Month (1-12)
- `year` (optional): Year

**Response:**
```json
{
  "balance": 15000000,
  "monthlyIncome": 12000000,
  "monthlyExpense": 7000000,
  "monthlySaving": 2000000,
  "stockAssets": 50000000,
  "totalAssets": 65000000
}
```

#### Get Recent Transactions
```
GET /api/v1/dashboard/recent-transactions
```

**Query Parameters:**
- `limit` (optional): Number of transactions (default: 5)

**Response:**
```json
[
  {
    "id": "txn_123",
    "amount": 150000,
    "type": "expense",
    "categoryName": "Ăn uống",
    "categoryIcon": "🍜",
    "note": "Cơm trưa",
    "date": "2024-05-31"
  }
]
```

#### Get Top Spending Categories
```
GET /api/v1/dashboard/top-spending
```

**Query Parameters:**
- `month` (optional): Month (1-12)
- `year` (optional): Year
- `limit` (optional): Number of categories (default: 5)

**Response:**
```json
[
  {
    "categoryId": "cat_1",
    "categoryName": "Ăn uống",
    "categoryIcon": "🍜",
    "amount": 2500000,
    "color": "#ef4444"
  }
]
```

#### Get Daily Trend (Last 7 Days)
```
GET /api/v1/dashboard/daily-trend
```

**Response:**
```json
[
  {
    "date": "2024-05-25",
    "income": 500000,
    "expense": 300000
  }
]
```

---

### 4. Stocks API

#### Get Stock Portfolio
```
GET /api/v1/stocks/portfolio
```

**Response:**
```json
{
  "totalValue": 50000000,
  "totalCash": 10000000,
  "stocks": [
    {
      "ticker": "VCB",
      "quantity": 100,
      "averagePrice": 80000,
      "currentPrice": 85000,
      "marketValue": 8500000,
      "profitLoss": 500000,
      "profitLossPercent": 6.25
    }
  ]
}
```

#### Get Stock Operations History
```
GET /api/v1/stocks/operations
```

**Query Parameters:**
- `ticker` (optional): Filter by ticker
- `type` (optional): Filter by type (buy/sell)
- `page` (optional): Page number
- `size` (optional): Page size

**Response:**
```json
{
  "content": [
    {
      "id": "stock_123",
      "ticker": "VCB",
      "type": "buy",
      "quantity": 100,
      "price": 80000,
      "total": 8000000,
      "date": "2024-05-01"
    }
  ],
  "totalElements": 50,
  "totalPages": 5
}
```

#### Create Stock Operation
```
POST /api/v1/stocks/operations
```

**Request Body:**
```json
{
  "ticker": "VCB",
  "type": "buy",
  "quantity": 100,
  "price": 80000,
  "date": "2024-05-01"
}
```

**Response:** 201 Created

#### Update Stock Operation
```
PUT /api/v1/stocks/operations/{id}
```

**Response:** 200 OK

#### Delete Stock Operation
```
DELETE /api/v1/stocks/operations/{id}
```

**Response:** 204 No Content

#### Update Current Price
```
PUT /api/v1/stocks/{ticker}/price
```

**Request Body:**
```json
{
  "price": 85000
}
```

**Response:** 200 OK

#### Get Stock Cash Transactions
```
GET /api/v1/stocks/cash
```

**Response:**
```json
{
  "balance": 10000000,
  "transactions": [
    {
      "id": "cash_123",
      "type": "deposit",
      "amount": 5000000,
      "date": "2024-05-01"
    }
  ]
}
```

#### Create Cash Transaction
```
POST /api/v1/stocks/cash
```

**Request Body:**
```json
{
  "type": "deposit",
  "amount": 5000000,
  "date": "2024-05-01"
}
```

**Response:** 201 Created

---

### 5. Rent API

#### Get Rent Payments
```
GET /api/v1/rent/payments
```

**Query Parameters:**
- `month` (optional): Month (1-12)
- `year` (optional): Year
- `status` (optional): Filter by status (paid/pending/overdue)

**Response:**
```json
{
  "content": [
    {
      "id": "rent_123",
      "amount": 5000000,
      "dueDate": "2024-05-01",
      "paidDate": "2024-04-30",
      "status": "paid",
      "month": 5,
      "year": 2024
    }
  ],
  "totalElements": 12
}
```

#### Create Rent Payment
```
POST /api/v1/rent/payments
```

**Request Body:**
```json
{
  "amount": 5000000,
  "dueDate": "2024-06-01",
  "paidDate": "2024-05-30"
}
```

**Response:** 201 Created

#### Update Rent Payment
```
PUT /api/v1/rent/payments/{id}
```

**Response:** 200 OK

#### Delete Rent Payment
```
DELETE /api/v1/rent/payments/{id}
```

**Response:** 204 No Content

#### Get Rent Summary
```
GET /api/v1/rent/summary
```

**Query Parameters:**
- `year` (optional): Year

**Response:**
```json
{
  "totalPaid": 60000000,
  "totalPending": 5000000,
  "totalOverdue": 0,
  "averagePayment": 5000000
}
```

---

### 6. Income API

#### Get Income Transactions
```
GET /api/v1/income
```

**Query Parameters:**
- `categoryId` (optional): Filter by category
- `dateFrom` (optional): Start date
- `dateTo` (optional): End date
- `page` (optional): Page number
- `size` (optional): Page size

**Response:**
```json
{
  "content": [
    {
      "id": "txn_456",
      "amount": 15000000,
      "categoryId": "cat_4",
      "categoryName": "Lương",
      "categoryIcon": "💰",
      "note": "Lương tháng 5",
      "date": "2024-05-25"
    }
  ],
  "totalElements": 24,
  "totalPages": 3
}
```

#### Create Income Transaction
```
POST /api/v1/income
```

**Request Body:**
```json
{
  "amount": 15000000,
  "categoryId": "cat_4",
  "note": "Lương tháng 5",
  "date": "2024-05-25"
}
```

**Response:** 201 Created

#### Get Income Statistics
```
GET /api/v1/income/statistics
```

**Query Parameters:**
- `month` (optional): Month
- `year` (optional): Year

**Response:**
```json
{
  "totalIncome": 15000000,
  "transactionCount": 3,
  "averageIncome": 5000000,
  "categories": [
    {
      "categoryId": "cat_4",
      "categoryName": "Lương",
      "amount": 15000000,
      "percentage": 100
    }
  ]
}
```

---

### 7. Reports API

#### Get Daily Report
```
GET /api/v1/reports/daily
```

**Query Parameters:**
- `month` (optional): Month (1-12)
- `year` (optional): Year

**Response:**
```json
{
  "expenseByCategory": [
    {
      "categoryId": "cat_1",
      "categoryName": "Ăn uống",
      "amount": 2500000,
      "color": "#ef4444"
    }
  ],
  "dailyTrend": [
    {
      "date": "2024-05-31",
      "income": 0,
      "expense": 500000
    }
  ],
  "recentTransactions": []
}
```

#### Get Monthly Report
```
GET /api/v1/reports/monthly
```

**Query Parameters:**
- `year` (optional): Year

**Response:**
```json
{
  "monthlyData": [
    {
      "month": 5,
      "income": 15000000,
      "expense": 8000000,
      "saving": 2000000
    }
  ]
}
```

#### Get Yearly Report
```
GET /api/v1/reports/yearly
```

**Response:**
```json
{
  "years": [
    {
      "year": 2024,
      "totalIncome": 90000000,
      "totalExpense": 50000000,
      "totalSaving": 15000000
    }
  ]
}
```

#### Get Category Breakdown
```
GET /api/v1/reports/category-breakdown
```

**Query Parameters:**
- `type` (optional): Filter by type (income/expense/saving)
- `month` (optional): Month
- `year` (optional): Year

**Response:**
```json
{
  "categories": [
    {
      "categoryId": "cat_1",
      "categoryName": "Ăn uống",
      "amount": 2500000,
      "percentage": 31.25,
      "color": "#ef4444"
    }
  ]
}
```

---

### 8. Balance API

#### Get Current Balance
```
GET /api/v1/balance
```

**Response:**
```json
{
  "balance": 15000000,
  "lastUpdated": "2024-05-31T12:00:00Z"
}
```

#### Get Balance History
```
GET /api/v1/balance/history
```

**Query Parameters:**
- `dateFrom` (optional): Start date
- `dateTo` (optional): End date
- `period` (optional): Period (daily/weekly/monthly)

**Response:**
```json
[
  {
    "date": "2024-05-31",
    "balance": 15000000
  }
]
```

---

## Error Responses

### Standard Error Format
```json
{
  "timestamp": "2024-05-31T10:30:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Invalid request parameters",
  "path": "/api/v1/transactions",
  "errors": [
    {
      "field": "amount",
      "message": "Amount must be positive"
    }
  ]
}
```

### Common HTTP Status Codes
- `200 OK` - Request successful
- `201 Created` - Resource created successfully
- `204 No Content` - Request successful, no content returned
- `400 Bad Request` - Invalid request parameters
- `401 Unauthorized` - Missing or invalid API key
- `403 Forbidden` - API key does not have permission
- `404 Not Found` - Resource not found
- `409 Conflict` - Resource conflict (e.g., duplicate)
- `422 Unprocessable Entity` - Validation error
- `500 Internal Server Error` - Server error

---

## Data Models

### Transaction
```json
{
  "id": "string",
  "amount": "number",
  "type": "income|expense|saving",
  "categoryId": "string",
  "note": "string",
  "date": "YYYY-MM-DD",
  "createdAt": "ISO8601",
  "updatedAt": "ISO8601"
}
```

### Category
```json
{
  "id": "string",
  "name": "string",
  "icon": "string",
  "type": "income|expense|saving",
  "color": "string",
  "transactionCount": "number"
}
```

### Stock Operation
```json
{
  "id": "string",
  "ticker": "string",
  "type": "buy|sell",
  "quantity": "number",
  "price": "number",
  "total": "number",
  "date": "YYYY-MM-DD",
  "createdAt": "ISO8601"
}
```

### Rent Payment
```json
{
  "id": "string",
  "amount": "number",
  "dueDate": "YYYY-MM-DD",
  "paidDate": "YYYY-MM-DD",
  "status": "paid|pending|overdue",
  "month": "number",
  "year": "number"
}
```

---

## Pagination

All list endpoints support pagination with these parameters:
- `page`: Page number (default: 1)
- `size`: Items per page (default: 10, max: 100)
- `sort`: Sort field
- `direction`: Sort direction (asc/desc)

Response includes pagination metadata:
```json
{
  "content": [],
  "totalElements": 100,
  "totalPages": 10,
  "currentPage": 1,
  "pageSize": 10
}
```

---

## Rate Limiting
- 1000 requests per minute per API key
- Rate limit headers included in response:
  - `X-RateLimit-Limit`: 1000
  - `X-RateLimit-Remaining`: 999
  - `X-RateLimit-Reset`: 1717142400

---

## Versioning
API version is included in the URL path: `/api/v1/`

Future versions will be numbered incrementally: `/api/v2/`, etc.