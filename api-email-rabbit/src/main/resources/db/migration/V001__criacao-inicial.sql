CREATE TABLE IF NOT EXISTS email_message (
    id BINARY(16) PRIMARY KEY,
    to_address VARCHAR(255) NOT NULL,
    subject VARCHAR(255) NOT NULL,
    text TEXT NOT NULL,
    tipo VARCHAR(255) NOT NULL
);
