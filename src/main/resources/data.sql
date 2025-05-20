INSERT INTO icons (name, path) VALUES ('Bank', '/icons/account_bank_24dp.svg');
INSERT INTO icons (name, path) VALUES ('Wallet', '/icons/account_wallet_24dp.svg');
INSERT INTO icons (name, path) VALUES ('Card', '/icons/account_card_24dp.svg');
INSERT INTO icons (name, path) VALUES ('Bus', '/icons/category_bus_24dp.svg');
INSERT INTO icons (name, path) VALUES ('Computer', '/icons/computer_24dp.svg');
INSERT INTO icons (name, path) VALUES ('Cottage', '/icons/cottage_24dp.svg');
INSERT INTO icons (name, path) VALUES ('Car', '/icons/directions_car_24dp.svg');
INSERT INTO icons (name, path) VALUES ('Electric Car', '/icons/electric_car_24dp.svg');
INSERT INTO icons (name, path) VALUES ('Beauty', '/icons/health_and_beauty_24dp.svg');
INSERT INTO icons (name, path) VALUES ('Gas Station', '/icons/local_gas_station_24dp.svg');
INSERT INTO icons (name, path) VALUES ('Medication', '/icons/medication_24dp.svg');
INSERT INTO icons (name, path) VALUES ('Paid', '/icons/paid_24dp.svg');
INSERT INTO icons (name, path) VALUES ('Payments', '/icons/payments_24dp.svg');
INSERT INTO icons (name, path) VALUES ('Restaurant', '/icons/restaurant_24dp.svg');
INSERT INTO icons (name, path) VALUES ('Savings', '/icons/savings_24dp.svg');
INSERT INTO icons (name, path) VALUES ('School', '/icons/school_24dp.svg');
INSERT INTO icons (name, path) VALUES ('Scooter', '/icons/scooter_24dp.svg');
INSERT INTO icons (name, path) VALUES ('Shopping Bag', '/icons/shopping_bag_24dp.svg');
INSERT INTO icons (name, path) VALUES ('Shopping Cart', '/icons/shopping_cart_24dp.svg');
INSERT INTO icons (name, path) VALUES ('Spa', '/icons/spa_24dp.svg');
INSERT INTO icons (name, path) VALUES ('Store', '/icons/store_24dp.svg');
INSERT INTO icons (name, path) VALUES ('Train', '/icons/train_24dp.svg');

INSERT INTO categories (name, is_expense, icon_id, user_id) VALUES ('other', true, 1, null);
INSERT INTO categories (name, is_expense, icon_id, user_id) VALUES ('other', false, 1, null);

INSERT INTO currencies (code, symbol, name, target) VALUES ('USD', '$', 'dollars', true);
INSERT INTO currencies (code, symbol, name, target) VALUES ('RUB', '₽', 'rubles', false);
INSERT INTO currencies (code, symbol, name, target) VALUES ('EUR', '€', 'euros', false);
INSERT INTO currencies (code, symbol, name, target) VALUES ('BTC', '₿', 'bitcoins', false);

INSERT INTO users (target_currency_id, email, password, first_name, role)
VALUES ('USD', 'test', '$2a$10$dFma5sRnxjfcLsN2FJPDo.o1D7V7r9lrYDmogtXZaClOBoVh39T7.', 'tester', 'ROLE_USER')