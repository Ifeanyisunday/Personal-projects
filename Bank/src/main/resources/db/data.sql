# SET FOREIGN_KEY_CHECKS = 0;
# # TRUNCATE TABLE Wallet;
# SET FOREIGN_KEY_CHECKS = 1;
#
# INSERT INTO wallet (id, balance, account_number) VALUES
#                                                      (200, 500.00, '12345'),
#                                                      (201, 200.00, '98989'),
#                                                      (202, 300.00, '32444');
#
# TRUNCATE TABLE Customer;
#
# INSERT INTO Customer (id, first_name, last_name, gender, email, password, wallet_id) VALUES
#                                                                                          (1, "Tree", "Mokey", "MALE", "mokey@gmail.com", "3311", 200),
#                                                                                          (2, "Love", "Mike", "FEMALE", "mikelove@gmail.com", "password", 201),
#                                                                                          (3, "Luthor", "Lex","MALE", "lex@gmail.com", "121", 202);