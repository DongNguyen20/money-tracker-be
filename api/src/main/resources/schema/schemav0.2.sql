-- Money Tracker Database Schema v0.2
-- PostgreSQL Database Schema - Rent Management UI Enhancements

-- Enum Types (from v0.1)
CREATE TYPE category_type_enum AS ENUM ('INCOME', 'EXPENSE', 'SAVING');
CREATE TYPE transaction_type_enum AS ENUM ('INCOME', 'EXPENSE', 'SAVING');
CREATE TYPE stock_transaction_type_enum AS ENUM ('BUY', 'SELL');
CREATE TYPE topup_type_enum AS ENUM ('DEPOSIT', 'WITHDRAWAL');
CREATE TYPE balance_type_enum AS ENUM ('STOCK', 'CASH');

-- New Enum Types for Rent Management
CREATE TYPE rent_status_enum AS ENUM ('PENDING', 'PAID', 'OVERDUE', 'PARTIALLY_PAID');

-- Categories Table (from v0.1)
CREATE TABLE categories (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(20) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    icon VARCHAR(10),
    type category_type_enum NOT NULL,
    color VARCHAR(20),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Transactions Table (from v0.1)
CREATE TABLE transactions (
    id BIGSERIAL PRIMARY KEY,
    amount DECIMAL(19,2) NOT NULL,
    type transaction_type_enum NOT NULL,
    category_id BIGINT NOT NULL,
    note VARCHAR(500),
    date_of_origin DATE NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_transaction_category FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE RESTRICT ON UPDATE CASCADE
);

-- Rent Table (Modified for UI - Flat structure matching frontend)
CREATE TABLE rent (
    id BIGSERIAL PRIMARY KEY,
    month VARCHAR(7) NOT NULL, -- Format: YYYY-MM
    base DECIMAL(19,2) NOT NULL DEFAULT 0,
    wifi DECIMAL(19,2) NOT NULL DEFAULT 0,
    garbage DECIMAL(19,2) NOT NULL DEFAULT 0,
    other DECIMAL(19,2) NOT NULL DEFAULT 0,
    elec_usage INTEGER NOT NULL DEFAULT 0,
    water_usage INTEGER NOT NULL DEFAULT 0,
    elec_total DECIMAL(19,2) NOT NULL DEFAULT 0,
    water_total DECIMAL(19,2) NOT NULL DEFAULT 0,
    total DECIMAL(19,2) NOT NULL DEFAULT 0,
    status rent_status_enum NOT NULL DEFAULT 'PAID',
    notes VARCHAR(500),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Stock Transactions Table (from v0.1)
CREATE TABLE stock_transactions (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(20) NOT NULL,
    type stock_transaction_type_enum NOT NULL,
    quantity INTEGER NOT NULL,
    amount DECIMAL(19,2) NOT NULL,
    interest_rate DECIMAL(5,4),
    day_of_origin DATE NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Stock Topup Table (from v0.1)
CREATE TABLE stock_topup (
    id BIGSERIAL PRIMARY KEY,
    type topup_type_enum NOT NULL,
    amount DECIMAL(19,2) NOT NULL,
    day_of_origin DATE NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Balance Table (from v0.1)
CREATE TABLE balance (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(20) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    available_balance DECIMAL(19,2) NOT NULL DEFAULT 0,
    type balance_type_enum NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Config Parameter Table (from v0.1)
CREATE TABLE config_param (
    id BIGSERIAL PRIMARY KEY,
    param_key VARCHAR(100) NOT NULL UNIQUE,
    param_value VARCHAR(500),
    unit VARCHAR(20),
    description VARCHAR(500),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Indexes for better performance
CREATE INDEX idx_transactions_category_id ON transactions(category_id);
CREATE INDEX idx_transactions_date ON transactions(date_of_origin);
CREATE INDEX idx_transactions_type ON transactions(type);
CREATE INDEX idx_categories_code ON categories(code);
CREATE INDEX idx_categories_type ON categories(type);
CREATE INDEX idx_rent_month ON rent(month);
CREATE INDEX idx_rent_status ON rent(status);
CREATE INDEX idx_stock_transactions_code ON stock_transactions(code);
CREATE INDEX idx_stock_transactions_date ON stock_transactions(day_of_origin);
CREATE INDEX idx_stock_topup_date ON stock_topup(day_of_origin);
CREATE INDEX idx_stock_topup_type ON stock_topup(type);
CREATE INDEX idx_balance_code ON balance(code);
CREATE INDEX idx_balance_type ON balance(type);
CREATE INDEX idx_config_param_key ON config_param(param_key);

-- Trigger to update updated_at timestamp
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

-- Apply trigger to all tables with updated_at
CREATE TRIGGER update_categories_updated_at BEFORE UPDATE ON categories
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_transactions_updated_at BEFORE UPDATE ON transactions
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_rent_updated_at BEFORE UPDATE ON rent
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_stock_transactions_updated_at BEFORE UPDATE ON stock_transactions
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_stock_topup_updated_at BEFORE UPDATE ON stock_topup
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_balance_updated_at BEFORE UPDATE ON balance
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_config_param_updated_at BEFORE UPDATE ON config_param
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

-- =============================================================================
-- SEED DATA - Sample Data for Development & Testing
-- =============================================================================

-- Categories Sample Data
INSERT INTO categories (code, name, icon, type, color) VALUES
    ('CAT001', 'Ăn uống', '🍜', 'EXPENSE', '#ef4444'),
    ('CAT002', 'Thuê nhà', '🏠', 'EXPENSE', '#f59e0b'),
    ('CAT003', 'Di chuyển', '🚗', 'EXPENSE', '#3b82f6'),
    ('CAT004', 'Mua sắm', '🛍️', 'EXPENSE', '#ec4899'),
    ('CAT005', 'Giải trí', '🎬', 'EXPENSE', '#8b5cf6'),
    ('CAT006', 'Sức khoẻ', '💊', 'EXPENSE', '#06b6d4'),
    ('CAT007', 'Đầu tư', '📈', 'EXPENSE', '#6366f1'),
    ('CAT008', 'Lương', '💰', 'INCOME', '#10b981'),
    ('CAT009', 'Thưởng', '🎁', 'INCOME', '#22c55e'),
    ('CAT010', 'Freelance', '💻', 'INCOME', '#14b8a6'),
    ('CAT011', 'Tiết kiệm', '🏦', 'SAVING', '#6366f1'),
    ('CAT012', 'Quỹ khẩn cấp', '🛡️', 'SAVING', '#f97316');

-- Rent Sample Data (Updated to match new flat structure)
INSERT INTO rent (month, base, wifi, garbage, other, elec_usage, water_usage, elec_total, water_total, total, status, notes) VALUES
    ('2025-01', 2200000.00, 200000.00, 30000.00, 50000.00, 120, 3, 540000.00, 60000.00, 3080000.00, 'PAID', 'Đã thanh toán đầy đủ'),
    ('2025-02', 2200000.00, 200000.00, 30000.00, 40000.00, 160, 4, 720000.00, 80000.00, 3270000.00, 'PAID', 'Đã thanh toán đầy đủ'),
    ('2025-03', 2200000.00, 200000.00, 30000.00, 30000.00, 100, 2, 450000.00, 40000.00, 2950000.00, 'PAID', 'Giảm do đi công tác'),
    ('2025-04', 2200000.00, 200000.00, 30000.00, 40000.00, 140, 3, 630000.00, 60000.00, 3160000.00, 'PENDING', 'Chưa thanh toán'),
    ('2025-05', 2200000.00, 200000.00, 30000.00, 50000.00, 150, 4, 675000.00, 80000.00, 3335000.00, 'PENDING', 'Chưa thanh toán');

