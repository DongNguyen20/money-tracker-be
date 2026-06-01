-- Money Tracker Database Schema v0.1
-- PostgreSQL Database Schema

-- Enum Types
CREATE TYPE category_type_enum AS ENUM ('INCOME', 'EXPENSE', 'SAVING');
CREATE TYPE transaction_type_enum AS ENUM ('INCOME', 'EXPENSE', 'SAVING');
CREATE TYPE stock_transaction_type_enum AS ENUM ('BUY', 'SELL');
CREATE TYPE topup_type_enum AS ENUM ('DEPOSIT', 'WITHDRAWAL');
CREATE TYPE balance_type_enum AS ENUM ('STOCK', 'CASH');

-- Categories Table
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

-- Transactions Table
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

-- Rent Table (Main rent record)
CREATE TABLE rent (
    id BIGSERIAL PRIMARY KEY,
    date DATE NOT NULL,
    name VARCHAR(200) NOT NULL,
    total_amount DECIMAL(19,2) NOT NULL DEFAULT 0,
    date_of_origin DATE NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Rent Detail Table (Detailed breakdown)
CREATE TABLE rent_detail (
    id BIGSERIAL PRIMARY KEY,
    rent_id BIGINT NOT NULL,
    base_amount DECIMAL(19,2) NOT NULL DEFAULT 0,
    wifi_amount DECIMAL(19,2) NOT NULL DEFAULT 0,
    other_amount DECIMAL(19,2) NOT NULL DEFAULT 0,
    kwh_consumed INTEGER,
    m3_consumed INTEGER,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_rent_detail_rent FOREIGN KEY (rent_id) REFERENCES rent(id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Stock Transactions Table
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

-- Stock Topup Table (Cash in/out for stock account)
CREATE TABLE stock_topup (
    id BIGSERIAL PRIMARY KEY,
    type topup_type_enum NOT NULL,
    amount DECIMAL(19,2) NOT NULL,
    day_of_origin DATE NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Balance Table (Track account balances)
CREATE TABLE balance (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(20) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    available_balance DECIMAL(19,2) NOT NULL DEFAULT 0,
    type balance_type_enum NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Config Parameter Table
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
CREATE INDEX idx_rent_date ON rent(date);
CREATE INDEX idx_rent_detail_rent_id ON rent_detail(rent_id);
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

CREATE TRIGGER update_rent_detail_updated_at BEFORE UPDATE ON rent_detail
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_stock_transactions_updated_at BEFORE UPDATE ON stock_transactions
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_stock_topup_updated_at BEFORE UPDATE ON stock_topup
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_balance_updated_at BEFORE UPDATE ON balance
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_config_param_updated_at BEFORE UPDATE ON config_param
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

-- Sample Data

-- Categories Sample Data
INSERT INTO categories (code, name, icon, type, color) VALUES
('FOOD', 'Food & Dining', '🍔', 'EXPENSE', '#FF6B6B'),
('SHOPPING', 'Shopping', '🛍️', 'EXPENSE', '#4ECDC4'),
('TRANSPORT', 'Transportation', '🚗', 'EXPENSE', '#45B7D1'),
('ENTERTAINMENT', 'Entertainment', '🎬', 'EXPENSE', '#96CEB4'),
('HEALTH', 'Healthcare', '💊', 'EXPENSE', '#FFEAA7'),
('SALARY', 'Salary', '💰', 'INCOME', '#2ECC71'),
('INVESTMENT', 'Investment', '📈', 'INCOME', '#3498DB'),
('FREELANCE', 'Freelance', '💻', 'INCOME', '#9B59B6'),
('SAVINGS', 'Savings', '🐷', 'SAVING', '#F39C12'),
('EMERGENCY', 'Emergency Fund', '🆘', 'SAVING', '#E74C3C');

-- Transactions Sample Data
INSERT INTO transactions (amount, type, category_id, note, date_of_origin) VALUES
(150000, 'EXPENSE', 1, 'Lunch at restaurant', '2026-01-15'),
(500000, 'EXPENSE', 2, 'Buy new clothes', '2026-01-16'),
(100000, 'EXPENSE', 3, 'Gas for car', '2026-01-17'),
(200000, 'EXPENSE', 4, 'Movie tickets', '2026-01-18'),
(300000, 'EXPENSE', 5, 'Medicine', '2026-01-19'),
(15000000, 'INCOME', 6, 'Monthly salary', '2026-01-01'),
(2000000, 'INCOME', 7, 'Stock dividend', '2026-01-10'),
(3500000, 'INCOME', 8, 'Freelance project', '2026-01-15'),
(5000000, 'SAVING', 9, 'Monthly savings', '2026-01-01'),
(1000000, 'SAVING', 10, 'Emergency fund contribution', '2026-01-05');

-- Rent Sample Data
INSERT INTO rent (date, name, total_amount, date_of_origin) VALUES
('2026-01-01', 'January 2026 Rent', 5500000, '2026-01-01'),
('2026-02-01', 'February 2026 Rent', 5500000, '2026-02-01'),
('2026-03-01', 'March 2026 Rent', 5500000, '2026-03-01');

-- Rent Detail Sample Data
INSERT INTO rent_detail (rent_id, base_amount, wifi_amount, other_amount, kwh_consumed, m3_consumed) VALUES
(1, 4000000, 500000, 1000000, 150, 20),
(2, 4000000, 500000, 1000000, 160, 22),
(3, 4000000, 500000, 1000000, 155, 21);

-- Stock Transactions Sample Data
INSERT INTO stock_transactions (code, type, quantity, amount, interest_rate, day_of_origin) VALUES
('VCB', 'BUY', 100, 85000000, NULL, '2026-01-05'),
('VIC', 'BUY', 50, 125000000, NULL, '2026-01-10'),
('FPT', 'BUY', 200, 78000000, NULL, '2026-01-15'),
('VCB', 'SELL', 50, 42500000, NULL, '2026-01-20'),
('MBB', 'BUY', 100, 23000000, 0.0450, '2026-01-25');

-- Stock Topup Sample Data
INSERT INTO stock_topup (type, amount, day_of_origin) VALUES
('DEPOSIT', 50000000, '2026-01-01'),
('DEPOSIT', 30000000, '2026-01-15'),
('WITHDRAWAL', 20000000, '2026-01-20'),
('DEPOSIT', 10000000, '2026-01-25');

-- Balance Sample Data
INSERT INTO balance (code, name, available_balance, type) VALUES
('CASH_VND', 'Vietnam Dong Cash', 45000000, 'CASH'),
('CASH_USD', 'US Dollar Cash', 1000, 'CASH'),
('STOCK_VCB', 'VCB Stock Account', 42500000, 'STOCK'),
('STOCK_PORTFOLIO', 'Stock Portfolio Total', 258500000, 'STOCK');

-- Config Parameter Sample Data
INSERT INTO config_param (param_key, param_value, unit, description) VALUES
('currency.default', 'VND', NULL, 'Default currency for transactions'),
('stock.commission.rate', '0.0035', '%', 'Stock trading commission rate'),
('rent.electricity.rate', '3500', 'VND/kWh', 'Electricity rate per kWh'),
('rent.water.rate', '25000', 'VND/m3', 'Water rate per cubic meter'),
('savings.target.monthly', '5000000', 'VND', 'Monthly savings target'),
('budget.warning.threshold', '80', '%', 'Budget warning threshold percentage');