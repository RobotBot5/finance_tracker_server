INSERT INTO icons (name, path) VALUES ('Bank', '/icons/account_bank_24dp.svg');
INSERT INTO icons (name, path) VALUES ('Wallet', '/icons/account_wallet_24dp.svg');
INSERT INTO icons (name, path) VALUES ('Card', '/icons/account_card_24dp.svg');
INSERT INTO icons (name, path) VALUES ('Bus', '/icons/category_bus_24dp.svg');

INSERT INTO categories (name, is_expense, icon_id, user_id) VALUES ('other', true, 1, null);
INSERT INTO categories (name, is_expense, icon_id, user_id) VALUES ('other', false, 1, null);

INSERT INTO currencies (code, symbol, name, target) VALUES ('USD', '$', 'dollars', true);
INSERT INTO currencies (code, symbol, name, target) VALUES ('RUB', '₽', 'rubles', false);
INSERT INTO currencies (code, symbol, name, target) VALUES ('EUR', '€', 'euros', false);
INSERT INTO currencies (code, symbol, name, target) VALUES ('BTC', '₿', 'bitcoins', false);

INSERT INTO users (target_currency_id, email, password, first_name, role)
VALUES ('USD', 'test', '$2a$10$dFma5sRnxjfcLsN2FJPDo.o1D7V7r9lrYDmogtXZaClOBoVh39T7.', 'tester', 'ROLE_USER')