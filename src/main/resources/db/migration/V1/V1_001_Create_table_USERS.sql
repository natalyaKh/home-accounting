CREATE TABLE users
(
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  create_date TIMESTAMP NOT NULL,
  update_date TIMESTAMP NOT NULL,
  email VARCHAR(120) NOT NULL NOT NULL,
  email_verification_status BIT(1) NOT NULL,
  email_verification_token VARCHAR(255) NULL,
  encrypted_password VARCHAR(255) NOT NULL,
  first_name VARCHAR(50) NOT NULL,
  last_name VARCHAR(50) NOT NULL,
  user_id VARCHAR(255) NOT NULL UNIQUE

)